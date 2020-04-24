package avocardio.avocardioapp.Activities.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.R;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
