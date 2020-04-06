package avocardio.avocardioapp.Activities.Login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import avocardio.avocardioapp.Activities.App;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {


    private static final String TAG = "WELCOME ACTIVITY" ;
    @BindView(R.id.openapp)
    Button openapp;

    LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        ButterKnife.bind(this);
        loginManager = ((App) getApplication()).getLoginManager();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.openapp)
    public void onViewClicked() {

        loginManager.askForGuacamole();
        startActivity(new Intent(this,LoginActivity.class));
        finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getImeiNumber() {
        //IMEI
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        } else {
            //pobiera dane urzadzenia
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                Toast.makeText(this, "Nie wspierany", Toast.LENGTH_LONG);
                return null;
            } else {
                Log.i(TAG, "WYSWIETLAM IMEI--------------" + telephonyManager.getImei());
                return telephonyManager.getImei();
            }
        }
        return null;
    }
}
