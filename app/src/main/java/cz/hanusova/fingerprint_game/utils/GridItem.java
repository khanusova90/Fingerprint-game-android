package cz.hanusova.fingerprint_game.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by Kristyna on 16/02/2017.
 */

public class GridItem {
    String amount;
    Drawable imageDrawable;

    public GridItem(String amount, Drawable imageDrawable) {
        this.amount = amount;
        this.imageDrawable = imageDrawable;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(Drawable imageDrawable) {
        this.imageDrawable = imageDrawable;
    }
}
