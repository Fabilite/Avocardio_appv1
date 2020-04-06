package avocardio.avocardioapp.Activities.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import avocardio.avocardioapp.Activities.App;
import avocardio.avocardioapp.Activities.Main.MainActivity;
import avocardio.avocardioapp.Activities.Register.RegisterActivity_1;
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
    @BindView(R.id.forgotPassword_btn)
    TextView forgotPasswordBtn;
    @BindView(R.id.signUp_btn)
    TextView signUpBtn;


    //Obiekt przechowywania activity
    private LoginManager loginManager;

    private static String TAG = "LoginActivity class";
   // AuthorizationPojo authorization = new AuthorizationPojo();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        //DANE NIE ULEGNA ZNISZCZENIU
        //Tworzenie jednej isntancji obiektu
        loginManager = ((App) getApplication()).getLoginManager();
    }


    //informujemy Managera o starcie activity
    @Override
    protected void onStart() {
        super.onStart();
        loginManager.onAttach(this);
    }

    //informujemy Managera o zatrzymaniu pracy activity
    @Override
    protected void onStop() {
        super.onStop();
        loginManager.onStop();
    }

    @SuppressLint("NewApi")
    @OnClick(R.id.login_btn)
    public void loginValidation() {
        loginManager.login();
//        String email = emailField.getText().toString();
//        String password = passwordField.getText().toString();
//
//        boolean hasErrors = false;
//
//        //    EMAIL VALIDATION
////        if (email.isEmpty()) {
////            emailField.setError(getString(R.string.empty_error));
////            hasErrors = true;
////        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
////            emailField.setError(getString(R.string.email_not_valid));
////            hasErrors = true;
////        }
////        // PASSWORD VALIDATION
////        if (password.isEmpty()) {
////            passwordField.setError(getString(R.string.empty_error));
////            hasErrors = true;
////        } else if (password.length() < 6) {
////            passwordField.setError(getString(R.string.password_toshort_error));
////            hasErrors = true;
////        }
//
//        // LOGOWANIE DO APLIKACJI
//        if (!hasErrors) {
//            try {
//                loginManager.login();
//            } catch (NullPointerException e) {
//                System.out.println("-------!!!!-------" + e.getMessage());
//            }
//        }
    }

    @OnClick(R.id.signUp_btn)
    public void goToRegister(){
        startActivity(new Intent(this, RegisterActivity_1.class));

    }


    public void loginSuccess() {
        Log.i(TAG,"UDALO SIE ZALOGOWAC :):):):):):)");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
         finish();
    }

    public void showError(String error) {
        Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
    }

    public void showProgress(boolean b) {
       // loginBtn.setEnabled(!b);
    }

}

