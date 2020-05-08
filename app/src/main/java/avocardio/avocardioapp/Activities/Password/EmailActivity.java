package avocardio.avocardioapp.Activities.Password;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class EmailActivity extends AppCompatActivity {

    @BindView(R.id.back_btn)
    ImageButton backBtn;
    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.send_email_btn)
    Button sendEmailBtn;
    @BindView(R.id.mainLayoutRPassword)
    ConstraintLayout mainLayout;
    @BindView(R.id.email_validation)
    TextView emailValidation;
    @BindView(R.id.timer_view)
    TextView timerView;
    @BindView(R.id.time_quest)
    TextView timeQuest;
    @BindView(R.id.time_disc)
    LinearLayout timeDisc;


    private ProgressBar progressBar;
    private PasswordManager passwordManager;
    private boolean sendEmailBtnActive = true;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_password_1);
        ButterKnife.bind(this);

        passwordManager = ((App) getApplication()).getPasswordManager();
        emailField.addTextChangedListener(activeButton);
    }

    @OnTouch(R.id.mainLayoutRPassword)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        passwordManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        passwordManager.onStop();
    }

    @OnClick(R.id.back_btn)
    public void backToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    //ON/OFF button active
    private TextWatcher activeButton = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = emailField.getText().toString().trim();
            if (!email.isEmpty() && sendEmailBtnActive) {
                sendEmailBtn.setEnabled(true);
                sendEmailBtn.setBackground(getResources().getDrawable(R.drawable.button_action_active));

            } else {
                sendEmailBtn.setEnabled(false);
                sendEmailBtn.setBackground(getResources().getDrawable(R.drawable.button_action_unactive));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.send_email_btn)
    public void goNext() {
        String email = emailField.getText().toString();
        if (emailValidation(email)) {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("EXTRA_email1session", email);
            passwordManager.sendEmail(email, intent);
        }
    }

    public void timeLock() {
        timeDisc.setVisibility(View.VISIBLE);
        timeQuest.setVisibility(View.VISIBLE);
        sendEmailBtn.setEnabled(false);
        sendEmailBtnActive = false;

        new CountDownTimer(900000, 1000) {

            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                String timeLfet = String.format("%02d:%02d", minutes, seconds);
                timerView.setText(" " + timeLfet);
            }

            public void onFinish() {
                timerView.setText("done!");
                sendEmailBtn.setEnabled(true);
                sendEmailBtnActive = true;
            }
        }.start();
    }

    public void popUpError(String text) {
        Snackbar snackbar = Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(this, R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.error_info));
        snackbar.show();
    }

    public void loginSuccess(Intent intent) {
        startActivity(intent);
        finish();
    }

    public void showError(String error) {
        Toast.makeText(EmailActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
    }


}
