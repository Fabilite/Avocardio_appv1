package avocardio.avocardioapp.Activities.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Activities.Others.LanguageActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.DataBase.SetupDao;
import avocardio.avocardioapp.Helpers.UserStorage;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnBoardingActivity extends AppCompatActivity {


    @BindView(R.id.onboarding_viewpager)
    ViewPager2 onboardingViewpager;
    @BindView(R.id.onboarding_indicators)
    LinearLayout onboardingIndicators;
    @BindView(R.id.skip_btn)
    TextView skipBtn;
    @BindView(R.id.lets_start)
    Button letsStart;
    @BindView(R.id.change_language)
    LinearLayout changeLanguage;
    @BindView(R.id.country_name)
    TextView countryName;

    private OnBoardingAdapter onBoardingAdapter;
    private LinearLayout linearIndicator;
    private SetupDao setupDao;

    private UserStorage userStorage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);

        linearIndicator = findViewById(R.id.onboarding_indicators);

        setDateToOnboarding();

//        DaoSession daoSession = ((App)getApplication()).getDaoSession();
//        setupDao = daoSession.getSetupDao();

        ViewPager2 viewPager = findViewById(R.id.onboarding_viewpager);
        viewPager.setAdapter(onBoardingAdapter);
        userStorage = ((App) getApplication()).getUserStorage();

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
        item1.setTitle(getString(R.string.l_screen_first_title));
        item1.setDescription(getString(R.string.l_screen_first_desc));
        item1.setImage(R.drawable.image1);
        OnBoardingItem item2 = new OnBoardingItem();
        item2.setTitle(getString(R.string.l_screen_second_title));
        item2.setDescription(getString(R.string.l_screen_second_desc));
        item2.setImage(R.drawable.image2);

        OnBoardingItem item3 = new OnBoardingItem();
        item3.setTitle(getString(R.string.l_screen_third_title));
        item3.setDescription(getString(R.string.l_screen_third_desc));
        item3.setImage(R.drawable.image3);

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
        onBoardingClicekd(1);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    @OnClick(R.id.change_language)
    public void onViewClicked() {
        startActivity(new Intent(this, LanguageActivity.class));
    }

    private void onBoardingClicekd(int click) {
        userStorage.saveOnBoardingClick(click);
    }

}
