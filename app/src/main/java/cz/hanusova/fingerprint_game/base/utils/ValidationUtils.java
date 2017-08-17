package cz.hanusova.fingerprint_game.base.utils;

import android.widget.EditText;

/**
 * Util class for validations
 *
 * Created by Kristyna on 27/03/2017.
 */
public final class ValidationUtils {

    private ValidationUtils() {
    }


    public static Boolean isNotEmpty(EditText editText) {
        return editText.getText().toString().length() > 0 ? true : setMandatoryError(editText);
    }

    private static Boolean setMandatoryError(EditText editText) {
        editText.setError("Pole je povinné!");
        return false;
    }
}
