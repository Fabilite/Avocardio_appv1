package avocardio.avocardioapp.Activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.Helpers.UserStorage;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.logout_btn)
    Button logoutBtn;
    @BindView(R.id.user_information)
    TextView userInformation;
    @BindView(R.id.mainLayout)
    ConstraintLayout mainLayout;
    @BindView(R.id.get_userData)
    Button getUserData;

    private UserStorage userStorage;
    private MainManager mainManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        mainManager = ((App) getApplication()).getMainManager();
        userStorage = ((App) getApplication()).getUserStorage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainManager.onStop();
    }

    @OnClick(R.id.logout_btn)
    public void logOut() {
        userStorage.logout();
        Intent intent =  new Intent(this, LoginActivity.class);
        intent.putExtra("active", 3);
        startActivity(intent);
        finish();
    }


    public void loginSuccess(String text) {
        userInformation.setText(text);
        //startActivity();
        //finish();
    }

    @OnClick(R.id.get_userData)
    public void getUserData(){
        try{
            mainManager.getUserInformation();
        }catch(NullPointerException e){
            e.getMessage();
        }
    }

    public void getUserDates() {

    }

    public void showError(String error) {
        Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
    }


}
