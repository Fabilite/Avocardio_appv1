package avocardio.avocardioapp.Activities.Register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Activities.MainActivity;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity_5_5 extends AppCompatActivity {

    @BindView(R.id.accept_btn)
    Button acceptBtn;
    @BindView(R.id.resignation_btn)
    Button resignationBtn;
    @BindView(R.id.previouswindowBtn)
    ImageView previouswindowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_5_5);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.accept_btn)
    public void onAcceptBtnClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.resignation_btn)
    public void onResignationBtnClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick(R.id.previouswindowBtn)
    public void onPreviouswindowBtnClicked() {
        this.finish();
    }
}
