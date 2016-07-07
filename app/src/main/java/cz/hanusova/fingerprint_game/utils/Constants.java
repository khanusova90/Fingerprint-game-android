package cz.hanusova.fingerprint_game.utils;

/**
 * Util class with constants for application
 *
 * Created by khanusova on 21.3.2016.
 */
public final class Constants {

    public static final String URL_BASE = "http://192.168.0.101:8080/fingerprint-game/android/1.0";

    public static final String IMG_URL_BASE = "http://192.168.0.101:8080/fingerprint-game/disk-resources/";

    //Emulator
//    public static final String URL_BASE = "http://10.0.2.2:8080/fingerprint-game/android";

    //Name of app for SharedPreferences
    public static final String SP_NAME = "fingerprint_game";

    public static final String EXTRA_PLACE = "place";
    public static final String EXTRA_USER = "user";

    private Constants() {
    }
}
