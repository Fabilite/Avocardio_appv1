package avocardio.avocardioapp.Activities.Login;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import avocardio.avocardioapp.Activities.Main.MainActivity;
import avocardio.avocardioapp.Activities.Others.LanguageActivity;
import avocardio.avocardioapp.Activities.Password.EmailActivity;
import avocardio.avocardioapp.Activities.Register.RegisterActivity_1;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.Helpers.Generates;
import avocardio.avocardioapp.Helpers.HelperMethod;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.mainLayoutLogin)
    ConstraintLayout mainLayout;
    @BindView(R.id.email_validation)
    TextView emailValidation;
    @BindView(R.id.password_validation)
    TextView passwordValidation;
    @BindView(R.id.change_language)
    LinearLayout changeLanguage;
    int response = 0;


    //Obiekt przechowywania activity
    private LoginManager loginManager;

    Generates generates = new Generates();
    HelperMethod helperMethod = new HelperMethod();

    private boolean emailV = false;
    private boolean passwordV = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginManager = ((App) getApplication()).getLoginManager();

        hidePlaceHolder(passwordField, R.string.g_password_placeholder);
        hidePlaceHolder(emailField, R.string.g_email_placeholder);
        Intent intent = getIntent();
        response = intent.getIntExtra("active", 0);
        showInformation(response);

    }

    private void tryDoIt(){
        Intent intent = getIntent();
        response = intent.getIntExtra("active", 0);
        showInformation(response);
    }

    //Ukrywanie klawiatury po kliknieciu w puste pole
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

    //Bottom popUp message
    public void popUpError(String text, int color) {
        Snackbar snackbar = Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(this, R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, color));
        snackbar.show();
    }

    private void showInformation(int response) {
        switch (response) {
            case 1:
                correctRegistration("Succesful registration");
                break;
            case 2:
              correctRegistration("Password change was successful ");
                break;
            case 3:
                correctRegistration("Logging out correctly");
                break;
            default:
                break;
        }
}


    //Bottom popUp message
    public void correctRegistration(String text) {
        Snackbar snackbar = Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(this, R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        snackbar.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean emailValidation(String email) {
        if (email.isEmpty()) {
            emailValidation.setText(getString(R.string.g_field_is_empty));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailValidation.setText(getString(R.string.g_wrong_email));
            return false;
        } else {
            emailValidation.setText(getString(R.string.g_empty));
            return true;
        }
    }

    private boolean passwordValidation(String password) {
        if (password.isEmpty()) {
            passwordValidation.setText(getString(R.string.g_field_is_empty));
            return false;
        } else if (password.length() <= 5) {
            passwordValidation.setText(getString(R.string.g_password_is_too_short));
            return false;
        } else {
            passwordValidation.setText(getString(R.string.g_empty));
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.login_btn)
    public void tryLogin() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (emailValidation(email) && passwordValidation(password)) {
            try {
                loginManager.clearSession();
                loginManager.login(email, generates.getSHA512(password));
            } catch (NullPointerException e) {
                e.getMessage();
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


    public void hidePlaceHolder(EditText editText, int textToSend) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    editText.setHint(textToSend);
                else
                    editText.setHint("");
            }
        });
    }

    public void loginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void showProgress(boolean b) {
        // loginBtn.setEnabled(!b);
    }

    @OnClick(R.id.change_language)
    public void onViewClicked() {
        startActivity(new Intent(this, LanguageActivity.class));
    }
}

