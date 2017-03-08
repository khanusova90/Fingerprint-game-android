package cz.hanusova.fingerprint_game.fragment;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.task.BitmapWorkerTask;

@EFragment(R.layout.fragment_place_info)
public class PlaceInfoFragment extends DialogFragment {

    @ViewById(R.id.fragment_place_name)
    TextView textName;

    @ViewById(R.id.fragment_place_time)
    TextView textTime;

    ImageView imageView;

    Place place;

    public PlaceInfoFragment() {

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Thread exception", paramThrowable.getMessage());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_place_info, container, false);
        textName = (TextView)view.findViewById(R.id.fragment_place_name);
        textTime = (TextView)view.findViewById(R.id.fragment_place_time);
        imageView = (ImageView)view.findViewById(R.id.fragment_place_image);
        textName.setText(place.getDescription());
        textTime.setText("22:00  4.3. 2017");

        Bitmap bitmap = null;
        try {
            bitmap = new BitmapWorkerTask(place.getMaterial().getIconName(), this.getContext(), 4).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(new BitmapDrawable(this.getContext().getResources(), bitmap));
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);





        return dialog;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
