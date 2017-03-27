package cz.hanusova.fingerprint_game.utils;

import android.widget.EditText;

import org.androidannotations.annotations.EBean;

/**
 * Created by Kristyna on 27/03/2017.
 */
@EBean
public class ValidationUtils {


    public Boolean isNotEmpty(EditText editText) {
        return editText.getText().toString().length() > 0 ? true : setMandatoryError(editText);
    }

    private Boolean setMandatoryError(EditText editText) {
        editText.setError("Pole je povinné!");
        return false;
    }
}
