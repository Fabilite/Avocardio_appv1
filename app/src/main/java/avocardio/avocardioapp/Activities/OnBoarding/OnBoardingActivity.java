package avocardio.avocardioapp.Activities.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnBoardingActivity extends AppCompatActivity {

    @BindView(R.id.next_btn)
    ImageView nextBtn;
    @BindView(R.id.onboarding_viewpager)
    ViewPager2 onboardingViewpager;
    @BindView(R.id.onboarding_indicators)
    LinearLayout onboardingIndicators;
    @BindView(R.id.skip_btn)
    TextView skipBtn;
    private OnBoardingAdapter onBoardingAdapter;
    private LinearLayout linearIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_activity);
        ButterKnife.bind(this);

        linearIndicator = findViewById(R.id.onboarding_indicators);

        setDateToOnboarding();

        ViewPager2 viewPager = findViewById(R.id.onboarding_viewpager);
        viewPager.setAdapter(onBoardingAdapter);

        setIndicators();
        setCurrentIndicators(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicators(position);
            }
        });

    }

    private void setDateToOnboarding() {
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem item1 = new OnBoardingItem();
        item1.setTitle("Personalise your diet");
        item1.setDescription("Avocardio diet is way to eat\nhealthy and tasty meals,\nwhich you will love.");
        item1.setImage(R.drawable.illustration_diet);

        OnBoardingItem item2 = new OnBoardingItem();
        item2.setTitle("Work out with pleasure");
        item2.setDescription("Avoacrdio trening guarantees\nyou a set of favourite exercises\nang good motivation.");
        item2.setImage(R.drawable.illustration_fit);

        OnBoardingItem item3 = new OnBoardingItem();
        item3.setTitle("Observe your progress");
        item3.setDescription("The diagram allows you to observe\nyour progress and way\nto the goal");
        item3.setImage(R.drawable.illustration_goal);

        onBoardingItems.add(item1);
        onBoardingItems.add(item2);
        onBoardingItems.add(item3);

        onBoardingAdapter = new OnBoardingAdapter(onBoardingItems);
    }

    private void setIndicators() {
        ImageView[] imageViews = new ImageView[onBoardingAdapter.getItemCount()];

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(12, 0, 12, 0);
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(getApplicationContext());
            imageViews[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
            imageViews[i].setLayoutParams(layoutParams);
            linearIndicator.addView(imageViews[i]);
        }
    }

    private void setCurrentIndicators(int index) {
        int childCOunt = linearIndicator.getChildCount();
        for (int i = 0; i < childCOunt; i++) {
            ImageView imageView = (ImageView) linearIndicator.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            }
        }
    }

    @OnClick(R.id.skip_btn)
    public void onSkipClicked() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.next_btn)
    public void onViewClicked() {
        startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
        finish();
    }
}
