package cz.hanusova.fingerprint_game.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.model.UserActivity;

/**
 * Created by Kristyna on 16/02/2017.
 */


@EViewGroup(R.layout.double_test_list_item)
public class ActivityItemView extends LinearLayout {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("d.M.yyyy HH:mm");

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
        int materialStrId = getResources().getIdentifier(userActivity.getPlace().getMaterial().getName(), "string", getContext().getPackageName());
        activityMaterial.setText(getResources().getString(materialStrId));
        activityPlace.setText(userActivity.getPlace().getName());
        activityDate.setText(getResources().getString(R.string.activity_start) + ": " + SDF.format(userActivity.getStartTime()));
    }
}
