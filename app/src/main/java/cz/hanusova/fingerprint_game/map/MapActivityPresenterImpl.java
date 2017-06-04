package cz.hanusova.fingerprint_game.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.task.BitmapWorkerTask;
import cz.hanusova.fingerprint_game.utils.AppUtils;

/**
 * Created by khanusova on 31.5.2017.
 */

public class MapActivityPresenterImpl implements MapActivityPresenter {
    private static final String TAG = "MapActivityPresenter";

    private MapActivityView view;
    private Bitmap[] mapField = new Bitmap[4];
    private List<Place> places;

    @Override
    public void createMap(int currentFloor, Context context){
        int index= currentFloor - 1;
        if (mapField[index] == null) {
            Log.d(TAG, "Map was null");
            String drawableName = "j" + currentFloor + "np";
            int drawableId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
//            Drawable defaultMap = context.getResources().getDrawable(drawableId);
            mapField[index] = BitmapFactory.decodeResource(context.getResources(), drawableId);
            //TODO: download map from server
        }
        view.updateView(mapField);
    }

//            System.out.println("MAP WAS NULL");
//
////            Drawable defaultMap = getResources().getDrawable(getResources().getIdentifier(drawableName, "drawable", getPackageName()));
//
//            System.out.println("MAP FIELD FILLED");
//            try {
//                System.out.println("DOWNLOADING MAP");
//                mapField[index] = new BitmapWorkerTask(getFloorName(currentFloor), this.getApplicationContext(), AppUtils.getVersionCode(this))
//                        .execute().get();
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            System.out.println("MAP DOWNLOADED");
//        }
//        System.out.println("CREATING DRAWABLE");
//        final Drawable mapDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(mapField[currentFloor - 1], MAP_WIDTH, MAP_HEIGHT, true));
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("GETTING PLACES");
//                places = userService.getActualUser().getPlacesByFloor(currentFloor);
//                List<Drawable> icons = getIcons(places);
//                changeIconPosition(mapDrawable, places, createLayers(mapDrawable, icons));
//            }
//        }).start();
//    }

    void downloadMapFromServer(int currentFloor, Context context) {
        //TODO: download map from server
        try {
            new BitmapWorkerTask(getFloorName(currentFloor), context, AppUtils.getVersionCode(context))
                    .execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //TODO: refresh view
    }

    private String getFloorName(int currentFloor) {
        return currentFloor + "NP.jpg";
    }

    @Override
    public void onAttach(MapActivityView view) {
        this.view = view;
        System.out.println("HELLO FROM PRESENTER");
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
