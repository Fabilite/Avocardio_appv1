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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import avocardio.avocardioapp.Activities.App;
import avocardio.avocardioapp.Activities.Main.MainActivity;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RegisterActivity_1 extends AppCompatActivity {

    @BindView(R.id.Firstname)
    EditText Firstname;
    @BindView(R.id.Birthday_date)
    EditText BirthdayDate;
    @BindView(R.id.next_page)
    Button nextPage;
    @BindView(R.id.Birthday)
    ImageButton Birthday;
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };


    final Calendar myCalendar = Calendar.getInstance();
    RegisterManager registerManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_1);
        ButterKnife.bind(this);

        registerManager = ((App) getApplication()).getRegisterManager();
    }


    @OnClick(R.id.Birthday)
    public void onViewClicked() {
        new DatePickerDialog(RegisterActivity_1.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.next_page)
    public void onNextPage(){

    }


    public void registerSucessful() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void showError(String error) {
        Toast.makeText(RegisterActivity_1.this, "Error " + error, Toast.LENGTH_LONG).show();

    }

    public void showProgress(boolean b) {
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        int style = DateFormat.MEDIUM;
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        BirthdayDate.setText(sdf.format(myCalendar.getTime()));
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
