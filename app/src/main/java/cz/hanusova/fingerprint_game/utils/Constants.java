package cz.hanusova.fingerprint_game.utils;

/**
 * Util class with constants for application
 *
 * Created by khanusova on 21.3.2016.
 */
public final class Constants {

    public static final String URL_BASE = "http://192.168.0.106:8080/fingerprint-game/android/1.0";
    public static final String IMG_URL_BASE = "http://192.168.0.106:8080/fingerprint-game/disk-resources/";

    //Emulator
//    public static final String URL_BASE = "http://10.0.2.2:8080/fingerprint-game/android/1.0";
//    public static final String IMG_URL_BASE = "http://10.0.2.2:8080/fingerprint-game/disk-resources/";

    //prod
//    public static final String URL_BASE = "http://beacon.uhk.cz/fingerprint-game/android/1.0";
//    public static final String IMG_URL_BASE = "http://beacon.uhk.cz/fingerprint-game/disk-resources/";

    //Name of app for SharedPreferences
    public static final String SP_NAME = "fingerprint_game";

    public static final String EXTRA_PLACE = "place"; //TODO: pouziva se?
    public static final String EXTRA_USER = "user";

    public static final String MATERIAL_WORKER = "WORKER";

    private Constants() {
    }
}
