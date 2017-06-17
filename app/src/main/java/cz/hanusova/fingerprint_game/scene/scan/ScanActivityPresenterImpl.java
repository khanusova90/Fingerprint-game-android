package cz.hanusova.fingerprint_game.scene.scan;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Date;
import java.util.List;

import cz.hanusova.fingerprint_game.listener.ScanResultListener;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.model.fingerprint.BleScan;
import cz.hanusova.fingerprint_game.model.fingerprint.CellScan;
import cz.hanusova.fingerprint_game.model.fingerprint.Fingerprint;
import cz.hanusova.fingerprint_game.model.fingerprint.WifiScan;
import cz.hanusova.fingerprint_game.rest.RestClient;
import cz.hanusova.fingerprint_game.scan.DeviceInformation;
import cz.hanusova.fingerprint_game.scan.Scanner;
import cz.hanusova.fingerprint_game.scan.SensorScanner;
import cz.hanusova.fingerprint_game.utils.AppUtils;

/**
 * Created by khanusova on 16/06/2017.
 */
@EBean
public class ScanActivityPresenterImpl implements ScanActivityPresenter {
    private static final String TAG = "ScanAcPresenter";
    private ScanActivityView view;

    @RestService
    RestClient restClient;

    private BluetoothAdapter bluetoothAdapter;
    private WifiManager wm;
    private CountDownTimer timer;
    private Scanner scanner;

    private boolean wasBTEnabled;
    private boolean wasWifiEnabled;

    @Override
    public void onAttach(ScanActivityView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void init(Context context){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wasBTEnabled = bluetoothAdapter.isEnabled();
        wasWifiEnabled = wm.isWifiEnabled();
        changeBTWifiState(true);
        scanner = new Scanner(context);
        view.startTracking();
    }

    public void destroy(){
        changeBTWifiState(false);
        timer.cancel();
        scanner.stopScan();
    }

    /**
     * Changes bluetooth and wifi state
     *
     * @param enable <code>true</code> if wifi and bluetooth should be enabled
     * @return <code>true</code> if state was changed properly
     */
    private boolean changeBTWifiState(boolean enable) {
        if (enable) {
            if (!wasBTEnabled && !wasWifiEnabled) {
                wm.setWifiEnabled(true);
                return bluetoothAdapter.enable();
            } else if (!wasBTEnabled) {
                return bluetoothAdapter.enable();
            } else
                return wasWifiEnabled || wm.setWifiEnabled(true);
        } else {
            if (!wasBTEnabled && !wasWifiEnabled) {
                wm.setWifiEnabled(false);
                return bluetoothAdapter.disable();
            } else if (!wasBTEnabled) {
                return bluetoothAdapter.disable();
            } else
                return wasWifiEnabled || wm.setWifiEnabled(false);
        }
    }

    @Override
    public void createTimer(){
        timer = new Timer(10 * 1000, 1000);
    }

    @Override
    public void startTimer(final Place place, final Context context){
        timer.start();
        scanner.startScan(10000, new ScanResultListener() {
            @Override
            public void onScanFinished(List<WifiScan> wifiScans, List<BleScan> bleScans, List<CellScan> cellScans) {
                Log.d(TAG, "Received onScanfinish, wifi = " + wifiScans.size() + ", ble = " + bleScans.size() + ", gsm = " + cellScans.size());
                Fingerprint fingerprint = createFingerprint(wifiScans, bleScans, cellScans, place, context);
                restClient.sendFingerprint(fingerprint);
            }
        });
    }

    /**
     * Creates new fingerprint
     *
     * @param wifiScans
     * @param bleScans
     * @param cellScans
     * @param place
     * @return {@link Fingerprint} filled with information about scans
     */
    private Fingerprint createFingerprint(List<WifiScan> wifiScans, List<BleScan> bleScans, List<CellScan> cellScans, Place place, Context context) {
        Fingerprint p = new Fingerprint();
        p.setWifiScans(wifiScans);
        p.setBleScans(bleScans); // naplnime daty z Bluetooth
        p.setCellScans(cellScans);
        new SensorScanner(context).fillPosition(p); // naplnime daty ze senzoru
        new DeviceInformation(context).fillPosition(p); // naplnime infomacemi o zarizeni
        p.setCreatedDate(new Date());
        p.setLevel(String.valueOf(place.getFloor()));
        p.setX(place.getxCoord());
        p.setY(place.getyCoord());
        p.setClientVersion(AppUtils.getVersionName(context));
        Log.d(TAG, "New fingerprint: " + p.toString());
        return p;
    }

    private class Timer extends CountDownTimer{
        String code;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            Log.d(TAG, "Timer created");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            view.updateCountdown(millisUntilFinished);
            if (code == null){
                code = view.getPlaceCode();
            }
            String actualCode = view.getPlaceCode();
            if (code == null || actualCode == null|| !code.equals(actualCode)){
                Log.d(TAG, "Capturing stopped");
                view.stopCountDown();
                view.startTracking();
                scanner.stopScan();
                cancel();
            }
        }

        @Override
        public void onFinish() {
            view.onCountdownFinished();
        }
    }
}
