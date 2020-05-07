package avocardio.avocardioapp.Helpers;

import android.app.Activity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import avocardio.avocardioapp.R;

public class HelperMethod {

    //POPUP
    public void showErrorPopUp(int text, ConstraintLayout layout, Activity activity) {
        Snackbar snackbar = Snackbar.make(layout, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(activity, R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(activity, R.color.error_info));
        snackbar.show();
    }

    //UKRYWANIE PLACHOLDERA PO KLIKNIECIU
    public void hidePlaceHolder(EditText fieldToChange, int showMessage) {
        fieldToChange.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    fieldToChange.setHint(showMessage);
                else
                    fieldToChange.setHint("");
            }
        });
    }

    //WALIDACJA HASLA
    public boolean passwordValidation(String password, TextView view){
        if(password.length() > 5){
            view.setText(R.string.g_empty);
            return true;
        }else{
            view.setText(R.string.g_password_is_too_short);
            return false;
        }
    }

    //WALIDACJA EMAIL
    public boolean emailValidation(String email, TextView view) {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            view.setText(R.string.g_empty);
            return true;
        }else if (email.isEmpty()) {
            view.setText(R.string.g_field_is_empty);
            return false;
        }else{
            view.setText(R.string.g_wrong_email);
            return false;
        }
    }



}
