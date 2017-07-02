package cz.hanusova.fingerprint_game.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.model.UserActivity;

/**
 * Created by Kristyna on 16/02/2017.
 */


@EViewGroup(R.layout.item_activity)
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

    @ViewById(R.id.activity_material_count)
    TextView activityMaterialCount;

    public ActivityItemView(Context context) {
        super(context);
    }

    public void bind(UserActivity userActivity) {
        Place place = userActivity.getPlace();
        activityName.setText(place.getPlaceType().getPlaceType());
        if (place.getPlaceType().getIdPlaceType() == 1L) {
            int materialStrId = getResources().getIdentifier(place.getMaterial().getName(), "string", getContext().getPackageName());
            activityMaterial.setText(getResources().getString(materialStrId));
        }
        activityPlace.setText(place.getName());
        activityDate.setText(getResources().getString(R.string.activity_start) + ": " + SDF.format(userActivity.getStartTime()));
        int workerAmount = userActivity.getMaterialAmount().intValue();
        activityMaterialCount.setText(workerAmount + " " + getResources().getQuantityString(R.plurals.workers, workerAmount));
    }
}
