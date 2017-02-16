package cz.hanusova.fingerprint_game.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import cz.hanusova.fingerprint_game.model.Inventory;
import cz.hanusova.fingerprint_game.service.UserService;
import cz.hanusova.fingerprint_game.service.impl.UserServiceImpl;
import cz.hanusova.fingerprint_game.view.GridItemView;
import cz.hanusova.fingerprint_game.view.GridItemView_;

/**
 * Created by Kristyna on 16/02/2017.
 */

@EBean
public class ImageAdapter extends BaseAdapter {

    List<Inventory> gridItems = new ArrayList<>();

    @RootContext
    Context context;

    @Bean(UserServiceImpl.class)
    UserService userService;

    @AfterInject
    void initAdapter() {
        gridItems = userService.getActualUser().getInventory();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        GridItemView personItemView;
        if (convertView == null) {
            personItemView = GridItemView_.build(context);
        } else {
            personItemView = (GridItemView) convertView;
        }

        personItemView.bind(getItem(i));

        return personItemView;
    }


    @Override
    public int getCount() {
        return gridItems.size();
    }

    @Override
    public Inventory getItem(int i) {
        return gridItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}

