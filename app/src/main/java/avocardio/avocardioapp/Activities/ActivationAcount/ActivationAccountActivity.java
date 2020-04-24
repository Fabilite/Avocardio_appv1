package avocardio.avocardioapp.Activities.ActivationAcount;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.chaos.view.PinView;
import com.google.android.material.snackbar.Snackbar;

import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class ActivationAccountActivity extends AppCompatActivity {

    @BindView(R.id.back_btn)
    ImageButton backBtn;
    @BindView(R.id.information)
    TextView information;
    @BindView(R.id.pinView)
    PinView pinView;
    @BindView(R.id.confirm_button)
    Button confirmButton;
    @BindView(R.id.time_view)
    TextView timeView;
    @BindView(R.id.mainLayoutActivation)
    ConstraintLayout mainLayout;

    ActivationManager activationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activation_account_activity);
        ButterKnife.bind(this);

        activationManager = ((App) getApplication()).getActivationManager();

    }

    @OnTouch(R.id.mainLayoutActivation)
    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        activationManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        activationManager.onStop();
    }

//    public void changeView(TextView view) {
//        if (view.length() != 0) {
//            view.setTextColor(getResources().getColorStateList(R.color.colorPrimary));
//            view.setBackground(getResources().getDrawable(R.drawable.elipse_edittext_background_active));
//        } else {
//            view.setTextColor(getResources().getColorStateList(R.color.dimgray));
//            view.setBackground(getResources().getDrawable(R.drawable.elipse_edittext_background_unactive));
//        }
//    }

    //UKRYWANIE KLAWIATURY
    private void hideKeaybord() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }


    @OnClick(R.id.back_btn)
    public void onBackBtnClicked() {
        startActivity(new Intent(this, LoginActivity.class));
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
            information.setText("Your activation code isn't valid.");
            information.setTextColor(getColor(R.color.error_info));
            hasErrors = true;
        }
        if(code.length() == 6){
            hideKeaybord();
        }

        if (!hasErrors) {
            pinView.setLineColor(getColor(R.color.colorPrimary));
            pinView.setTextColor(getColor(R.color.colorPrimary));
            information.setText("Your activation code is correct.");
            information.setTextColor(getColor(R.color.colorPrimary));

            activationManager.tryToActive(code);
        }
    }


    public void loginSuccess() {
        startActivity(new Intent(ActivationAccountActivity.this, LoginActivity.class));
        finish();
    }

    public void showError(String error) {
        Toast.makeText(ActivationAccountActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
    }

    public void showProgress(boolean b) {
        // loginBtn.setEnabled(!b);
    }

    public void popUpError(String text) {
        Snackbar snackbar = Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(this, R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.error_info));
        snackbar.show();
    }
}
