package avocardio.avocardioapp.Activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import avocardio.avocardioapp.Activities.App;
import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Activities.Login.WelcomeActivity;
import avocardio.avocardioapp.R;
import avocardio.avocardioapp.UserStorage;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.logout_btn)
    Button logoutBtn;
    private UserStorage userStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userStorage = ((App) getApplication()).getUserStorage();
        //sprawdzam czy uzytkownik jest zalogowany
        if (userStorage.isHasToLogin()) {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activitymain);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.logout_btn)
    public void logOut(){
        //CZYSZCZENIE BAZY Z DANYCH LOGOWANIA
        //userStorage.logOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
