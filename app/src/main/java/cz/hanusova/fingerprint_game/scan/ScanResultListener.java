package cz.hanusova.fingerprint_game.scan;

import java.util.List;

import cz.hanusova.fingerprint_game.model.fingerprint.BleScan;
import cz.hanusova.fingerprint_game.model.fingerprint.CellScan;
import cz.hanusova.fingerprint_game.model.fingerprint.WifiScan;

/**
 * Interface pro prijem udalosti v prubehu skenovani.
 * <p>
 * created by vojtele1
 */
public interface ScanResultListener {

    /**
     * Zavolano pri dokonceni skenovani. Je volano z jineho vlakna proto v MUSI byt v implementaci kod spusten na spravnem (UI) vlakne
     *
     * @param wifiScans Seznam naskenovanych wifin
     * @param bleScans  Seznam naskenovanych BLE beaconu
     * @param cellScans Seznam ziskanych bts informaci
     */
    void onScanFinished(List<WifiScan> wifiScans, List<BleScan> bleScans, List<CellScan> cellScans);
}
