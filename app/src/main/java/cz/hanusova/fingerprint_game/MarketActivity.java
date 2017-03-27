package cz.hanusova.fingerprint_game;

import android.content.Context;
import android.content.Intent;
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
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.model.Item;
import cz.hanusova.fingerprint_game.rest.RestClient;
import cz.hanusova.fingerprint_game.task.BitmapWorkerTask;
import cz.hanusova.fingerprint_game.utils.AppUtils;
import cz.hanusova.fingerprint_game.utils.Constants;

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

    @RestService
    RestClient restClient;

    @AfterViews
    public void init() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        if (items == null || items.isEmpty()) {
            items = (ArrayList<Item>) restClient.getPossibleItems();
        }
        for (final Item item : items) {
            View imageLayout = inflater.inflate(R.layout.image_item, null);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.item_bitmap);
            try {
                Bitmap bitmap = new BitmapWorkerTask(item.getImgUrl(), this.getApplicationContext(), AppUtils.getVersionCode(this)).execute().get();
                imageView.setImageBitmap(bitmap);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setSelected(!item.isSelected());
                    }
                });
                itemLayout.addView(imageLayout);
            } catch (InterruptedException | ExecutionException e) {
                Log.e(TAG, "Could not download item image", e);
            }
        }
    }

    @Click(R.id.market_buy)
    public void buy() {
        ArrayList<Item> boughtItems = new ArrayList<>();
        for (Item item : items) {
            if (item.isSelected()) {
                boughtItems.add(item);
            }
        }
        Intent i = new Intent();
        i.putExtra(Constants.EXTRA_ITEMS, boughtItems);
        setResult(RESULT_OK, i);
        finish();
    }
}
