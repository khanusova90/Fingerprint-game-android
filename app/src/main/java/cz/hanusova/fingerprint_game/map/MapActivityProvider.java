package cz.hanusova.fingerprint_game.map;

import android.content.Context;

import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.task.BitmapWorkerTask;
import cz.hanusova.fingerprint_game.utils.AppUtils;

/**
 * Created by khanusova on 31.5.2017.
 */

public class MapActivityProvider {
    private static final String TAG = "MapActivityProvider";

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
}
