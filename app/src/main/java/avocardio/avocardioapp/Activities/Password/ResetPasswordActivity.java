package avocardio.avocardioapp.Activities.Password;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.chaos.view.PinView;
import com.google.android.material.snackbar.Snackbar;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

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
    @BindView(R.id.mainLayoutRCode)
    ConstraintLayout mainLayout;

    PasswordManager passwordManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_activity);
        ButterKnife.bind(this);

        passwordManager = ((App) getApplication()).getPasswordManager();
    }

    @OnTouch(R.id.mainLayoutRCode)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(pinView.getWindowToken(), 0);
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

        if(code.length() < 6) {
            popUpError("Code is to short");
            hasErrors = true;
        }else if(pinView.length() == 6) {
            hasErrors = false;
            hideKeyboard();
        }

        if (!hasErrors) {
            Intent intent = getIntent();
            String email = intent.getStringExtra("EXTRA_email1session");
            information.setTextColor(getColor(R.color.colorPrimary));
            try {
                passwordManager.tryReset(email, code);
            } catch (NullPointerException e) {
                System.out.println("" + e.getMessage());
            }
        }
    }

    public void popUpError(String text) {
        Snackbar snackbar = Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(this, R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.error_info));
        snackbar.show();
    }

    public void loginSuccess() {
        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
        finish();
    }
}
