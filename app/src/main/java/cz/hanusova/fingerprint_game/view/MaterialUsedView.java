package cz.hanusova.fingerprint_game.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.base.utils.Constants;

/**
 * View to display used material while scanning
 *
 * Created by khanusova on 26/07/2017.
 */
@EViewGroup(R.layout.material_used)
public class MaterialUsedView extends LinearLayout{

    private static final int ICON_SIZE = 100;

    @ViewById(R.id.material_image)
    ImageView materialImage;
    @ViewById(R.id.material_used)
    TextView materialUsed;

    public MaterialUsedView(Context context) {
        super(context);
    }

    public void setMaterialImage(String iconName) {
        Glide.with(getContext())
                .load(Constants.IMG_URL_BASE + iconName)
                .override(ICON_SIZE, ICON_SIZE)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(materialImage);
    }

    public void setMaterialUsed(String materialCount) {
        materialUsed.setText(materialCount);
    }
}
