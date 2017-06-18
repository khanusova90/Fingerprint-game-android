package cz.hanusova.fingerprint_game.scene.user;

import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.adapter.ImageAdapter;
import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.UserActivity;
import cz.hanusova.fingerprint_game.base.service.UserService;
import cz.hanusova.fingerprint_game.base.service.impl.UserServiceImpl;
import cz.hanusova.fingerprint_game.view.ActivityItemView;
import cz.hanusova.fingerprint_game.view.ActivityItemView_;


@EActivity(R.layout.content_user_detail)
public class UserDetailActivity extends AppCompatActivity {

    @Bean(UserServiceImpl.class)
    UserService userService;

    @Bean(ImageAdapter.class)
    ImageAdapter imageAdapter;
    @ViewById(R.id.gridview_inventory)
    GridView inventoryGrid;
    @ViewById(R.id.progressBar_experience)
    ProgressBar progressBarExperience;
    @ViewById(R.id.progressBar_places)
    ProgressBar progressBarPlaces;
    @ViewById(R.id.detail_place_progress)
    TextView places;
    @ViewById(R.id.detail_level)
    TextView level;
    @ViewById(R.id.detail_level_progress)
    TextView experience;
    @ViewById(R.id.text_username)
    TextView username;
    @ViewById(R.id.list_of_activities)
    LinearLayout listOfActivities;

    private AppUser user;

    @AfterViews
    void init() {
        user = userService.getActualUser();

        this.setTitle(getString(R.string.title_activity_user_detail));

        int placesVal = user.getPlaceProgress();
        int activitiesVal = user.getLevelProgress();
        places.setText(String.valueOf(placesVal) + "%");
        places.setTextSize(15);
        level.setText(getString(R.string.lvl) + " " + user.getLevel());
        experience.setText(String.valueOf(activitiesVal + "%"));
        progressBarExperience.setProgress(activitiesVal);
        progressBarPlaces.setProgress(placesVal);
        username.setText(user.getUsername());
        inventoryGrid.setAdapter(imageAdapter);
        showActivities();
    }

    private void showActivities(){
        List<UserActivity> activities = userService.getActualUser().getActivities();
        for (UserActivity activity : activities) {
            ActivityItemView view = ActivityItemView_.build(this);
            view.bind(activity);
            listOfActivities.addView(view);
        }
    }
}