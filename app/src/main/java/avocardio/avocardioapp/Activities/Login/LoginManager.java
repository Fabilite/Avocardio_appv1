package avocardio.avocardioapp.Activities.Login;

import java.io.IOException;
import java.lang.annotation.Annotation;

import avocardio.avocardioapp.Connections.Api.AvocardioApi;
import avocardio.avocardioapp.Connections.ResReq.ErrorResponse;
import avocardio.avocardioapp.Connections.ResReq.LoginRequest;
import avocardio.avocardioapp.Connections.ResReq.LoginResponse;
import avocardio.avocardioapp.Helpers.UserStorage;
import avocardio.avocardioapp.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginManager {

    private LoginActivity loginActivity;
    private final UserStorage userStorage;
    private final AvocardioApi avocardioApi;
    private final Retrofit retrofit;
    private Call<LoginResponse> loginResponseCall;

    public LoginManager(UserStorage userStorage, AvocardioApi avocardioApi, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.avocardioApi = avocardioApi;
        this.retrofit = retrofit;
    }

    //Zapamientuje activity do ktorego jestesmy podpieci
    public void onAttach(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        //updateProgress();
    }


    //Odpinanie od activity
    public void onStop() {
        this.loginActivity = null;
    }

    public void clearSession() {
        userStorage.clearAll();
    }

    public void login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = email;
        loginRequest.password = password;
        loginRequest.os = "A";
        //zabezpieczenie przed podwojnym wywolaniem akcji logowania
        if (loginResponseCall == null) {
            loginResponseCall = avocardioApi.getLogin(loginRequest);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    loginResponseCall = null;
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        if (loginActivity != null) {
                            loginActivity.loginSuccess();
                        }
                    } else {
                        ResponseBody responseBody = response.errorBody();
                        try {
                            Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                            ErrorResponse errorResponse = converter.convert(responseBody);
                            if (loginActivity != null) {
                                //wywolanie komunikatu z bledami
                                switch (errorResponse.error_code) {
                                    case 1002:
                                        loginActivity.popUpError("This e-mail or password is not valid.", R.color.error_info);
                                        break;
                                    default:
                                        loginActivity.popUpError("Something went wrong try again later", R.color.error_info);
                                        break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    loginResponseCall = null;
                    if (loginActivity != null) {
                        //wywolanie komunikatu z problemami
                        //loginActivity.showError(t.getLocalizedMessage());
                    }

                }
            });
        }
    }

    private void updateProgress() {
        if (loginActivity != null) {
            loginActivity.showProgress(loginResponseCall != null);
        }
    }


}
