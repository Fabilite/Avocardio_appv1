package avocardio.avocardioapp.Activities.Others;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import avocardio.avocardioapp.Activities.OnBoarding.SplashActivity;
import avocardio.avocardioapp.DataBase.DaoSession;
import avocardio.avocardioapp.DataBase.Setup;
import avocardio.avocardioapp.DataBase.SetupDao;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageActivity extends AppCompatActivity {

    @BindView(R.id.back_btn)
    ImageButton backBtn;
    @BindView(R.id.poland_Choose)
    TextView polandChoose;
    @BindView(R.id.english_Choose)
    TextView englishChoose;
    @BindView(R.id.german_Choose)
    TextView germanChoose;
    @BindView(R.id.confirm_Btn)
    TextView confirmBtn;

    private SetupDao setupDao;
    private DaoSession daoSession;

    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);

        setupDao = daoSession.getSetupDao();

        confirmBtn.setEnabled(false);


    }

    @OnClick({R.id.poland_Choose, R.id.english_Choose, R.id.german_Choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.poland_Choose:
                countryCode("Poland");
                break;
            case R.id.english_Choose:
                countryCode("United Kingdom");
                break;
            case R.id.german_Choose:
                countryCode("Germany");
                break;
        }
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


    //ZMIANA JEZYKA
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        updateLanguage(lang);
        Intent refresh = new Intent(this, SplashActivity.class);
        startActivity(refresh);
    }

    @OnClick(R.id.back_btn)
    public void onViewClicked() {
        finish();
    }

    public void updateLanguage(String language){
        Setup setup = new Setup();
        setup.setLanguage(language);
        setupDao.insert(setup);
    }


}
