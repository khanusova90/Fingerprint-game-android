package cz.hanusova.fingerprint_game.utils;

/**
 * Util class with constants for application
 * <p>
 * Created by khanusova on 21.3.2016.
 */
public final class Constants {

//      public static final String URL_BASE = "http://10.0.0.53:8080/fingerprint-game/android/1.0";
//    public static final String IMG_URL_BASE = "http://192.168.0.103:8080/fingerprint-game/disk-resources/";

    //Emulator
//    public static final String URL_BASE = "http://10.0.2.2:8080/fingerprint-game/android/1.0";
//    public static final String IMG_URL_BASE = "http://10.0.2.2:8080/fingerprint-game/disk-resources/";

    //prod
    public static final String URL_BASE = "http://beacon.uhk.cz/fingerprint-game/android/1.0";
    public static final String IMG_URL_BASE = "http://beacon.uhk.cz/fingerprint-game/disk-resources/";
    public static final String STAG_API_BASE_URL = "https://stagws.uhk.cz/ws/services/rest/";



    //Name of app for SharedPreferences
    public static final String SP_NAME = "fingerprint_game";

    public static final String EXTRA_PLACE = "place"; //TODO: pouziva se?
    public static final String EXTRA_USER = "user";
    public static final String EXTRA_ACTIVITIES = "activities";
    public static final String EXTRA_ERROR = "error";

    public static final String MATERIAL_WORKER = "WORKER";
    public static final String MATERIAL_WOOD = "WOOD";
    public static final String MATERIAL_STONE = "STONE";

    public static final int BUILD_WOOD = 10;
    public static final int BUILD_STONE = 10;

    private Constants() {
    }
}
