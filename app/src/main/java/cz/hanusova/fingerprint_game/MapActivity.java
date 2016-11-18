package cz.hanusova.fingerprint_game;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.hanusova.fingerprint_game.utils.Constants;
import cz.hanusova.fingerprint_game.view.TouchImageView;

/**
 * Created by khanusova on 6.6.2016.
 *
 * Activity for displaying map
 */
@EActivity(R.layout.map)
public class MapActivity extends AbstractAsyncActivity {
    private static final String TAG = "MapActivity";

//    @ViewById(R.id.map_floor_1)
//    Button btnFloor1;
//    @ViewById(R.id.map_floor_2)
//    Button btnFloor2;
//    @ViewById(R.id.map_floor_3)
//    Button btnFloor3;
//    @ViewById(R.id.map_floor_4)
//    Button btnFloor4;

    @ViewById(R.id.img_map)
    TouchImageView mapView;

    @DrawableRes(R.drawable.j4np)
    Drawable map;
    @DrawableRes(R.drawable.money_icon)
    Drawable icon;

    private List<Bitmap> icons = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @AfterViews
    void init(){
        Bitmap bitmap = ((BitmapDrawable)icon).getBitmap();
        Drawable smallIcon = new BitmapDrawable(getResources(), bitmap);
        //TODO: zkusit nacpat jen icon
        LayerDrawable iconLd = new LayerDrawable(new Drawable[]{map, smallIcon});
//        new MapAsyncTask().execute();
//        BitmapDrawable mozaika = new BitmapDrawable(getResources(), mozaikaDr);
//        LayerDrawable iconLd = new LayerDrawable(new Drawable[]{mozaika, smallIcon});
        int width = map.getIntrinsicWidth();
        int height = map.getIntrinsicHeight();

        int posX = 50;
        int posY = 130;

        int iconW = 50;
        int iconH = 50;

        iconLd.setLayerInset(1, posX, posY, width - posX - iconW, height - posY - iconH); //TODO: for cyklus pro index kazde ikony -> od 1 do icons.size
        mapView.setImageDrawable(iconLd);
        TouchImageView iv = (TouchImageView) findViewById(R.id.img_map);
//        iv.setImageDrawable(iconLd);
//        Button btn = new Button(this);
//        btn.setPadding(posX, posY, width - posX - iconW, height - posY - iconH);
//        btn.setBackground(icon);


//        ImageView image = new ImageView(getApplicationContext());
//        image.setImageBitmap(bitmap);
//        Drawable d = image.getDrawable();
//        LayerDrawable ld = new LayerDrawable(new Drawable[]{map, d});
//        iv.setImageDrawable(ld);
    }

    @Click(R.id.map_camera)
    void startCamera(){
        QrActivity_.intent(this).start();
    }

    //TODO: externalizovat
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
//                URL url = new URL(Constants.IMG_URL_BASE + "mozaika.jpg");
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                URL iconUrl = new URL(Constants.IMG_URL_BASE + "money_icon.png");
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
            }
            addIcons(iv, bitmap);
        }

        private void addIcons(TouchImageView iv, Bitmap bitmap){
//            List<ImageView> iconList = new ArrayList<>();
            List<Drawable> drawables = new ArrayList<>();
            Drawable mapBitmap = new BitmapDrawable(getResources(), bitmap);
//            Drawable mapBitmap = getResources().getDrawable(R.drawable.j4np);
            drawables.add(mapBitmap);
            for (Bitmap icon : icons){
                Bitmap smallIcon = Bitmap.createBitmap(icon, 10, 20, 15, 15);
//                ImageView image = new ImageView(context);
//                image.setImageBitmap(smallIcon);
//                iconList.add(image);
                Drawable drawable = new BitmapDrawable(getResources(), smallIcon);
                drawables.add(drawable);
            }

            Drawable drawable = getResources().getDrawable(R.drawable.money_icon);
            drawables.add(drawable);

            LayerDrawable ld = new LayerDrawable(drawables.toArray(new Drawable[drawables.size()]));
            ld.setLayerInset(1, 20, 30, 100, 120);
            iv.setImageDrawable(ld);
        }
    }

    public void setIcons(List<Bitmap> icons){
        this.icons = icons;
    }
}
