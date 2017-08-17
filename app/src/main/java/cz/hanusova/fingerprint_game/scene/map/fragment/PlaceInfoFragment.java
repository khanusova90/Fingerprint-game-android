package cz.hanusova.fingerprint_game.scene.map.fragment;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.base.service.UserService;
import cz.hanusova.fingerprint_game.base.service.impl.UserServiceImpl;
import cz.hanusova.fingerprint_game.base.utils.AppUtils;
import cz.hanusova.fingerprint_game.base.utils.PlaceUtils;
import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.model.UserActivity;
import cz.hanusova.fingerprint_game.task.BitmapWorkerTask;

/**
 * Fragment for displaying information about place
 */
@EFragment(R.layout.fragment_place_info)
public class PlaceInfoFragment extends DialogFragment {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("d.M.yyyy HH:mm");

    @ViewById(R.id.fragment_place_name)
    TextView placeName;
    @ViewById(R.id.fragment_place_time)
    TextView textTime;
    @ViewById(R.id.place_start)
    TextView placeStart;
    @ViewById(R.id.place_type)
    TextView placeType;
    @ViewById(R.id.fragment_place_image)
    ImageView imageView;

    @Bean(UserServiceImpl.class)
    UserService userService;

    @FragmentArg
    Place place;
    AppUser user;

    @AfterViews
    public void initViews() {
        user = userService.getActualUser();

        List<UserActivity> activities = user.getActivities();
        UserActivity activity = null;

        for (UserActivity a : activities) {
            if (a.getPlace().equals(place)) {
                activity = a;
                break;
            }
        }

        placeType.setText(place.getPlaceType().getPlaceType());
        placeName.setText(place.getDescription());
        if (activity != null) {
            placeStart.setVisibility(View.VISIBLE);
            textTime.setText(SDF.format(activity.getStartTime()));
        }

        Bitmap bitmap = null;
        try {
            String iconName = PlaceUtils.getIconName(place);
            bitmap = new BitmapWorkerTask(iconName, getContext(), AppUtils.getVersionCode(getContext())).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(new BitmapDrawable(this.getContext().getResources(), bitmap));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
