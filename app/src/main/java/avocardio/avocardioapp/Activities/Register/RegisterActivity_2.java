package avocardio.avocardioapp.Activities.Register;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import avocardio.avocardioapp.Activities.ActivationAcount.ActivationAccountActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.Helpers.Generates;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity_2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.editText_email)
    EditText editTextEmail;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.editText_password)
    EditText editTextPassword;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.action_bar_spinner_country)
    Spinner country_spinner;
    @BindView(R.id.checkbox_regulations)
    CheckBox checkboxRegulations;
    @BindView(R.id.checkbox_newsletter)
    CheckBox checkboxNewsletter;
    @BindView(R.id.create_account_btn)
    Button createAccountBtn;

    RegisterManager registerManager;

    @BindView(R.id.back_btn)
    ImageButton backBtn;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    private String email;
    private String password;
    private String country;
    private String newsleter = "0";
    private String acceptTerms = "0";

    private final String TAG = "SPINNER TEST";

    private Generates generates = new Generates();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_2);
        ButterKnife.bind(this);

        registerManager = ((App) getApplication()).getRegisterManager();
        spinnerDate();

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerManager.onAttach_2(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        registerManager.onStop_2();
    }

    @OnClick(R.id.create_account_btn)
    public void tryToRegister() {
        String name;
        String brithday;
        String sex;
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        boolean hasErrors = false;

        if (email.isEmpty()) {
            editTextEmail.setError(getText(R.string.register_error_empty));
            hasErrors = true;
        }
        if (!isEmailValid(email)) {
            editTextEmail.setError(getText(R.string.register_error_email_not_valid));
            hasErrors = true;
        }
        if (password.length() < 6) {
            editTextPassword.setError(getText(R.string.register_error_password_short));
            hasErrors = true;
        }
        if(acceptTerms.equals("0")){
            Toast toast = Toast.makeText(this,"You must accept the terms", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL,Gravity.CENTER_VERTICAL,0);
            toast.show();
            hasErrors = true;
        }

        if (!hasErrors) {
            Intent intent = getIntent();
            name = intent.getStringExtra("EXTRA_namesesion");
            brithday = intent.getStringExtra("EXTRA_brithdaysesion");
            sex = intent.getStringExtra("EXTRA_sexsesion");
            try {
                registerManager.register(email, generates.getSHA512(password), name, brithday, sex, newsleter);
            } catch (NullPointerException e) {
                System.out.println("" + e.getMessage());
            }
        }
    }

    @OnClick(R.id.back_btn)
    public void goBack(){
        startActivity(new Intent(this, RegisterActivity_1.class));
        finish();
    }


    public void registerSucessful() {
        startActivity(new Intent(this, ActivationAccountActivity.class));
        finish();
    }

    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

//    public void showProgress(boolean b) {
//    }

    //email valid
    static boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private void spinnerDate() {
        country_spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.Country_array, android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(arrayAdapter);
        country_spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    }

    //UKRYWANIE KLAWIATURY
    private void hideKeaybord() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @OnClick({R.id.checkbox_regulations, R.id.checkbox_newsletter})
    public void onNewsletterClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkbox_regulations:
                if (checked)
                    acceptTerms = "1";
                else {
                    acceptTerms = "0";
                }
                break;
            case R.id.checkbox_newsletter:
                if (checked)
                    newsleter = "1";
                else {
                    newsleter = "0";
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
