package cz.hanusova.fingerprint_game.scene.tutorial;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import cz.hanusova.fingerprint_game.R;

/**
 * Created by khanusova on 02/07/2017.
 */
@EFragment(R.layout.slide_page)
public class SlideFragment extends Fragment {

    private static final String[] slideNames = new String[] {"screen_login", "screen_map", "screen_user_detail", "screen_place_info"};

    @ViewById(R.id.tutorial_image)
    ImageView tutorialImage;

    @FragmentArg
    int currentPage;

    @AfterViews
    void init(){
        int imgIdentifier = getResources().getIdentifier(slideNames[currentPage], "drawable", getContext().getPackageName());
        tutorialImage.setImageDrawable(getResources().getDrawable(imgIdentifier));

        switch(currentPage) {
            case 0:
                Snackbar.
        }
    }

}
