package cz.hanusova.fingerprint_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.model.Item;
import cz.hanusova.fingerprint_game.task.BitmapWorkerTask;
import cz.hanusova.fingerprint_game.utils.AppUtils;

/**
 * Created by khanusova on 7.10.2016.
 */
@EActivity(R.layout.activity_market)
public class MarketActivity extends AppCompatActivity {
    private static final String TAG = "MarketActivity";

    @ViewById(R.id.market_item_layout)
    LinearLayout itemLayout;

    @Extra
    ArrayList<Item> items;

    @AfterViews
    public void init() {
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        for (Item item : items){ //TODO: pokusit se znovu stahnout, pokud je list prazdny
            View imageLayout = inflater.inflate(R.layout.image_item, null);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.item_bitmap);
            try {
                Bitmap bitmap = new BitmapWorkerTask(item.getImgUrl(), this.getApplicationContext(), AppUtils.getVersionCode(this)).execute().get();
                imageView.setImageBitmap(bitmap);
                itemLayout.addView(imageLayout);
            } catch (InterruptedException | ExecutionException e) {
                Log.e(TAG, "Could not download item image", e);
            }
        }
    }

    @Click(R.id.market_buy)
    public void buy(){
        setResult(RESULT_OK);
        finish();
    }
}
