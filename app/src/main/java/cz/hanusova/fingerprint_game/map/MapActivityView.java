package cz.hanusova.fingerprint_game.map;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.List;

import cz.hanusova.fingerprint_game.base.BaseView;

/**
 * Created by khanusova on 31.5.2017.
 */

public interface MapActivityView extends BaseView {

    void updateView();

    void setMapField(Bitmap[] mapField);

    void updateIcons(List<Drawable> icons);
}
