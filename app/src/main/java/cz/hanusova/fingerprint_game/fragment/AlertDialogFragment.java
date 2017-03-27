package cz.hanusova.fingerprint_game.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import org.androidannotations.annotations.EFragment;

import cz.hanusova.fingerprint_game.MapActivity_;
import cz.hanusova.fingerprint_game.R;

/**
 * Created by Kristyna on 27/03/2017.
 */
@EFragment
public class AlertDialogFragment extends DialogFragment {

    public static AlertDialogFragment newInstance(int title) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Přejete si ukončit hru?")
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MapActivity_)getActivity()).doPositiveClick();
                            }
                        }
                )
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MapActivity_)getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();
    }
}
