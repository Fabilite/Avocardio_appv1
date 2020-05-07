package avocardio.avocardioapp.Activities.OnBoarding;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
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

    Locale myLocale;
    @BindView(R.id.country_name)
    TextView countryName;

    private OnBoardingAdapter onBoardingAdapter;
    private LinearLayout linearIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
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
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    //ZMIANA JEZYKA
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, OnBoardingActivity.class);
        startActivity(refresh);

    }

    @OnClick(R.id.change_language)
    public void onViewClicked() {
      countryList();

    }

    private void countryList() {
        ListView listView = new ListView(this);
        List<String> countryData = new ArrayList<>();
        countryData.add("Poland");
        countryData.add("Germany");
        countryData.add("United Kingdom");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, countryData);
        listView.setAdapter(arrayAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(OnBoardingActivity.this);
        builder.setCancelable(true);
        builder.setView(listView);
        final AlertDialog dialog = builder.create();

        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String country = countryData.get(position).toString();
                countryName.setText(country);
                countryCode(country);
                dialog.dismiss();
            }
        });
    }

    private void countryCode(String country) {
        switch (country) {
            case "Poland":
                setLocale("PL");
                break;
            case "Germany":
                setLocale("DE");
                break;
            case "United Kingdom":
                setLocale("EN");
                break;
            default:
                setLocale("EN");
                break;
        }
    }
}
