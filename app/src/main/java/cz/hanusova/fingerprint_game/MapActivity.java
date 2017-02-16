package cz.hanusova.fingerprint_game;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.messaging.FirebaseMessaging;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
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
@OptionsMenu(R.menu.map_toolbar_menu)
public class MapActivity extends AppCompatActivity {
    private static final String tag = "MapActivity";
    private static final int REQ_CODE_QR = 1;
    private static final int ICON_SIZE = 16;
    private static final int MAP_HEIGHT = 2800;
    private static final int MAP_WIDTH = 2600;
    @Bean(UserServiceImpl.class)
    UserService userService;

    @ViewById(R.id.img_map)
    TouchImageView mapView;

    @ViewById(R.id.action_menu)
    FloatingActionMenu floatingActionMenu;

    @ViewById(R.id.action_floor_up)
    FloatingActionButton buttonFloorUp;

    @ViewById(R.id.action_floor_down)
    FloatingActionButton buttonFloorDown;

    @ViewById(R.id.action_camera)
    FloatingActionButton buttonCamera;

    @ViewById(R.id.action_profile)
    FloatingActionButton buttonProfile;

    @ViewById(R.id.action_menu)
    FloatingActionMenu buttonActionMenu;


    @OptionsMenuItem(R.id.action_map_options)
    MenuItem optionsItem;
    //TODO: fix gonna be in version solving ticket
    private int APP_VERSION = 4;
    private String CURRENT_FLOOR = "1NP.jpg";
    private int currentFloor = 1;  // 1 - 4 NP, not 0 - 3
    private Drawable[] layers;
    private Bitmap[] mapField = new Bitmap[4];


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @AfterViews
    void init() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Thread exception", paramThrowable.getMessage());
            }
        });


        this.setTitle(currentFloor + ". patro");

        if (mapField[currentFloor - 1] == null) {
            try {
                mapField[currentFloor - 1] = new cz.hanusova.fingerprint_game.task.BitmapWorkerTask(getFloorName(currentFloor), this.getApplicationContext(), APP_VERSION)
                        .execute().get();
            } catch (InterruptedException e) {
                //TODO: handle
                e.printStackTrace();

            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        Drawable mapDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(mapField[currentFloor - 1], MAP_WIDTH, MAP_HEIGHT, true));
        AppUser user = userService.getActualUser();
        List<Place> places = user.getPlacesByFloor(currentFloor);
        if (places != null) {
            List<Drawable> icons = getIcons(places);
            LayerDrawable layers = createLayers(mapDrawable, icons);
            changeIconPosition(mapDrawable, places, layers);
        }

        buttonFloorDown.setEnabled(!(currentFloor == 1));
        buttonFloorUp.setEnabled(!(currentFloor == 4));


    }

    @OptionsItem
    void action_map_options() {

        //TODO: app options menu
    }

    public String getFloorName(int currentFloor) {
        return currentFloor + "NP.jpg";
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
        layers = new Drawable[icons.size() + 1];
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
            ld.setLayerInset(i + 1, x, y, width - x + ICON_SIZE, height - y + ICON_SIZE);
        }
        mapView.setImageDrawable(ld);
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

//    @Click(R.id.action_profile)
//    void goToProfile() {
//        UserDetailActivity_.intent(this.getApplicationContext()).start();
//    }


    @OnActivityResult(REQ_CODE_QR)
    void showActivitiesUpdate(int resultCode, @OnActivityResult.Extra(value = Constants.EXTRA_ACTIVITIES) ArrayList<UserActivity> activities) {
        //TODO: zobrazit aktivity
        System.out.println("ZOBRAZOVANI AKTIVIT");
    }

}
