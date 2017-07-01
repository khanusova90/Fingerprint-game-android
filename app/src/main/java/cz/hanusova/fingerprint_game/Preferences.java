package cz.hanusova.fingerprint_game;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by khanusova on 10.10.2016.
 */
@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface Preferences {

    @DefaultString("")
    String username();

    @DefaultString("")
    String password();

    @DefaultString("")
    String user();

    @DefaultBoolean(false)
    boolean stayIn();
}
