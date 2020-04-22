package avocardio.avocardioapp.Helpers;

import android.view.View;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import avocardio.avocardioapp.R;
import butterknife.BindView;

public class ProgressBarAction {

    @BindView(R.id.progressbar_viewer)
    public ProgressBar progressBar;

    private ConstraintLayout constraintLayout;


    public void progressActiveted(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void progressFinished(){
        progressBar.setVisibility(View.GONE);
    }
}
