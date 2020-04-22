package avocardio.avocardioapp.Activities.Register;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RegisterActivity_1 extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();

    RegisterManager registerManager;
    @BindView(R.id.name_field)
    EditText nameField;
    @BindView(R.id.brithday_field)
    EditText brithdayField;
    @BindView(R.id.goNext)
    Button goNext;
    @BindView(R.id.brithday_btn)
    ImageView brithdayBtn;
    @BindView(R.id.female_btn)
    Button femaleBtn;
    @BindView(R.id.male_btn)
    Button maleBtn;
    @BindView(R.id.back_btn)
    ImageButton backBtn;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.language)
    LinearLayout language;

    private String sexChose = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_1);
        ButterKnife.bind(this);

        registerManager = ((App) getApplication()).getRegisterManager();

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.brithday_btn)
    public void onViewClicked() {

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        new DatePickerDialog(RegisterActivity_1.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    //informujemy Managera o starcie activity
    @Override
    protected void onStart() {
        super.onStart();
        registerManager.onAttach_1(this);
    }

    //informujemy Managera o zatrzymaniu pracy activity
    @Override
    protected void onStop() {
        super.onStop();
        registerManager.onStop_1();
    }

    @OnClick(R.id.female_btn)
    public void checkFemale() {
        sexChose = "F";
        changeBackground(femaleBtn, maleBtn);
    }

    @OnClick(R.id.male_btn)
    public void checkMale() {
        sexChose = "M";
        changeBackground(maleBtn, femaleBtn);
    }

    private void changeBackground(Button button, Button button2) {
        button.setTextColor(getColor(R.color.white));
        button.setBackground(getResources().getDrawable(R.drawable.register_button_active_f_m));

        button2.setTextColor(getColor(R.color.colorPrimary));
        button2.setBackground(getResources().getDrawable(R.drawable.register_button_f_m));
    }

    @OnClick(R.id.back_btn)
    public void goBack(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.goNext)
    public void goToNext() {
        String name = nameField.getText().toString();
        String brithday = brithdayField.getText().toString();

        //Name to UperCase / remove spaces
        String nameTransform = name.replaceAll("\\s", "");
        String finalName = nameTransform.substring(0,1).toUpperCase() + nameTransform.substring(1);


        Intent intent = new Intent(this, RegisterActivity_2.class);

        boolean hasErrors = false;

        if (name.length() < 3) {
            nameField.setError(getString(R.string.register_error_firstName));
            hasErrors = true;
        } else if (name.isEmpty()) {
            nameField.setError(getString(R.string.register_error_empty));
            hasErrors = true;
        }

        if (brithday.isEmpty()) {
            brithdayField.setError(getString(R.string.register_error_empty));
            hasErrors = true;
        }

        if (sexChose.isEmpty()) {
            //TODO
            hasErrors = true;
        }

        if (!hasErrors) {
            intent.putExtra("EXTRA_namesesion", finalName);
            intent.putExtra("EXTRA_brithdaysesion", brithday);
            intent.putExtra("EXTRA_sexsesion", sexChose);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        int style = DateFormat.MEDIUM;
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        brithdayField.setText(sdf.format(myCalendar.getTime()));
        hideKeaybord();
    }

    //UKRYWANIE KLAWIATURY
    private void hideKeaybord() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }
}
