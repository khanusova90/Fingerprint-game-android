package cz.hanusova.fingerprint_game.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import cz.hanusova.fingerprint_game.model.UserActivity;
import cz.hanusova.fingerprint_game.service.UserService;
import cz.hanusova.fingerprint_game.service.impl.UserServiceImpl;
import cz.hanusova.fingerprint_game.view.ActivityItemView;
import cz.hanusova.fingerprint_game.view.ActivityItemView_;

/**
 * Created by Kristyna on 15/02/2017.
 */
@EBean
public class ActivityAdapter extends BaseAdapter {

    List<UserActivity> usersActivities;

    @RootContext
    Context context;

    @Bean(UserServiceImpl.class)
    UserService userService;


    @AfterInject
    void initAdapter() {
        usersActivities = userService.getActualUser().getActivities();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ActivityItemView activityItemView;
        if (convertView == null) {
            activityItemView = ActivityItemView_.build(context);

        } else {
            activityItemView = (ActivityItemView) convertView;
        }
        activityItemView.bind(getItem(position));
        usersActivities.get(position);
        return activityItemView;
    }

    @Override
    public int getCount() {
        return usersActivities.size();
    }

    @Override
    public UserActivity getItem(int position) {
        return usersActivities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
