package avocardio.avocardioapp.Activities.Register;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.GregorianCalendar;
import java.util.Locale;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

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
    @BindView(R.id.mainLayoutRegister)
    ConstraintLayout mainLayoutRegister;
    @BindView(R.id.first_name_validation)
    TextView firstNameValidation;
    @BindView(R.id.brithday_field_validation)
    TextView brithdayFieldValidation;

    private String sexChose = "";
    private boolean isAdult = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_1);
        ButterKnife.bind(this);

        registerManager = ((App) getApplication()).getRegisterManager();

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

    @OnClick(R.id.back_btn)
    public void goBack() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.goNext)
    public void goToNext() {
        String name = nameField.getText().toString();
        String brithday = brithdayField.getText().toString();
        String finalName = "";

        Intent intent = new Intent(this, RegisterActivity_2.class);

        boolean hasErrors = false;

        if (name.length() > 3) {
            firstNameValidation.setText("");
        } else if (name.isEmpty()) {
            firstNameValidation.setText(getString(R.string.register_error_empty));
            hasErrors = true;
        } else if (name.length() < 3) {
            firstNameValidation.setText(R.string.register_error_firstName);
            hasErrors = true;
        } else if (!name.isEmpty()) {
            finalName = name.replaceAll("\\s", "");
            finalName = name.substring(0, 1).toUpperCase() + name.substring(1);
        }


        if (brithday.isEmpty()) {
            brithdayFieldValidation.setText(getString(R.string.register_error_empty));
            hasErrors = true;
        } else if (!isAdult) {
            brithdayFieldValidation.setText("Sorry, you must be 16 years old");
            hasErrors = true;
        }

        if (sexChose.isEmpty()) {
            //TODO
            hasErrors = true;
        }

        if (!hasErrors) {
            intent.putExtra("EXTRA_namesesion", name);
            intent.putExtra("EXTRA_brithdaysesion", brithday);
            intent.putExtra("EXTRA_sexsesion", sexChose);
            startActivity(intent);
        }
    }

    @OnTouch(R.id.mainLayoutRegister)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        int style = DateFormat.MEDIUM;
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        brithdayField.setText(sdf.format(myCalendar.getTime()));
        hideKeaybord();

    }

    //Ikona kalendarza jest klikalna
    @OnTouch(R.id.brithday_field)
    public void setDatePicker(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (brithdayField.getRight() - brithdayField.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        dateAction();
                        return true;
                    }
                }
                return false;
            }
        });
    }


//    @OnFocusChange(R.id.name_field)
//    public void setNameField(View view) {
//        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    nameField.setHint("");
//                else
//                    nameField.setHint(R.string.register_status_namehint);
//            }
//        });
//    }

//    @OnFocusChange(R.id.brithday_field)
//    public void setBrithdayChange(View view) {
//        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    brithdayField.setHint("");
//                else
//                    brithdayField.setHint(R.string.register_status_brithdayhint);
//            }
//        });
//    }

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

    //ZMIANA BACKGROUND
    private void changeBackground(Button button, Button button2) {
        button.setTextColor(getColor(R.color.white));
        button.setBackground(getResources().getDrawable(R.drawable.register_button_active_f_m));

        button2.setTextColor(getColor(R.color.colorPrimary));
        button2.setBackground(getResources().getDrawable(R.drawable.register_button_f_m));
    }

    private void changeBtnActionBackgroundActive(Button button) {
        button.setTextColor(getColor(R.color.white));
        button.setBackground(getResources().getDrawable(R.drawable.button_action_active));
    }


    //VALIDACJA WIEKU
    @SuppressLint("WrongConstant")
    public void onDateSet(int year, int month, int day) {
        GregorianCalendar userAge = new GregorianCalendar(year, month, day);
        GregorianCalendar minAdultAge = new GregorianCalendar();
        minAdultAge.add(Calendar.YEAR, -15);
        if (minAdultAge.before(userAge)) {
            isAdult = false;
        }else{
            isAdult = true;
        }
    }

    //UKRYWANIE KLAWIATURY
    private void hideKeaybord() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }

    public void dateAction() {

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
            onDateSet(year, monthOfYear, dayOfMonth);

        };

        new DatePickerDialog(RegisterActivity_1.this, R.style.MyDataPickerTheme, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
