package cz.hanusova.fingerprint_game;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.LruCache;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.messaging.FirebaseMessaging;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.model.UserActivity;
import cz.hanusova.fingerprint_game.service.UserService;
import cz.hanusova.fingerprint_game.service.impl.UserServiceImpl;
import cz.hanusova.fingerprint_game.utils.Constants;
import cz.hanusova.fingerprint_game.view.TouchImageView;

/**
 * Created by khanusova on 6.6.2016.
 * <p>
 * Activity for displaying map
 */
@EActivity(R.layout.map)
public class MapActivity extends AbstractAsyncActivity {
    private static final String TAG = "MapActivity";
    private static final int REQ_CODE_QR = 1;
    private static final int ICON_SIZE = 32;
    private static final int MAP_HEIGHT = 2800;
    private static final int MAP_WIDTH = 2600;
    //TODO: fix gonna be in version solving ticket
    private int APP_VERSION = 2;
    private String CURRENT_FLOOR = "1NP.jpg";


    @Bean(UserServiceImpl.class)
    UserService userService;

    @ViewById(R.id.img_map)
    TouchImageView mapView;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @AfterViews
    void init() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Bitmap bitmap = null;
        try {
            bitmap = new cz.hanusova.fingerprint_game.task.BitmapWorkerTask(CURRENT_FLOOR, this.getApplicationContext(), APP_VERSION)
                    .execute().get();
        } catch (InterruptedException e) {
            //TODO: handle
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Drawable mapDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, MAP_WIDTH, MAP_HEIGHT, true));
        AppUser user = userService.getActualUser();
        List<Drawable> icons = getIcons(user.getPlaces());
        LayerDrawable layers = createLayers(mapDrawable, icons);
        changeIconPosition(mapDrawable, user.getPlaces(), layers);

    }

    private List<Drawable> getIcons(List<Place> places) {
        List<Drawable> icons = new ArrayList<>();
        for (Place p : places) {
            try {
                String iconName = p.getPlaceType().getImgUrl();
                if (iconName == null) {
                    iconName = p.getMaterial().getIconName();
                }
                Bitmap bitmap = new cz.hanusova.fingerprint_game.task.BitmapWorkerTask(iconName, this.getApplicationContext(), APP_VERSION)
                        .execute()
                        .get();

                Drawable icon = new BitmapDrawable(getResources(), bitmap);
                icons.add(icon);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return icons;
    }

    private LayerDrawable createLayers(Drawable mapDrawable, List<Drawable> icons) {
        Drawable[] layers = new Drawable[icons.size() + 1];
        layers[0] = mapDrawable;
        for (int i = 0; i < icons.size(); i++) {
            Drawable icon = icons.get(i);
            layers[i + 1] = icon;
        }
        return new LayerDrawable(layers);
    }

    private void changeIconPosition(Drawable mapDrawable, List<Place> places, LayerDrawable ld) {
        int width = mapDrawable.getIntrinsicWidth();
        int height = mapDrawable.getIntrinsicHeight();

        for (int i = 0; i < places.size(); i++) {
            Place p = places.get(i);
            int x = p.getxCoord();
            int y = p.getyCoord();
            ld.setLayerInset(i + 1, x, y, width - x - ICON_SIZE, height - y - ICON_SIZE);
        }
        mapView.setImageDrawable(ld);
    }


    @Click(R.id.map_camera)
    void startCamera() {
        QrActivity_.intent(this).startForResult(REQ_CODE_QR);
    }

    @OnActivityResult(REQ_CODE_QR)
    void showActivitiesUpdate(int resultCode, @OnActivityResult.Extra(value = Constants.EXTRA_ACTIVITIES) ArrayList<UserActivity> activities) {
        //TODO: zobrazit aktivity
        System.out.println("ZOBRAZOVANI AKTIVIT");
    }

}
