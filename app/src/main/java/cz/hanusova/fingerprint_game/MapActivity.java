package cz.hanusova.fingerprint_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.hanusova.fingerprint_game.utils.Constants;
import cz.hanusova.fingerprint_game.view.TouchImageView;

/**
 * Created by khanusova on 6.6.2016.
 */
public class MapActivity extends AbstractAsyncActivity {

    private List<Bitmap> icons = new ArrayList<>();
    private Context context;

    private static final String TAG = "MapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.map);
        new MapAsyncTask().execute();
    }

    private class MapAsyncTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(Constants.IMG_URL_BASE + "J1NP.jpg");
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                URL iconUrl = new URL("http://192.168.0.101:8080/fingerprint-game/disk-resources/money_icon.png");
                Bitmap icon = BitmapFactory.decodeStream(iconUrl.openConnection().getInputStream());
                icons.add(icon);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Could not download map from server");
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            dismissProgressDialog();
            TouchImageView iv = (TouchImageView) findViewById(R.id.img_map);
            if (bitmap != null) {
//                iv.setMaxZoom(4f);
                addIcons(iv, bitmap);
            }
        }

        private void addIcons(TouchImageView iv, Bitmap bitmap){
//            List<ImageView> iconList = new ArrayList<>();
            List<Drawable> drawables = new ArrayList<>();
            Drawable mapBitmap = new BitmapDrawable(getResources(), bitmap);
            drawables.add(mapBitmap);
            for (Bitmap icon : icons){
                Bitmap smallIcon = Bitmap.createBitmap(icon, 10, 20, 15, 15);
//                ImageView image = new ImageView(context);
//                image.setImageBitmap(smallIcon);
//                iconList.add(image);
                Drawable drawable = new BitmapDrawable(getResources(), smallIcon);
                drawables.add(drawable);
            }
            LayerDrawable ld = new LayerDrawable(drawables.toArray(new Drawable[drawables.size()]));
            iv.setImageDrawable(ld);
        }
    }

    public void setIcons(List<Bitmap> icons){
        this.icons = icons;
    }
}
