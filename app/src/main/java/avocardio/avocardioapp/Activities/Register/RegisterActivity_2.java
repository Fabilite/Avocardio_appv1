package avocardio.avocardioapp.Activities.Register;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Patterns;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import avocardio.avocardioapp.Activities.ActivationAcount.ActivationAccountActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.Helpers.Generates;
import avocardio.avocardioapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class RegisterActivity_2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.editText_email)
    EditText editTextEmail;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.password_field)
    EditText editTextPassword;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.checkbox_regulations)
    CheckBox checkboxRegulations;
    @BindView(R.id.checkbox_newsletter)
    CheckBox checkboxNewsletter;
    @BindView(R.id.create_account_btn)
    Button createAccountBtn;
    @BindView(R.id.action_bar_spinner_country)
    Spinner country_spinner;
    @BindView(R.id.back_btn)
    ImageButton backBtn;
    @BindView(R.id.mainLayoutRegister2)
    ConstraintLayout mainLayout;
    @BindView(R.id.email_field_validation)
    TextView emailFieldValidation;
    @BindView(R.id.password_field_validation)
    TextView passwordFieldValidation;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    private RegisterManager registerManager;


    private String newsleter = "0";
    private boolean acceptRegulations = false;

    private Generates generates = new Generates();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_2);
        ButterKnife.bind(this);

        registerManager = ((App) getApplication()).getRegisterManager();
        spinnerDate();

    }

    @OnTouch(R.id.mainLayoutRegister2)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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

    private boolean emailValidation(String email) {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailFieldValidation.setText(getString(R.string.g_empty));
            return true;
        }else if (email.isEmpty()) {
            emailFieldValidation.setText(getString(R.string.g_field_is_empty));
            return false;
        }else{
            emailFieldValidation.setText(getString(R.string.g_wrong_email));
            return false;
        }
    }

    private boolean passwordValidation(String password){
        if(password.length() > 5){
            passwordFieldValidation.setText(getString(R.string.g_empty));
            return true;
        }else{
            passwordFieldValidation.setText(getString(R.string.g_password_is_too_short));
            return false;
        }
    }

    @OnClick(R.id.create_account_btn)
    public void tryToRegister() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String name;
        String brithday;
        String sex;

        if(!acceptRegulations){
            popUpError("You must accept the terms");
        }

        if(emailValidation(email) & passwordValidation(password) & acceptRegulations){
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
    public void goBack() {
        startActivity(new Intent(this, RegisterActivity_1.class));
        finish();
    }


    public void registerSucessful() {
        startActivity(new Intent(this, ActivationAccountActivity.class));
        popUpError("SUCCES");
        finish();
    }

    public void popUpError(String text) {
        Snackbar snackbar = Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(this, R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.error_info));
        snackbar.show();
    }

    private void spinnerDate() {
        country_spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.Country_array, android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(arrayAdapter);
        country_spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    }


    @OnClick({R.id.checkbox_regulations, R.id.checkbox_newsletter})
    public void onNewsletterClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkbox_regulations:
                if (checked) {
                    acceptRegulations = true;
                }
                else {
                    acceptRegulations = false;
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
