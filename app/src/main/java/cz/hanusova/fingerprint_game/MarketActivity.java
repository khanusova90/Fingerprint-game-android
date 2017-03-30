package cz.hanusova.fingerprint_game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.model.Item;
import cz.hanusova.fingerprint_game.model.ItemEnum;
import cz.hanusova.fingerprint_game.rest.RestClient;
import cz.hanusova.fingerprint_game.service.UserService;
import cz.hanusova.fingerprint_game.service.impl.UserServiceImpl;
import cz.hanusova.fingerprint_game.task.BitmapWorkerTask;
import cz.hanusova.fingerprint_game.utils.AppUtils;
import cz.hanusova.fingerprint_game.utils.Constants;
import cz.hanusova.fingerprint_game.utils.TypeUtils;

/**
 * Created by khanusova on 7.10.2016.
 */
@EActivity(R.layout.activity_market)
public class MarketActivity extends AppCompatActivity {
    private static final String TAG = "MarketActivity";

    @ViewById(R.id.market_item_layout)
    LinearLayout itemLayout;

    @ViewById(R.id.money_lay)
    LinearLayout moneyLayout;

    @ViewById(R.id.money_my)
    TextView tvUsersMoney;

    @ViewById(R.id.price_items)
    TextView tvPriceForSelected;

    @ViewById(R.id.market_buy)
    Button btnBuy;

    @Extra
    ArrayList<Item> items;

    @RestService
    RestClient restClient;

    @Bean(UserServiceImpl.class)
    UserService userService;

    @Bean
    TypeUtils tu;

    //ToolTipManager tooltips;

    @AfterViews
    public void init() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        if (items == null || items.isEmpty()) {
            items = (ArrayList<Item>) restClient.getPossibleItems();
        }

        for (final Item item : items) {
            View imageLayout = inflater.inflate(R.layout.image_item, null);
            final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.item_bitmap);
            TextView priceOfAnItem = (TextView) imageLayout.findViewById(R.id.item_name);
            TextView levelOfAnItem = (TextView) imageLayout.findViewById(R.id.item_level);


            //TODO: tooltip for each item
            try {
                Bitmap bitmap = new BitmapWorkerTask(item.getImgUrl(), this.getApplicationContext(), AppUtils.getVersionCode(this)).execute().get();
                imageView.setImageBitmap(bitmap);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setSelected(!item.isSelected());
                        updatePrice();
                        imageView.setBackgroundColor(item.isSelected() ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.colorWhite));
                    }
                });
                itemLayout.addView(imageLayout);
            } catch (InterruptedException | ExecutionException e) {
                Log.e(TAG, "Could not download item image", e);
            }
            priceOfAnItem.setText("Cena: " + item.getItemType().getPrice());
            levelOfAnItem.setText("Level: " + tu.getString(item.getLevel()));
        }
        tvUsersMoney.setText(tu.getString(userService.getInventory("GOLD").getAmount()));
        LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        margin.setMargins(0, items.size() * 30, 0, 0);
        //TODO: dynamic margin, not working dunnno why
        // moneyLayout.setLayoutParams(margin);
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


    public void updatePrice() {
        int price = 0;
        for (Item item : items) {
            if (item.isSelected()) {
                price += Integer.valueOf(item.getItemType().getPrice());
            }
            tvPriceForSelected.setText(tu.getString(price));
            Boolean notEnoughtMoney = tu.getInt(tvPriceForSelected.getText()) > userService.getInventory("GOLD").getAmount().intValue();
            tvUsersMoney.setTextColor(notEnoughtMoney
                    ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.colorGray));
            btnBuy.setEnabled(!notEnoughtMoney);
        }
    }

    public String getTooltipText(ItemEnum type) {

        switch (type) {
            case PICK:
                return getResources().getString(R.string.pickTooltip);
            case AXE:
                return getResources().getString(R.string.axeTooltip);
            case LADDER:
                return getResources().getString(R.string.ladderTooltip);

        }
        return "";
    }
}
