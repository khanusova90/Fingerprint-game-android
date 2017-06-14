package cz.hanusova.fingerprint_game.utils;

import android.widget.EditText;

/**
 * Created by Kristyna on 27/03/2017.
 */
public final class ValidationUtils {

    private ValidationUtils(){}


    public static Boolean isNotEmpty(EditText editText) {
        return editText.getText().toString().length() > 0 ? true : setMandatoryError(editText);
    }

    private static Boolean setMandatoryError(EditText editText) {
        editText.setError("Pole je povinné!");
        return false;
    }
}
