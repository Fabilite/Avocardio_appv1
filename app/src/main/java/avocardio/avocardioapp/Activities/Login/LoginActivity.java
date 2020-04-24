package avocardio.avocardioapp.Activities.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import avocardio.avocardioapp.Activities.LoadingProgressBar;
import avocardio.avocardioapp.Activities.Main.MainActivity;
import avocardio.avocardioapp.Activities.Password.EmailActivity;
import avocardio.avocardioapp.Activities.Register.RegisterActivity_1;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.Helpers.Generates;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTouch;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.password_field)
    EditText passwordField;
    @BindView(R.id.forgot_Password_btn)
    TextView forgotPasswordBtn;
    @BindView(R.id.signUp_btn)
    LinearLayout signUpBtn;
    Generates generates = new Generates();
    @BindView(R.id.mainLayoutLogin)
    ConstraintLayout mainLayout;

    //Obiekt przechowywania activity
    private LoginManager loginManager;
    LoadingProgressBar loadingProgressBar;

    private static String TAG = "LoginActivity class";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        loadingProgressBar = new LoadingProgressBar(this);
        //DANE NIE ULEGNA ZNISZCZENIU
        //Tworzenie jednej isntancji obiektu
        loginManager = ((App) getApplication()).getLoginManager();

    }

    @OnFocusChange(R.id.email_field)
    public void setEmailChange(View view) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    emailField.setHint(R.string.login_status_emailhint);
                else
                    emailField.setHint("");
            }
        });
    }


    @OnFocusChange(R.id.password_field)
    public void setPasswordChange(View view) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    passwordField.setHint(R.string.login_status_passwordhint);
                else
                    passwordField.setHint("");
            }
        });
    }

    @OnTouch(R.id.mainLayoutLogin)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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


    public void popUp(Activity activity, String text) {
        Snackbar snackbar = Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(this, R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.error_info));
        snackbar.show();

    }

    @OnClick(R.id.login_btn)
    public void loginValidation() {
        popUp(this, "test");
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        boolean hasErrors = false;

        //EMAIL VALIDATION
        if (email.isEmpty()) {
            emailField.setError(getString(R.string.register_error_empty));
            hasErrors = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError(getString(R.string.register_error_email_not_valid));
            hasErrors = true;
        }
        // PASSWORD VALIDATION
        if (password.isEmpty()) {
            passwordField.setError(getString(R.string.register_error_empty));
            hasErrors = true;
        } else if (password.length() <= 5) {
            passwordField.setError(getString(R.string.register_error_password_short));
            hasErrors = true;
        }

        // LOGOWANIE DO APLIKACJI
        if (!hasErrors) {
            loadingProgressBar.startLoadingProgressBar();
            try {
                loginManager.login(email, generates.getSHA512(password));

            } catch (NullPointerException e) {
                System.out.println("-------!!!!-------" + e.getMessage());
            }
        }
    }


    @OnClick(R.id.signUp_btn)
    public void goToRegister() {
        startActivity(new Intent(this, RegisterActivity_1.class));
    }

    @OnClick(R.id.forgot_Password_btn)
    public void goToPasswordRemember() {
        startActivity(new Intent(this, EmailActivity.class));
    }


    public void loginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void showError(String error) {
        Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
        loadingProgressBar.stopProgressBar();
    }

    public void showProgress(boolean b) {
        // loginBtn.setEnabled(!b);
    }

}

