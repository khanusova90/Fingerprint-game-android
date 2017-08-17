package cz.hanusova.fingerprint_game.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.model.Inventory;
import cz.hanusova.fingerprint_game.task.BitmapWorkerTask;

/**
 * Created by Kristyna on 16/02/2017.
 */

@EViewGroup(R.layout.item_inventory)
public class GridItemView extends LinearLayout {


    @ViewById(R.id.grid_text)
    TextView textView;

    @ViewById(R.id.grid_image)
    ImageView imageView;

    public GridItemView(Context context) {
        super(context);
    }

    public void bind(Inventory inventory) {
        textView.setText(inventory.getAmount().toString());

        Bitmap bitmap = null;
        try {
            bitmap = new BitmapWorkerTask(inventory.getMaterial().getIconName(), this.getContext(), 4).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(new BitmapDrawable(this.getContext().getResources(), bitmap));
    }
}
