package avocardio.avocardioapp.Activities.OnBoarding;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import avocardio.avocardioapp.Activities.Main.MainActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.Helpers.UserStorage;
import avocardio.avocardioapp.R;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private UserStorage userStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userStorage = ((App)getApplication()).getUserStorage();
        setLanguage(userStorage.getLangugae());
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                if(userStorage.isHasToLogin()){
                    startActivity(new Intent(getApplicationContext(), OnBoardingActivity.class));
                    finish();
                }else {
                    Log.i("User_hash/user storage","-----------------------------------------"+ userStorage.getOnbardingClicked());
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
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

    private int checkOnBoarding(){
        return userStorage.getOnbardingClicked();
    }

    public void setLanguage(String language) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(language.toLowerCase()));
        }else{
            conf.locale = new Locale(language.toLowerCase());
        }
        res.updateConfiguration(conf, dm);
    }
}
