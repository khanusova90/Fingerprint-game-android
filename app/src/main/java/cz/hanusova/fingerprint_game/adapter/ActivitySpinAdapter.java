package cz.hanusova.fingerprint_game.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cz.hanusova.fingerprint_game.model.Activity;

/**
 * Created by khanusova on 5.6.2016.
 */
public class ActivitySpinAdapter extends ArrayAdapter<Activity> {

    private List<Activity> activities;
    private Context context;

    public ActivitySpinAdapter(Context context, int resource, List <Activity> objects) {
        super(context, resource, objects);
        this.context = context;
        this.activities = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(activities.get(position).getName());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(activities.get(position).getName());

        return label;
    }
}
