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

public class RegisterActivity_4_5 extends AppCompatActivity {
    @BindView(R.id.previouswindowBtn)
    ImageView previouswindowBtn;
    @BindView(R.id.next_widow_btn)
    Button nextWidowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_4_5);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.previouswindowBtn)
    public void onViewClicked() {
        this.finish();
    }

    @OnClick(R.id.next_widow_btn)
    public void onViewClicked_nextbtn() {
        startActivity(new Intent(this, RegisterActivity_5_5.class));
    }
}
