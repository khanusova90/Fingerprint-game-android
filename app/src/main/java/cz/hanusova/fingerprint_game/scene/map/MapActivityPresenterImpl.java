package cz.hanusova.fingerprint_game.scene.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.base.service.UserService;
import cz.hanusova.fingerprint_game.base.service.impl.UserServiceImpl;
import cz.hanusova.fingerprint_game.base.utils.AppUtils;
import cz.hanusova.fingerprint_game.base.utils.Constants;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.task.BitmapWorkerTask;

/**
 * Created by khanusova on 31.5.2017.
 */
@EBean
public class MapActivityPresenterImpl implements MapActivityPresenter {
    private static final String TAG = "MapActivityPresenter";
    @Bean(UserServiceImpl.class)
    UserService userService;
    private MapActivityView view;
    private List<Place> places;

    @Override
    public void createMap(int currentFloor, Context context) {
        Log.d(TAG, "creating map");
        String drawableName = "j" + currentFloor + "np";
        int drawableId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
//        Bitmap map = BitmapFactory.decodeResource(context.getResources(), drawableId);
//        downloadMap(currentFloor, context);

//        private void createGlideMap(){
//            String drawableName = "j" + currentFloor + "np";
//            int drawableId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
            Glide.with(context)
                    .load(Constants.IMG_URL_BASE + currentFloor + "NP.jpg")
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(drawableId)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            view.setMap(resource);
                            view.updateView();
                            Glide.clear(this);
                        }
                    });
//        }

//        view.setMap(map);
//        view.updateView();
    }

    @Background
    void downloadMap(int currentFloor, Context context) {
        Log.i(TAG, "Downloading map from server - " + currentFloor + " floor");
        Bitmap map = null;
        try {
            map = new BitmapWorkerTask(getFloorName(currentFloor), context, AppUtils.getVersionCode(context))
                    .execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (view != null) {
            view.setMap(map);
            view.updateView();
        }
    }

    @Override
    public List<Place> getPlaces(int currentFloor) {
        return userService.getActualUser().getPlacesByFloor(currentFloor);
    }

    @Override
    public void createIcons(Context context, int currentFloor) {
        places = userService.getActualUser().getPlacesByFloor(currentFloor);
        List<Drawable> icons = new ArrayList<>();
        if (places != null) {
            for (Place p : places) {
                try {
                    String iconName = p.getPlaceType().getImgUrl();
                    if (iconName == null) {
                        iconName = p.getMaterial().getIconName();
                    }
                    Bitmap bitmap = new BitmapWorkerTask(iconName, context, AppUtils.getVersionCode(context)).execute().get();
                    Drawable icon = new BitmapDrawable(context.getResources(), bitmap);
                    icons.add(icon);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        view.updateIcons(icons);
//        view.updateView();
    }

    private String getFloorName(int currentFloor) {
        return currentFloor + "NP.jpg";
    }

    @Override
    public void onAttach(MapActivityView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
