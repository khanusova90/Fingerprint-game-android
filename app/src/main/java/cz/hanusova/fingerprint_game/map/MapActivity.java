package cz.hanusova.fingerprint_game.map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import com.github.clans.fab.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.hanusova.fingerprint_game.FingerprintApplication;
import cz.hanusova.fingerprint_game.LoginActivity_;
import cz.hanusova.fingerprint_game.Preferences_;
import cz.hanusova.fingerprint_game.QrActivity_;
import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.UserDetailActivity_;
import cz.hanusova.fingerprint_game.base.BasePresenter;
import cz.hanusova.fingerprint_game.base.ui.BaseActivity;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.view.TouchImageView;

/**
 * Created by khanusova on 6.6.2016.
 * <p>
 * Activity for displaying map
 */
@EActivity(R.layout.map)
@OptionsMenu(R.menu.map_toolbar_menu)
public class MapActivity extends BaseActivity implements MapActivityView {
    private static final String TAG = "MapActivity";

    private static final int REQ_CODE_QR = 1;
    private static final int ICON_SIZE = 8;
    private static final int MAP_HEIGHT = 2800;
    private static final int MAP_WIDTH = 2600;

    @ViewById(R.id.img_map)
    TouchImageView mapView;
    @ViewById(R.id.action_floor_up)
    FloatingActionButton buttonFloorUp;
    @ViewById(R.id.action_floor_down)
    FloatingActionButton buttonFloorDown;

    @Pref
    Preferences_ preferences;

    @Inject
    MapActivityPresenter presenter;

    private int currentFloor = 1;  // 1 - 4 NP, not 0 - 3
    private List<Drawable> icons = new ArrayList<>();
    private Bitmap[] mapField = new Bitmap[4];

    @AfterViews
    void init() {
        setTitle(currentFloor + ". patro");
        presenter.createIcons(this, currentFloor);
        presenter.createMap(currentFloor, this);
        buttonFloorDown.setEnabled(!(currentFloor == 1));
        buttonFloorUp.setEnabled(!(currentFloor == 4));
    }

    @Override
    @UiThread
    public void updateView() {
        Log.d(TAG, "Updating map view");
        final Drawable mapDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(mapField[currentFloor - 1], MAP_WIDTH, MAP_HEIGHT, true));
        changeIconPosition(mapDrawable, createLayers(mapDrawable));
    }

    @Override
    public void setMapField(Bitmap[] mapField){
        this.mapField = mapField;
    }

    @Override
    public void updateIcons(List<Drawable> icons) {
        this.icons = icons;
    }

    private LayerDrawable createLayers(Drawable mapDrawable) {
        Drawable[] layers = new Drawable[icons != null ? icons.size() + 1 : 1];
        layers[0] = mapDrawable;
        for (int i = 1; i < layers.length; i++) {
            layers[i] = icons.get(i - 1);
        }
        return new LayerDrawable(layers);
    }

    private void changeIconPosition(Drawable mapDrawable, LayerDrawable ld) {
        List<Place> places = presenter.getPlaces(currentFloor);
        if (places == null || icons.isEmpty()) {
            mapView.setImageDrawable(ld);
            return;
        }
        System.out.println("PLACES SIZE " + places.size());
        for (int i = 0; i < places.size(); i++) {
            Place p = places.get(i);
            int x = p.getxCoord();
            int y = p.getyCoord();
            ld.setLayerInset(i + 1, x, y, mapDrawable.getIntrinsicWidth() - x + ICON_SIZE, mapDrawable.getIntrinsicHeight() - y + ICON_SIZE);
        }
        mapView.setImageDrawable(ld);
        mapView.setPlaces(places);
        mapView.setFragmentManager(getSupportFragmentManager());
    }

    @Override
    public void inject() {
        FingerprintApplication.instance.getComponent().inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }
    @OptionsItem
    void action_map_logout() {
        preferences.clear();
        LoginActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
        finish();
    }
    @Click(R.id.action_camera)
    void startCamera() {
        QrActivity_.intent(this).startForResult(REQ_CODE_QR);
    }

    @Click(R.id.action_floor_down)
    void goDown() {
        currentFloor = currentFloor > 1 ? --currentFloor : currentFloor;
        init();
    }

    @Click(R.id.action_floor_up)
    void goUp() {
        currentFloor = currentFloor < 4 ? ++currentFloor : currentFloor;
        init();
    }

    @Click(R.id.action_profile)
    void goToProfile() {
        //TODO: proč new task?
        UserDetailActivity_.intent(this).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
    }
}
