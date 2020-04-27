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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;
import java.util.TimeZone;

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
    private boolean hasErrors = false;
    private boolean sexActive = false;
    private boolean isAdult = false;

    private String day = "";
    private String month = "";
    private String year = "";

    private String TAG = "Register activity ------------------";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_1);
        ButterKnife.bind(this);

        registerManager = ((App) getApplication()).getRegisterManager();

        editTextValidation();

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

    public void editTextValidation() {

        brithdayField.addTextChangedListener(new TextWatcher() {

            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1950) ? 1950 : (year > 2020) ? 2020 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));


                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    brithdayField.setText(current);
                    brithdayField.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
    }

    @OnClick(R.id.goNext)
    public void goToNext(){
        String name = nameField.getText().toString();
        String brithday = brithdayField.getText().toString();
        Intent intent = new Intent(this, RegisterActivity_2.class);

        if(name.length() > 2){
            if (!name.matches("[A-Z][a-z]*")) {
                firstNameValidation.setText(getString(R.string.g_errors_notvalid));
                hasErrors = true;
            }else {
                firstNameValidation.setText(getString(R.string.g_empty));
                hasErrors = false;
            }
        }else if (name.isEmpty()) {
            firstNameValidation.setText(getString(R.string.g_error_empty));
            hasErrors = true;
        }else if(name.length() < 3) {
            firstNameValidation.setText(getString(R.string.g_errors_notvalid));
            hasErrors = true;
        }


        if(brithday.length() < 10) {
            brithdayFieldValidation.setText(getString(R.string.g_errors_notvalid));
            hasErrors = true;
        } else if (brithday.length() == 10) {
            day = brithday.substring(0, 2);
            month = brithday.substring(3, 5);
            year = brithday.substring(6, brithday.length());
            Log.i("-----------", day + "------------");
            Log.i("-----------", month + "------------");
            Log.i("-----------", year + "------------");

            if(day.matches("[0-9]*") & month.matches("[0-9]*") & year.matches("[0-9]*")) {
                if (!britdayDateValidation(year, month, day)) {
                    brithdayFieldValidation.setText(getString(R.string.s_britday_adult_validation));
                    hasErrors = true;
                    Log.i("-----------", brithday + "------------");
                } else {
                    brithdayFieldValidation.setText(getString(R.string.g_empty));
                    hasErrors = false;
                }
            }else{
                brithdayFieldValidation.setText(getString(R.string.g_errors_notvalid));
                hasErrors = true;
            }
        } else{
            brithdayFieldValidation.setText(getString(R.string.g_errors_notvalid));
            hasErrors = true;
        }

        if (sexChose.isEmpty()) {
            popUpError("Choose your sex",R.color.error_info);
            hasErrors = true;
        }else{
            hasErrors = false;
        }

        if (!hasErrors) {
            String brithdayDate = year + "-" + month + "-" + day;

            intent.putExtra("EXTRA_namesesion", name);
            intent.putExtra("EXTRA_brithdaysesion", brithdayDate);
            intent.putExtra("EXTRA_sexsesion", sexChose);
            startActivity(intent);
        }
    }

    //Ukrywanie klawiatury
    @OnTouch(R.id.mainLayoutRegister)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    // aktualizacja tabel kalendarza
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
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

    //Walidacja female
    @OnClick(R.id.female_btn)
    public void checkFemale() {
        sexChose = "F";
        changeBackground(femaleBtn, maleBtn);
        if (!sexActive) {
            changeBtnActionBackgroundActive(goNext, 1);
            sexActive = true;
        }
    }

    //Walidacja male
    @OnClick(R.id.male_btn)
    public void checkMale() {
        sexChose = "M";
        changeBackground(maleBtn, femaleBtn);
        if (!sexActive) {
            changeBtnActionBackgroundActive(goNext, 1);
            sexActive = true;
        }
    }

    public void popUpError(String text, int color) {
        Snackbar snackbar = Snackbar.make(mainLayoutRegister, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(this, R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, color));
        snackbar.show();
    }

    //ZMIANA BACKGROUND
    private void changeBackground(Button button, Button button2) {
        button.setTextColor(getColor(R.color.white));
        button.setBackground(getResources().getDrawable(R.drawable.register_button_active_f_m));

        button2.setTextColor(getColor(R.color.colorPrimary));
        button2.setBackground(getResources().getDrawable(R.drawable.register_button_f_m));
    }

    //zmiana coloru buttona
    private void changeBtnActionBackgroundActive(Button button, int active) {
        if (active == 1) {
            button.setTextColor(getColor(R.color.white));
            button.setBackground(getResources().getDrawable(R.drawable.button_action_active));
        } else {
            button.setTextColor(getColor(R.color.white));
            button.setBackground(getResources().getDrawable(R.drawable.button_action_unactive));
        }
    }


    //VALIDACJA WIEKU
    @SuppressLint("WrongConstant")
    public boolean britdayDateValidation(String yearB, String monthB, String dayB) {

        int year = Integer.parseInt(yearB);
        int month = Integer.parseInt(monthB);
        int day = Integer.parseInt(dayB);

        java.util.Calendar calendar = java.util.Calendar.getInstance(TimeZone.getDefault());

        int todayYear = calendar.get(java.util.Calendar.YEAR);
        int todayMont = calendar.get(java.util.Calendar.MONTH) + 1;
        int todayDay = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        if ((todayYear - year) > 16) {
            return true;
        } else if ((todayYear - year == 16)) {
            if (todayMont > month) {
                return true;
            } else if (todayMont == month) {
                if (todayDay >= day) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
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
        };

        new DatePickerDialog(RegisterActivity_1.this, R.style.MyDataPickerTheme, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
