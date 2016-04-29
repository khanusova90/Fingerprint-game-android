package cz.hanusova.fingerprint_game.utils;

/**
 * Trida pro ukladani konstant potrebnych pro celou aplikaci
 *
 * Created by khanusova on 21.3.2016.
 */
public final class Constants {

//    public static final String URL_BASE = "http://localhost:8080/fingerprint-game/android";

    public static final String URL_BASE = "http://192.168.0.101:8080/fingerprint-game/android";

    //Emulator
//    public static final String URL_BASE = "http://10.0.2.2:8080/fingerprint-game/android";

    //Name of app for SharedPreferences
    public static final String SP_NAME = "fingerprint_game";

    public static final String EXTRA_PLACE = "place";

    private Constants() {
    }
}
