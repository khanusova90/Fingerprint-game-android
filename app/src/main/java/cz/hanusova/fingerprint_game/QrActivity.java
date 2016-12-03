package cz.hanusova.fingerprint_game;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

import cz.hanusova.fingerprint_game.camera.BarcodeGraphic;
import cz.hanusova.fingerprint_game.camera.BarcodeGraphicTracker;
import cz.hanusova.fingerprint_game.camera.BarcodeTrackerFactory;
import cz.hanusova.fingerprint_game.camera.CameraSource;
import cz.hanusova.fingerprint_game.camera.CameraSourcePreview;
import cz.hanusova.fingerprint_game.camera.GraphicOverlay;
import cz.hanusova.fingerprint_game.model.Activity;
import cz.hanusova.fingerprint_game.model.ActivityEnum;
import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Inventory;
import cz.hanusova.fingerprint_game.model.Material;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.model.PlaceType;
import cz.hanusova.fingerprint_game.rest.RestClient;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by khanusova on 9.9.2016.
 */
@EActivity(R.layout.qr_capture)
public class QrActivity extends AppCompatActivity {
    private static final String TAG = "QrActivity";

    // intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 9001;

    @RestService
    RestClient activityClient;

    @Pref
    Preferences_ preferences;

    @ViewById(R.id.preview)
    CameraSourcePreview preview;
    @ViewById(R.id.graphicOverlay)
    GraphicOverlay<GraphicOverlay.Graphic> overlay;
    @ViewById(R.id.qr_countdown)
    Button qrCountdown;
    @ViewById(R.id.qr_choose_workers)
    SeekBar seekWorkers;

    private CameraSource cameraSource;
    private BarcodeTrackerFactory barcodeFactory;
    private CountDownTimer timer;

    private Place testPlace;
    private Place place;

    @AfterViews
    public void init() {
        //TODO: Check permission
        createCameraSource(true, false);
        createTimer();
        startTracking();
    }

    private void hideSeekers(){
        seekWorkers.setVisibility(View.GONE);
    }

    @Click(R.id.qr_test)
    public void qrTest() {
        testPlace = new Place();
        testPlace.setIdPlace(1l);
        testPlace.setName("Naleziště");

        PlaceType pt = new PlaceType();
        pt.setIdPlaceType(1l);

//        Activity ac = new Activity();
//        ac.setIdActivity(1l);
//        ac.setName("Těžit");

        pt.setActivity(ActivityEnum.MINE);
        testPlace.setPlaceType(pt);
        MineActivity_.intent(this).place(testPlace).start();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
        qrCountdown.setVisibility(View.GONE);
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (preview != null) {
            preview.stop();
        }
        qrCountdown.setVisibility(View.GONE);
        timer.cancel();
        hideSeekers();
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (preview != null) {
            preview.release();
        }
        qrCountdown.setVisibility(View.GONE);
    }

    private void startTracking(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                BarcodeGraphicTracker tracker = barcodeFactory.getTracker();
                //TODO: vyresit tak, aby se opakovalo (znovu zavolani po odjeti z kodu apod.)
                while (place == null) {
                    while (tracker == null || tracker.getBarcode() == null) {
                        tracker = barcodeFactory.getTracker();
                    }
                    place = getPlaceInfo();
                }
                changeCountdownVisibility();
//                qrCountdown.setVisibility(View.VISIBLE);
                timer.start();
                ActivityEnum activity = place.getPlaceType().getActivity();
                BarcodeGraphic.activity = activity;
                showActivity(activity);
            }
        }).start();
    }

    @UiThread
    public void changeCountdownVisibility(){
        int visibility = qrCountdown.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
        qrCountdown.setVisibility(visibility);
    }

    @UiThread
    public void updateCountdown(long millisLeft){
        qrCountdown.setText(getString(R.string.qr_countdown, millisLeft / 1000));
    }

    private void createTimer() {
        Log.i(TAG, "Creating timer");
        timer = new CountDownTimer(10 * 1000, 1000) {
            private Barcode barcode = null;

            @Override
            public void onTick(long millisLeft) {
                updateCountdown(millisLeft);
                if (barcode == null){
                    Log.d(TAG, "Getting actual barcode");
                    barcode = getActualBarcode();
                }
                //TODO: zobrazit mozne cinnosti uz pri snimani
                Barcode actualBarcode = getActualBarcode();
                if (barcode == null || actualBarcode == null || !barcode.displayValue.equals(actualBarcode.displayValue)){
                    Log.i(TAG, "Barcode capturing stopped!");
                    //TODO: pridat sipku pro navrat do predchozi activity
                    qrCountdown.setVisibility(View.GONE);
                    stopTimer();
                    startTracking();
                }
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished, starting activity");
                //TODO: vyresit ruzne activity pro ruzna mista - ulozit nazev activity v DB?
                // Napr. fragment do activity
                if (place != null) {
                    //TODO: spustit aktivitu, vratit na MapActivity
                    MineActivity_.intent(getBaseContext())
                            .place(place)
                            .flags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
                            .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .start();
                }
            }
        };
    }

    private void showActivity(ActivityEnum activity){ //FIXME: prejmenovat
        switch(activity){
            case MINE:
                AppUser user = getUser();
                Inventory workers = getWorkers(user);
                showWorkersSeek(workers);
//                seekWorkers.setMax(workers.getAmount().intValue());
//                seekWorkers.setVisibility(View.VISIBLE);
                break;
            default:
                //TODO: informovat o nezname aktivite
                break;
        }
    }

    @UiThread
    void showWorkersSeek(Inventory workers){
        seekWorkers.setMax(workers.getAmount().intValue());
        seekWorkers.setVisibility(View.VISIBLE);
    }

    //TODO: vytvorit service
    private AppUser getUser(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(preferences.user().get(), AppUser.class);
        } catch (IOException e){
            Log.e(TAG, "Error occurred while trying to get actual user", e);
            return null;
        }
    }

    /**
     * Gets workers from user's inventory. If it does not find any, returns <code>null</code>
     * @param user
     * @return {@link Inventory} with workers
     */
    private Inventory getWorkers(AppUser user){
        Set <Inventory> inventorySet = user.getInventory();
        for (Inventory inv : inventorySet){
            if (inv.getMaterial().getName().equals(Constants.MATERIAL_WORKER)){
                return inv;
            }
        }
        return null;
    }

    private void stopTimer(){
        timer.cancel();
    }

    private Place getPlaceInfo() {
        Barcode barcode = getActualBarcode();
        if (barcode != null) {
            String url = barcode.displayValue;
            String placeCode = url.substring(url.lastIndexOf("/") + 1);
            Log.d(TAG, "Getting place with code " + placeCode);
            Place place = activityClient.getPlaceByCode(placeCode);
            return place;
        }
        return null;
    }

    /**
     *
     * @return {@link Barcode} that is being captured
     */
    private Barcode getActualBarcode() {
        BarcodeGraphic graphic = (BarcodeGraphic) overlay.getFirstGraphic();
        if (graphic != null) {
            return graphic.getBarcode();
        }
        return null;
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (cameraSource != null) {
            try {
                preview.start(cameraSource, overlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     * <p>
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = getApplicationContext();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
        barcodeFactory = new BarcodeTrackerFactory(overlay);
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());

        if (!barcodeDetector.isOperational()) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    autoFocus ? Camera.Parameters.FOCUS_MODE_AUTO : null);
        }

        cameraSource = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }

}
