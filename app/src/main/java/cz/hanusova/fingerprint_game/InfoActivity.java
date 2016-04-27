package cz.hanusova.fingerprint_game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by khanusova on 4.4.2016.
 */
public class InfoActivity extends Activity {

    private static final int RC_BARCODE_CAPTURE = 9001;
    private TextView errorMessageTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
//        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sp = this.getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);

        TextView usernameTv = (TextView) findViewById(R.id.info_username);
        TextView stagnameTv = (TextView) findViewById(R.id.info_stagname);
        errorMessageTv = (TextView) findViewById(R.id.info_scan_error);

        String username = sp.getString(getString(R.string.username), getString(R.string.username));
        String stagname = sp.getString(getString(R.string.stagname), getString(R.string.stagname));

        usernameTv.setText(username);
        stagnameTv.setText(stagname);

        initQrBtn(this);

        //TODO: http://stackoverflow.com/questions/8831050/android-how-to-read-qr-code-in-my-application
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE){
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("Barcode");
//                    statusMessage.setText(R.string.barcode_success);
                    System.out.println(barcode.displayValue); //TODO: zpracovat precteny kod
//                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    errorMessageTv.setText(R.string.info_scan_fail);
//                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                errorMessageTv.setText(String.format(getString(R.string.info_scan_fail),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initQrBtn(final Context context) {
        Button loginBtn = (Button) findViewById(R.id.btn_info_scan);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QrActivity.class);
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        };
        loginBtn.setOnClickListener(listener);
    }

    private void initDetector() {
        BarcodeDetector detector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE).build();
        if (!detector.isOperational()) {
            Toast.makeText(this, "Nepodařilo se načíst scanner.", Toast.LENGTH_LONG).show();
            return;
        }
//        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
//        SparseArray<Barcode> barcodes = detector.detect(frame);
    }
}
