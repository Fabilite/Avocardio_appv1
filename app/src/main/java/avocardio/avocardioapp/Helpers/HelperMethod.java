package avocardio.avocardioapp.Helpers;

import android.app.Activity;

import androidx.constraintlayout.widget.ConstraintLayout;

public class HelperMethod {

    private String text;
    private ConstraintLayout layout;
    private Activity activity;

    public HelperMethod(String text, ConstraintLayout layout, Activity activity) {
        this.activity = activity;
        this.layout = layout;
        this.text = text;
    }
//
//    public void popUpError(text, layout, activity) {
//        Snackbar snackbar = Snackbar.make(layout, text, Snackbar.LENGTH_LONG)
//                .setActionTextColor(ContextCompat.getColor(activity, R.color.white));
//        View sbView = snackbar.getView();
//        sbView.setBackgroundColor(ContextCompat.getColor(activity, R.color.error_info));
//        snackbar.show();
//    }
}
