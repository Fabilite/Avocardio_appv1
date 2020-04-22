package avocardio.avocardioapp.Activities.Password;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailActivity extends AppCompatActivity {

    @BindView(R.id.back_btn)
    ImageButton backBtn;
    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.send_email_btn)
    Button sendEmailBtn;


    private ProgressBar progressBar;
    private PasswordManager passwordManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remember_password_activity);
        ButterKnife.bind(this);
        passwordManager = ((App) getApplication()).getPasswordManager();
    }

    private void startProgress(){
        this.progressBar = findViewById(R.id.progressbar_viewer);
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

    @OnClick(R.id.send_email_btn)
    public void goNext() {
        String email = emailField.getText().toString();
        Intent intent = new Intent(this, ResetPasswordActivity.class);

        boolean hasErrors = false;

        //EMAIL VALIDATION
        if (email.isEmpty()) {
            emailField.setError(getString(R.string.register_error_empty));
            hasErrors = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError(getString(R.string.register_error_email_not_valid));
            hasErrors = true;
        }

        if (!hasErrors) {
            intent.putExtra("EXTRA_email1session", email);
            passwordManager.sendEmail(email,intent);
        }
    }

    public void loginSuccess(Intent intent) {
        startActivity(intent);
        finish();
    }

    public void showError(String error) {
        Toast.makeText(EmailActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
    }
}
