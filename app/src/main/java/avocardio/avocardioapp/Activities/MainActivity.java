package avocardio.avocardioapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Activities.Register.RegisterActivity_1_5;
import avocardio.avocardioapp.Connections.Api.AuthorizationPojo;
import avocardio.avocardioapp.Connections.Connection;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.imageViewLogo_relocation)
    RelativeLayout relative;
    @BindView(R.id.imageView_logo)
    ImageView imageViewLogo;
    @BindView(R.id.register_btn)
    Button registerBtn;

    private static String TAG = "-------Main Activity - class-------";

    AuthorizationPojo authorization = new AuthorizationPojo();
    Connection connection = new Connection();
    Handler handler = new Handler();
    Runnable runable = () -> {
        relative.setVisibility(View.VISIBLE);
    };


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        handler.postDelayed(runable, 2000);

        //POBIERANIE IMEI
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        } else {
            //pobiera dane urzadzenia
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                Toast.makeText(this, "Nie wspierany", Toast.LENGTH_LONG);
            } else {
                Log.i(TAG, "WYSWIETLAM IMEI--------------" + telephonyManager.getImei());
                authorization.setImei(telephonyManager.getImei());
            }

        }

    }


    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        connection.firstConnection();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.register_btn)
    public void onRegisterBtnClick() {
        connection.firstConnection();
        startActivity(new Intent(this, RegisterActivity_1_5.class));
    }

}
