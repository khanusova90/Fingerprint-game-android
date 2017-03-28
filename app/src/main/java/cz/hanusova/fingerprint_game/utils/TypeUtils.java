package cz.hanusova.fingerprint_game.utils;

import org.androidannotations.annotations.EBean;

import java.math.BigDecimal;

/**
 * Created by Kristyna on 28/03/2017.
 */
@EBean
public class TypeUtils {


    public int getInt(String string){
        return Integer.valueOf(string);
    }
    public int getInt(CharSequence string){
        return Integer.parseInt(string.toString());
    }
    public String getString(int integer){
        return String.valueOf(integer);
    }
    public String getString(BigDecimal integer){
        return String.valueOf(integer);
    }


}
