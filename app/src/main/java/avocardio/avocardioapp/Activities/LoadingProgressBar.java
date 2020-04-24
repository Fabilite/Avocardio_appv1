package avocardio.avocardioapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import avocardio.avocardioapp.R;

public class LoadingProgressBar {

    Activity activity;
    AlertDialog alertDialog;

    public LoadingProgressBar(Activity activity){
        this.activity = activity;
    }

    public void startLoadingProgressBar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_progressbar_layout, null));
        builder.setCancelable(true);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public void stopProgressBar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        alertDialog.dismiss();
    }
}
