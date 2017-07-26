package cz.hanusova.fingerprint_game.scene.scan;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Date;
import java.util.List;

import cz.hanusova.fingerprint_game.base.utils.AppUtils;
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
import cz.hanusova.fingerprint_game.scene.scan.service.GyroscopeService;
import cz.hanusova.fingerprint_game.scene.scan.service.impl.GyroscopeServiceImpl;

import static android.content.Context.SENSOR_SERVICE;
import static android.hardware.Sensor.TYPE_GYROSCOPE;

/**
 * Created by khanusova on 16/06/2017.
 */
@EBean
public class ScanActivityPresenterImpl implements ScanActivityPresenter {
    private static final String TAG = "ScanAcPresenter";

    @RestService
    RestClient restClient;

    @Bean(GyroscopeServiceImpl.class)
    GyroscopeService gyroscopeService;

    private ScanActivityView view;
    private BluetoothAdapter bluetoothAdapter;
    private WifiManager wm;
    private CountDownTimer timer;
    private Scanner scanner;
    private SensorManager sensorManager;
    private Sensor gyroscope;

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
    public void init(Context context) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(TYPE_GYROSCOPE);
        wasBTEnabled = bluetoothAdapter.isEnabled();
        wasWifiEnabled = wm.isWifiEnabled();
        changeBTWifiState(true);
        scanner = new Scanner(context);
        view.startTracking();
    }

    public void destroy() {
        changeBTWifiState(false);
        timer.cancel();
        scanner.stopScan();
        sensorManager.unregisterListener(gyroscopeService, gyroscope);
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
    public Place getPlace(String code) {
        if (code != null) {
            Log.d(TAG, "Getting place with code " + code);
            Place place = restClient.getPlaceByCode(code);
            return place;
        }
        return null;
    }

    @Override
    public void createTimer() {
        timer = new Timer(10 * 1000, 1000);
    }

    @Override
    public void startTimer(final Place place, final Context context) {
        timer.start();
        sensorManager.registerListener(gyroscopeService, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
        scanner.startScan(10000, new ScanResultListener() {
            @Override
            public void onScanFinished(List<WifiScan> wifiScans, List<BleScan> bleScans, List<CellScan> cellScans) {
                Log.d(TAG, "Received onScanfinish, wifi = " + wifiScans.size() + ", ble = " + bleScans.size() + ", gsm = " + cellScans.size());
                Fingerprint fingerprint = createFingerprint(wifiScans, bleScans, cellScans, place, context);
                restClient.sendFingerprint(fingerprint);
            }
        });
    }

    @Override
    public void stopTimer() {
        sensorManager.unregisterListener(gyroscopeService, gyroscope);
        scanner.stopScan();
        timer.cancel();
        view.stopCountDown();
        view.startTracking();
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

    private class Timer extends CountDownTimer {

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
        }

        @Override
        public void onFinish() {
            view.onCountdownFinished();
            sensorManager.unregisterListener(gyroscopeService, gyroscope);
        }
    }
}
