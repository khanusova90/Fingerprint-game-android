package cz.hanusova.fingerprint_game.base.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.base.BasePresenter;
import cz.hanusova.fingerprint_game.base.BaseView;

/**
 * Created by khanusova on 31.5.2017.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ProgressDialog progressDialog;
    private boolean destroyed = false;

    protected abstract BasePresenter getPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onDetach();
        destroyed = true;
    }

    @UiThread
    protected void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }
        if (TextUtils.isEmpty(message)) {
            message = getString(R.string.wait);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @UiThread
    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }
}
