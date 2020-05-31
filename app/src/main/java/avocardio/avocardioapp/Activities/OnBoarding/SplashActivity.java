package avocardio.avocardioapp.Activities.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.DataBase.DaoSession;
import avocardio.avocardioapp.DataBase.Setup;
import avocardio.avocardioapp.DataBase.SetupDao;
import avocardio.avocardioapp.R;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    private SetupDao setupDao;
    private DaoSession daoSession;
    private int id = 1;

    List<Setup> setupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        daoSession = ((App)getApplication()).getDaoSession();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                startActivity(new Intent(getApplicationContext(), OnBoardingActivity.class));
                finish();
            }
        }
    };



    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }


}
