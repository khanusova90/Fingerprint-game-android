package cz.hanusova.fingerprint_game.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.model.UserActivity;

/**
 * Created by Kristyna on 16/02/2017.
 */


@EViewGroup(R.layout.double_test_list_item)
public class ActivityItemView extends LinearLayout {

    @ViewById(R.id.activity_name)
    TextView activityName;

    @ViewById(R.id.activity_material)
    TextView activityMaterial;

    @ViewById(R.id.activity_place)
    TextView activityPlace;

    @ViewById(R.id.activity_since)
    TextView activityDate;

    public ActivityItemView(Context context) {
        super(context);
    }

    public void bind(UserActivity userActivity) {
        activityName.setText(userActivity.getPlace().getPlaceType().getPlaceType());
        activityMaterial.setText(userActivity.getPlace().getMaterial().getName());
        activityPlace.setText(userActivity.getPlace().getName());
        activityDate.setText(userActivity.getStartTime().toString());
    }
}
