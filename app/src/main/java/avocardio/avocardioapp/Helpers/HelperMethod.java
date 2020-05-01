package avocardio.avocardioapp.Helpers;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

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
    public void hidePlaceHolder(EditText editText_field, int showMessage) {
        editText_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    editText_field.setHint(showMessage);
                else
                    editText_field.setHint("");
            }
        });
    }
}
