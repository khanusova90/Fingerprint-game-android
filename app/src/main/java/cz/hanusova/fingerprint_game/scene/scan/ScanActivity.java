package cz.hanusova.fingerprint_game.scene.scan;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.Preferences_;
import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.base.BasePresenter;
import cz.hanusova.fingerprint_game.base.service.UserService;
import cz.hanusova.fingerprint_game.base.service.impl.UserServiceImpl;
import cz.hanusova.fingerprint_game.base.ui.BaseActivity;
import cz.hanusova.fingerprint_game.base.utils.AppUtils;
import cz.hanusova.fingerprint_game.base.utils.Constants;
import cz.hanusova.fingerprint_game.base.utils.PlaceUtils;
import cz.hanusova.fingerprint_game.camera.BarcodeGraphic;
import cz.hanusova.fingerprint_game.camera.BarcodeGraphicTracker;
import cz.hanusova.fingerprint_game.camera.BarcodeTrackerFactory;
import cz.hanusova.fingerprint_game.camera.CameraSource;
import cz.hanusova.fingerprint_game.camera.CameraSourcePreview;
import cz.hanusova.fingerprint_game.camera.GraphicOverlay;
import cz.hanusova.fingerprint_game.model.ActivityEnum;
import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Inventory;
import cz.hanusova.fingerprint_game.model.Item;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.rest.RestClient;
import cz.hanusova.fingerprint_game.scene.market.MarketActivity_;
import cz.hanusova.fingerprint_game.scene.scan.event.UserMovedEvent;
import cz.hanusova.fingerprint_game.task.BitmapWorkerTask;
import cz.hanusova.fingerprint_game.view.MaterialUsedView;
import cz.hanusova.fingerprint_game.view.MaterialUsedView_;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.google.android.gms.vision.barcode.Barcode.QR_CODE;

/**
 * Created by khanusova on 9.9.2016.
 */
@EActivity(R.layout.qr_capture)
public class ScanActivity extends BaseActivity implements ScanActivityView  {
    private static final String TAG = "ScanActivity";

    // intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 9001;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 5;
    private static final int RC_ACCESS_LOCATION = 6;
    private static final int RC_READ_PHONE_STATE = 7;
    private static final int REQ_CODE_MARKET = 2;

    @RestService
    RestClient restClient;

    @Bean(UserServiceImpl.class)
    UserService userService;
    @Bean(ScanActivityPresenterImpl.class)
    ScanActivityPresenter presenter;


    @Pref
    Preferences_ preferences;

    @ViewById(R.id.preview)
    CameraSourcePreview preview;
    @ViewById(R.id.graphicOverlay)
    GraphicOverlay<GraphicOverlay.Graphic> overlay;
    @ViewById(R.id.qr_countdown)
    TextView qrCountdown;
    @ViewById(R.id.qr_progress)
    NumberProgressBar qrProgress;
    @ViewById(R.id.qr_choose_workers)
    SeekBar seekWorkers;
    @ViewById(R.id.qr_seek_value)
    TextView workAmountText;
    @ViewById(R.id.material_image)
    ImageView materialImage;
    @ViewById(R.id.qr_material_used)
    ViewGroup materialUsedLayout;

    private MaterialUsedView workersView;
    private MaterialUsedView woodView;
    private MaterialUsedView stoneView;

    private CameraSource cameraSource;
    private BarcodeTrackerFactory barcodeFactory;
    private Thread scanThread;

    private Place place;

    private ArrayList<Item> possibleItems = new ArrayList<>();

    //Flag indicates that camera permission was granted and scanning was started
    private boolean scanStarted = false;
    private boolean cameraGranted = false;
    private boolean locationGranted = false;
    private boolean phoneStateGranted = false;

    @AfterViews
    public void init() {
        EventBus.getDefault().register(this);
        checkPermissions();
        startScan();
    }

    private void checkPermissions(){
        int permissionCamera = ContextCompat.checkSelfPermission(this, CAMERA);
        if (permissionCamera == PERMISSION_GRANTED) {
            cameraGranted = true;
        } else {
            requestPerm(CAMERA, RC_HANDLE_CAMERA_PERM, R.string.permission_camera_rationale);
        }

        int permissionLocation = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
        if (permissionLocation == PERMISSION_GRANTED) {
            locationGranted = true;
        } else {
            requestPerm(ACCESS_COARSE_LOCATION, RC_ACCESS_LOCATION, R.string.permission_location);
        }

        int permissionPhoneState = ContextCompat.checkSelfPermission(this, READ_PHONE_STATE);
        if (permissionPhoneState == PERMISSION_GRANTED){
            phoneStateGranted = true;
        } else {
            requestPerm(READ_PHONE_STATE, RC_READ_PHONE_STATE, R.string.permission_read_phone_state);
        }
    }

    private void startScan() {
        if (cameraGranted && locationGranted && phoneStateGranted) {
            createCameraSource(false);
            createScanThread();
            presenter.init(this);
            scanStarted = true;
        } else {
            checkPermissions();
        }
    }

    @UiThread
    void changeVisibility(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;

        seekWorkers.setVisibility(visibility);
        workAmountText.setVisibility(visibility);
        qrCountdown.setVisibility(visibility);
        qrProgress.setVisibility(visibility);
        materialImage.setVisibility(visibility);
        if (materialUsedLayout.getChildCount() > 0) {
            materialUsedLayout.removeAllViews();
        }
        materialUsedLayout.setVisibility(visibility);
    }

    private void createScanThread() {
        scanThread = new Thread(new Runnable() {
            @Override
            public void run() {
                BarcodeGraphicTracker tracker = barcodeFactory.getTracker();
                while (place == null) {
                    while (tracker == null || tracker.getBarcode() == null) {
                        tracker = barcodeFactory.getTracker();
                    }
                    place = presenter.getPlace(getPlaceCode());
                }
                changeVisibility(true);
                presenter.startTimer(place, getApplicationContext());
                if (place != null) { // scan could be being interrupted just now -> place is set to null and thread is being interrupted
                    ActivityEnum activity = place.getPlaceType().getActivity();
                        loadImage();
                    showActivity(activity);
                }
            }
        });
    }

    @UiThread
    void loadImage(){
        Glide.with(getApplicationContext())
                .load(Constants.IMG_URL_BASE + PlaceUtils.getIconName(place))
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BarcodeGraphic.placeIcon = resource;
                    }
                });
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
        changeVisibility(false);
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (scanStarted) {
            if (preview != null) {
                preview.stop();
            }
            presenter.destroy();
            changeVisibility(false);
            if (!scanThread.isInterrupted()) {
                scanThread.interrupt();
            }
            place = null;
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (preview != null) {
            preview.release();
        }
    }

    private void requestPerm(String permission, final int code, @StringRes int permissionString) {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{permission};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                permission)) {
            ActivityCompat.requestPermissions(this, permissions, code);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        code);
            }
        };

        Snackbar.make(overlay, permissionString,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_HANDLE_CAMERA_PERM:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    cameraGranted = true;
                    startScan();
                } else {
                    finish();
                }
                break;
            case RC_ACCESS_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    locationGranted = true;
                    startScan();
                } else {
                    finish();
                }
                break;
            case RC_READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults [0] == PERMISSION_GRANTED){
                    phoneStateGranted = true;
                    startScan();
                } else {
                    finish();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void startTracking() {
        presenter.createTimer();
        if (!scanThread.getState().equals(Thread.State.RUNNABLE)) {
            scanThread.start();
        }
    }

    @UiThread
    @Override
    public void updateCountdown(long millisLeft) {
        int secondsLeft = (int) millisLeft / 1000;
        qrCountdown.setText(getString(R.string.qr_countdown, secondsLeft));
        qrProgress.setProgress(10 - secondsLeft);
    }

    @Override
    public void stopCountDown() {
        changeVisibility(false);
        scanThread.interrupt();
        place = null;
    }

    @Subscribe
    public void onUserMoved(UserMovedEvent event) {
        presenter.stopTimer();
    }

    @Override
    @UiThread
    public void onCountdownFinished() {
        Log.i(TAG, "Timer finished, starting activity");
        if (place != null) {
            switch (place.getPlaceType().getActivity()) {
                case BUILD:
                case MINE:
                    startActivity();
                    break;
                case BUY:
                    MarketActivity_.intent(this).items(possibleItems).startForResult(REQ_CODE_MARKET);
                    break;
            }
        }
    }

    @Background
    public void startActivity() {
        AppUser user = restClient.startActivity(Integer.valueOf(seekWorkers.getProgress()), place);
        finishScanActivity(user);
    }

    @OnActivityResult(REQ_CODE_MARKET)
    public void returnToMap(@OnActivityResult.Extra(value = Constants.EXTRA_ITEMS) ArrayList<Item> items) {
        buyItems(items);
    }

    @Background
    void buyItems(List<Item> items) {
        AppUser user = restClient.buyItem(items); //Prida item do DB a vrati aktualizovaneho uzivatele
        finishScanActivity(user);
    }

    private void finishScanActivity(AppUser user){
        userService.updateUser(user);
        Intent i = new Intent();
        i.putExtra(Constants.EXTRA_FLOOR, place.getFloor());
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private void showActivity(ActivityEnum activity) { //FIXME: prejmenovat
        //TODO: upravit, pokud uz uzivatel na danem miste ma spustenou aktivitu -> ukazat aktualni pocet
        switch (activity) {
            case MINE:
                Inventory workers = userService.getWorkers();
                showWorkersSeek(workers);
                break;
            case BUILD:
                Inventory wood = userService.getInventory(Constants.MATERIAL_WOOD);
                Inventory stone = userService.getInventory(Constants.MATERIAL_STONE);
                int woodAmount = wood.getAmount().intValue() / Constants.BUILD_WOOD;
                int stoneAmount = stone.getAmount().intValue() / Constants.BUILD_STONE;
                showSeekBar(Math.min(woodAmount, stoneAmount));
                break;
            case BUY:
                possibleItems = (ArrayList<Item>) restClient.getPossibleItems();
                for (Item item : possibleItems) {
                    String itemUrl = item.getImgUrl();
                    try {
                        new BitmapWorkerTask(itemUrl, this.getApplicationContext(), AppUtils.getVersionCode(this)).execute().get();
                    } catch (InterruptedException | ExecutionException e) {
                        Log.e(TAG, "Could not download image", e);
                    }
                }
                break;
            default:
                //TODO: informovat o nezname aktivite
                break;
        }
    }

    @UiThread
    void showWorkersSeek(Inventory workers) {
        int workersAmount = workers.getAmount().intValue();
        seekWorkers.setMax(workersAmount);
        seekWorkers.setProgress(workersAmount / 2);
        seekWorkers.setVisibility(View.VISIBLE);
        workAmountText.setVisibility(View.VISIBLE);

        workersView = MaterialUsedView_.build(this);
        workersView.setMaterialImage(Constants.ICON_WORKER);
        workersView.setMaterialUsed(String.valueOf(workersAmount / 2));
        materialUsedLayout.addView(workersView);
    }

    @UiThread
    void showSeekBar(int maxValue) {
        seekWorkers.setMax(maxValue);
        seekWorkers.setProgress(maxValue / 2);
        seekWorkers.setVisibility(View.VISIBLE);
        workAmountText.setVisibility(View.VISIBLE);

        woodView = MaterialUsedView_.build(this);
        woodView.setMaterialImage(Constants.ICON_WOOD);
        woodView.setMaterialUsed(String.valueOf(maxValue / 2 * Constants.BUILD_WOOD));
        materialUsedLayout.addView(woodView);
        System.out.println("ADDING WOOD");

        stoneView = MaterialUsedView_.build(this);
        stoneView.setMaterialImage(Constants.ICON_STONE);
        stoneView.setMaterialUsed(String.valueOf(maxValue / 2 * Constants.BUILD_STONE));
        materialUsedLayout.addView(stoneView);
        System.out.println("ADDING STONE");
    }

    @SeekBarProgressChange(R.id.qr_choose_workers)
    void updateAmountText() {
        workAmountText.setText(seekWorkers.getProgress() + "/" + seekWorkers.getMax());
        if (workersView != null) {
            workersView.setMaterialUsed(String.valueOf(seekWorkers.getProgress()));
        }
        if (stoneView != null) {
            stoneView.setMaterialUsed(String.valueOf(seekWorkers.getProgress() * Constants.BUILD_STONE));
        }
        if (woodView != null){
            woodView.setMaterialUsed(String.valueOf(seekWorkers.getProgress() * Constants.BUILD_WOOD));
        }
    }

    @Override
    public String getPlaceCode() {
        Barcode barcode = getActualBarcode();
        if (barcode != null) {
            String url = barcode.rawValue;
            return url.substring(url.lastIndexOf("/") + 1);
        }
        return null;
    }

    /**
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
    private void createCameraSource(boolean useFlash) {
        Context context = getApplicationContext();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).setBarcodeFormats(QR_CODE).build();
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
                .setRequestedPreviewSize(1080, 1920)
                .setRequestedFps(15.0f);

        cameraSource = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }
}
