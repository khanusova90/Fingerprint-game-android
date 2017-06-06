package cz.hanusova.fingerprint_game;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cz.hanusova.fingerprint_game.adapter.ActivityAdapter;
import cz.hanusova.fingerprint_game.adapter.ImageAdapter;
import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.service.UserService;
import cz.hanusova.fingerprint_game.service.impl.UserServiceImpl;


@EActivity(R.layout.content_user_detail)
public class UserDetailActivity extends AppCompatActivity {

    @Bean(UserServiceImpl.class)
    UserService userService;

    @Bean(ActivityAdapter.class)
    ActivityAdapter activityAdapter;

    @Bean(ImageAdapter.class)
    ImageAdapter imageAdapter;

    @ViewById(R.id.gridview_inventory)
    GridView inventoryGrid;

    @ViewById(R.id.progressBar_experience)
    ProgressBar progressBarExperience;

    @ViewById(R.id.progressBar_places)
    ProgressBar progressBarPlaces;

    @ViewById(R.id.text_places)
    TextView places;

    @ViewById(R.id.text_experience)
    TextView experience;

    @ViewById(R.id.text_username)
    TextView username;

    @ViewById(R.id.list_of_activities)
    ListView listOfActivities;

    private AppUser user;

    @AfterViews
    void bindAdapter() {
        inventoryGrid.setAdapter(imageAdapter);
    }

    @AfterViews
    void bindAdapter2() {
        listOfActivities.setAdapter(activityAdapter);

    }

    @AfterViews
    void init() {

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Thread exception", paramThrowable.getMessage());
            }
        });
        user = userService.getActualUser();

        this.setTitle("Profil");

        int placesVal = 55;
        int activitiesVal = 44;
        places.setText(this.getResources().getText(R.string.places) + " " + String.valueOf(placesVal) + "%");
        places.setTextSize(15);
        experience.setText(this.getResources().getText(R.string.experience) + " " + String.valueOf(activitiesVal + "%"));
        progressBarExperience.setProgress(activitiesVal);
        progressBarPlaces.setProgress(placesVal);
        username.setText(user.getUsername());
    }
}