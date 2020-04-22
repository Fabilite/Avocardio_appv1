package avocardio.avocardioapp.Activities.Password;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.annotation.Annotation;

import avocardio.avocardioapp.Connections.Api.AvocardioApi;
import avocardio.avocardioapp.Connections.ResReq.EmailRequest;
import avocardio.avocardioapp.Connections.ResReq.ErrorResponse;
import avocardio.avocardioapp.Connections.ResReq.PasswordRequset;
import avocardio.avocardioapp.Connections.ResReq.PasswordResponse;
import avocardio.avocardioapp.Helpers.UserStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PasswordManager {

    private EmailActivity emailActivity;
    private ResetPasswordActivity resetPasswordActivity;
    private final UserStorage userStorage;
    private final AvocardioApi avocardioApi;
    private final Retrofit retrofit;
    private Call<PasswordResponse> passwordResponseCall;
    private String email_adrress = null;


    public PasswordManager(UserStorage userStorage, AvocardioApi avocardioApi, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.avocardioApi = avocardioApi;
        this.retrofit = retrofit;
    }

    //Zapamientuje activity do ktorego jestesmy podpieci
    public void onAttach(EmailActivity emailActivity) {
        this.emailActivity = emailActivity;
        //updateProgress();
    }

    public void onAttach_Pas(ResetPasswordActivity resetPasswordActivity) {
        this.resetPasswordActivity = resetPasswordActivity;
        //updateProgress();
    }

    //Odpinanie od activity
    public void onStop() {
        this.emailActivity = null;
    }

    public void onStopPas() {
        this.resetPasswordActivity = null;
    }

    public void sendEmail(String email, Intent intent) {
        EmailRequest request = new EmailRequest();
        request.email = email;

        if (passwordResponseCall == null) {
            passwordResponseCall = avocardioApi.getResetCode(request);
            passwordResponseCall.enqueue(new Callback<PasswordResponse>() {

                @Override
                public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                    passwordResponseCall = null;
                    if (response.isSuccessful()) {
                        response.body().toString();
                        if (emailActivity != null) {
                            emailActivity.loginSuccess(intent);
                        }
                    } else {
                        ResponseBody responseBody = response.errorBody();
                        try {
                            Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                            ErrorResponse errorResponse = converter.convert(responseBody);
                            if (emailActivity != null) {
                                //wywolanie komunikatu z bledami
                                emailActivity.showError(errorResponse.error);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onFailure(Call<PasswordResponse> call, Throwable t) {
                    passwordResponseCall = null;
                    if (emailActivity != null) {
                        //wywolanie komunikatu z problemami
                        emailActivity.showError(t.getLocalizedMessage());
                    }

                }
            });
        }

    }

    public void tryReset(String email, String reset_code) {
        PasswordRequset request = new PasswordRequset();
        request.email = email;
        request.reset_code = reset_code;
        Log.i(ResetPasswordActivity.class.getSimpleName(), "---------------------email_adresss " + email_adrress);
        Log.i(ResetPasswordActivity.class.getSimpleName(), "------------------------------------------email " + email);
        Log.i(ResetPasswordActivity.class.getSimpleName(), "-----------------------------------------------email.request " + request.email);

        if (passwordResponseCall == null) {
            passwordResponseCall = avocardioApi.postReset(request);
            passwordResponseCall.enqueue(new Callback<PasswordResponse>() {

                @Override
                public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                    passwordResponseCall = null;
                    if (response.isSuccessful()) {
                        response.body().toString();
                        if (resetPasswordActivity != null) {
                            resetPasswordActivity.loginSuccess();
                        } else {
                            ResponseBody responseBody = response.errorBody();
                            try {
                                Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                                ErrorResponse errorResponse = converter.convert(responseBody);
                                if (resetPasswordActivity != null) {
                                    //wywolanie komunikatu z bledami
                                    resetPasswordActivity.showError(errorResponse.error);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        {
                            //obsluga bledow
                            switch (response.code()) {
                                case 1004:
                                    Toast.makeText(resetPasswordActivity, ":( Sorry you must wait 15 minutes.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 1003:
                                    Toast.makeText(resetPasswordActivity, "The reset code has expired ", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PasswordResponse> call, Throwable t) {
                    passwordResponseCall = null;
                    if (resetPasswordActivity != null) {
                        //wywolanie komunikatu z problemami
                        resetPasswordActivity.showError(t.getLocalizedMessage());
                    }

                }
            });
        }

    }


}
