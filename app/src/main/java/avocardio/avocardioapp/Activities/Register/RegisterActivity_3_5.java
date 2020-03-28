package avocardio.avocardioapp.Activities.Register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity_3_5 extends AppCompatActivity {

    @BindView(R.id.previouswindowBtn)
    ImageView previouswindowBtn;
    @BindView(R.id.next_widow_btn)
    Button nextWidowBtn;
    @BindView(R.id.female_button)
    ImageButton femaleButton;
    @BindView(R.id.male_button)
    ImageButton maleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_3_5);
        ButterKnife.bind(this);
    }

    // ZAMIAST IMAGE VIEW UZYJ CHECK BOX!

    @OnClick(R.id.previouswindowBtn)
    public void onViewClickedPreviousActivity() {
        this.finish();
    }

    @OnClick(R.id.next_widow_btn)
    public void onNextBtnClick(){
        startActivity(new Intent(this, RegisterActivity_4_5.class));
    }
}
