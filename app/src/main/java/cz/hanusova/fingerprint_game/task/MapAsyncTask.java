package cz.hanusova.fingerprint_game.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;

import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by khanusova on 18.12.2016.
 */
public class MapAsyncTask extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(Constants.IMG_URL_BASE + params[0]);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bitmap;
        } catch (Exception e) {
            //TODO: zpracovat vyjimku
            e.printStackTrace();
        }


        return null;
    }
}
