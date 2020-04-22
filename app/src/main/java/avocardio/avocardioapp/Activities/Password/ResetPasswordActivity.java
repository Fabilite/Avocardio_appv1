package avocardio.avocardioapp.Activities.Password;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.back_btn)
    ImageButton backBtn;
    EditText number6;
    @BindView(R.id.confirm_button)
    Button confirmButton;
    @BindView(R.id.time_view)
    TextView timeView;
    @BindView(R.id.information)
    TextView information;
    @BindView(R.id.pinView)
    PinView pinView;

    PasswordManager passwordManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_activity);
        ButterKnife.bind(this);

        passwordManager = ((App) getApplication()).getPasswordManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        passwordManager.onAttach_Pas(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        passwordManager.onStopPas();
    }


    @OnClick(R.id.back_btn)
    public void backTo() {
        startActivity(new Intent(this, EmailActivity.class));
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @OnClick(R.id.confirm_button)
    public void onConfirmBtnClicked() {
        boolean hasErrors = false;

        String code = pinView.getText().toString();

        if (code.length() < 6) {
            pinView.setLineColor(getColor(R.color.error_info));
            pinView.setTextColor(getColor(R.color.error_info));
            information.setText("Your reset code isn't valid.");
            information.setTextColor(getColor(R.color.error_info));
            hasErrors = true;
        }

        if (!hasErrors) {
            Intent intent = getIntent();
            String email = intent.getStringExtra("EXTRA_email1session");
            Log.i(ResetPasswordActivity.class.getSimpleName(), "--------------------------------------------------------------------------email" + email);
            pinView.setLineColor(getColor(R.color.colorPrimary));
            pinView.setTextColor(getColor(R.color.colorPrimary));
            information.setText("Your reset code is correct.");
            information.setTextColor(getColor(R.color.colorPrimary));

            try {
                passwordManager.tryReset(email, code);
            } catch (NullPointerException e) {
                System.out.println("" + e.getMessage());
            }
        }
    }

    public void loginSuccess() {
        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
        finish();
    }

    public void showError(String error) {
        Toast.makeText(ResetPasswordActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
    }
}
