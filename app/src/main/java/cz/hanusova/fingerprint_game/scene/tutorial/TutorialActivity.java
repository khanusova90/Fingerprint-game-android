package cz.hanusova.fingerprint_game.scene.tutorial;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cz.hanusova.fingerprint_game.R;

/**
 * Created by khanusova on 02/07/2017.
 */
@EActivity(R.layout.activity_tutorial)
public class TutorialActivity extends FragmentActivity {

    private static final int NUM_PAGES = 5;

    @ViewById(R.id.tutorial_pager)
    ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @AfterViews
    void init(){
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = SlideFragment_.builder().currentPage(position).build();
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
