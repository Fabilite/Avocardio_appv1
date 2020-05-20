package avocardio.avocardioapp.Activities.Register;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import avocardio.avocardioapp.Activities.ActivationAcount.ActivationAccountActivity;
import avocardio.avocardioapp.Activities.Others.LanguageActivity;
import avocardio.avocardioapp.Connections.Api.App;
import avocardio.avocardioapp.Helpers.Generates;
import avocardio.avocardioapp.Helpers.HelperMethod;
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
    Button country_button;
    @BindView(R.id.back_btn)
    ImageButton backBtn;
    @BindView(R.id.mainLayoutRegister2)
    ConstraintLayout mainLayout;
    @BindView(R.id.email_field_validation)
    TextView emailFieldValidation;
    @BindView(R.id.password_field_validation)
    TextView passwordFieldValidation;
    @BindView(R.id.change_language)
    LinearLayout changeLanguage;


    private RegisterManager registerManager;
    public HelperMethod helper = new HelperMethod();
    private String newsleter = "0";
    private boolean acceptRegulations = false;
    private String countryC = "";

    private Generates generates = new Generates();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);
        ButterKnife.bind(this);
        registerManager = ((App) getApplication()).getRegisterManager();
        activityEngine();

    }


    @OnTouch(R.id.mainLayoutRegister2)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void activityEngine() {
        helper.hidePlaceHolder(editTextEmail, R.string.g_email_placeholder);
        helper.hidePlaceHolder(editTextPassword, R.string.g_password_placeholder);
        editTextPassword.addTextChangedListener(activeButton);
        editTextEmail.addTextChangedListener(activeButton);
        countryList();
    }

    //COUNTRY PICKER
    private void countryList() {
        ListView listView = new ListView(this);
        List<String> countryData = new ArrayList<>();
        countryData.add("Poland");
        countryData.add("Germany");
        countryData.add("United Kingdom");
        countryData.add("Italy");
        countryData.add("Spain");
        countryData.add("Portugal");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, countryData);
        listView.setAdapter(arrayAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity_2.this);
        builder.setCancelable(true);
        builder.setView(listView);
        final AlertDialog dialog = builder.create();

        country_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String country = countryData.get(position).toString();
                country_button.setText(country);
                countryCode(country);
                dialog.dismiss();
            }
        });
    }

    private void countryCode(String country) {
        switch (country) {
            case "Poland":
                countryC = "PL";
                break;
            case "Germany":
                countryC = "DE";
                break;
            case "Italy":
                countryC = "DE";
                break;
            case "Spain":
                countryC = "ES";
                break;
            case "Portugal":
                countryC = "PT";
                break;
            case "United Kingdom":
                countryC = "UK";
                break;
            default:
                countryC = "";
                break;
        }
    }

    //UAKTYWNIANIE BUTTONA
    private TextWatcher activeButton = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if ((!editTextEmail.getText().toString().isEmpty()) && (!editTextPassword.getText().toString().isEmpty())) {
                createAccountBtn.setEnabled(true);
                createAccountBtn.setBackground(getResources().getDrawable(R.drawable.button_action_active));
            } else {
                createAccountBtn.setEnabled(false);
                createAccountBtn.setBackground(getResources().getDrawable(R.drawable.button_action_unactive));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String name;
        String brithday;
        String sex;
        String language;

        if (countryC.length() != 2) {
            popUpError("You must choose your country");
        }

        if (!acceptRegulations) {
            popUpError("You must accept the terms");
        }

        if (helper.emailValidation(email, emailFieldValidation) & helper.passwordValidation(password, passwordFieldValidation) && (countryC.length() == 2) && acceptRegulations) {
            Intent intent = getIntent();
            name = intent.getStringExtra("EXTRA_namesesion");
            brithday = intent.getStringExtra("EXTRA_brithdaysesion");
            sex = intent.getStringExtra("EXTRA_sexsesion");
            try {
                registerManager.clearUserStorage();
                registerManager.register(email, generates.getSHA512(password), name, brithday, sex, newsleter, countryC);
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
        finish();
    }

    public void popUpError(String text) {

        //R ID!!!!!!!!!!!!!!!!!!!!!!!!!!!


        Snackbar snackbar = Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(this, R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.error_info));
        snackbar.show();
    }

    @OnClick({R.id.checkbox_regulations, R.id.checkbox_newsletter})
    public void onNewsletterClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkbox_regulations:
                if (checked) {
                    acceptRegulations = true;
                } else {
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

    @OnClick(R.id.change_language)
    public void onViewClicked() {
        startActivity(new Intent(this, LanguageActivity.class));
    }
}
