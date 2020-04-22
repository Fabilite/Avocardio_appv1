package avocardio.avocardioapp.Activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.R;
import avocardio.avocardioapp.Helpers.UserStorage;
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
//        if (userStorage.isHasToLogin()) {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//            return;
//        }

        setContentView(R.layout.main_activity);
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
