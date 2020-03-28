package avocardio.avocardioapp.Activities.Register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity_2_5 extends AppCompatActivity {

    @BindView(R.id.previouswindowBtn)
    ImageView previouswindowBtn;
    @BindView(R.id.next_widow_btn)
    Button nextWidowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_2_5);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.previouswindowBtn)
    public void onViewClickedPreviousActivity() {
        this.finish();
    }

    @OnClick(R.id.next_widow_btn)
    public void onViewClickedNextActivity() {
        startActivity(new Intent(this, RegisterActivity_3_5.class));
    }
}
