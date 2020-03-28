package avocardio.avocardioapp.Activities.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import avocardio.avocardioapp.Activities.App;
import avocardio.avocardioapp.Activities.Register.RegisterActivity_1_5;
import avocardio.avocardioapp.Connections.UserManager;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.password_field)
    EditText passwordField;
    @BindView(R.id.previouswindowBtn)
    ImageView previouswindowBtn;
    @BindView(R.id.imei)
    TextView imei;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        //DANE NIE ULEGNA ZNISZCZENIU
        userManager = ((App)getApplication()).getUserManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
           userManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        userManager.onStop();
    }

    @OnClick(R.id.login_btn)
    public void loginValidation() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        boolean hasErrors = false;

        //    EMAIL VALIDATION
        if (email.isEmpty()) {
            emailField.setError(getString(R.string.empty_error));
            hasErrors = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError(getString(R.string.email_not_valid));
            hasErrors = true;
        }
        // PASSWORD VALIDATION
        if (password.isEmpty()) {
            passwordField.setError(getString(R.string.empty_error));
            hasErrors = true;
        } else if (password.length() < 6) {
            passwordField.setError(getString(R.string.password_toshort_error));
            hasErrors = true;
        }

        // LOGOWANIE DO APLIKACJI
        if (!hasErrors) {
            try{
               userManager.login();
            }catch (NullPointerException e){
                System.out.println("-------!!!!-------"+e.getMessage());
            }
        }
    }


    public void loginSuccess(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity_1_5.class));
        finish();
    }

    public void showError(String error){
        Toast.makeText(LoginActivity.this, "Error: " +error, Toast.LENGTH_LONG).show();
    }

}

