package avocardio.avocardioapp.Activities.Login;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.lang.annotation.Annotation;

import avocardio.avocardioapp.Connections.Api.AvocardioApi;
import avocardio.avocardioapp.Connections.ErrorResponse;
import avocardio.avocardioapp.Connections.UserResponse;
import avocardio.avocardioapp.UserStorage;
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
    private Call<UserResponse> call;

    private static String TAG = "UserManager";

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void askForGuacamole() {
        Log.d(TAG, "Metoda askForGuacamole: ");
        Log.i(TAG, "GUACAMOLE!----------------------------------------" + userStorage.getGuacamoleInfo());

        //zabezpieczenie przed podwojnym wywolaniem akcji logowania
//        if (call == null) {
//            if (userStorage.getGuacamoleInfo().isEmpty()) {
                call = avocardioApi.getFirstAuthorization("352937084822763", "");
                call.enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        //call = null;
                        if (response.isSuccessful()) {
                            UserResponse body = response.body();
                            // pobiera dane do SharedPreferences
                            userStorage.login(body);
                            Log.i(TAG, "BODY!----------------------------------------" + body);
                            if (loginActivity != null) {
                            }
                        } else {
                            ResponseBody responseBody = response.errorBody();
                            try {
                                Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                                ErrorResponse errorResponse = converter.convert(responseBody);
                                if (loginActivity != null) {
                                    //wywolanie komunikatu z bledami
                                    loginActivity.showError(errorResponse.error);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        call = null;
                        if (loginActivity != null) {
                            //wywolanie komunikatu z problemami
                            loginActivity.showError(t.getLocalizedMessage());
                        }

                    }
                });
            }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void login() {
        //zabezpieczenie przed podwojnym wywolaniem akcji logowania
        if (call == null) {
                call = avocardioApi.getAuthorization("352937084822763", userStorage.getGuacamoleInfo(), "");
                call.enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        call = null;
                        if (response.isSuccessful()) {
                            //TODO
                            response.body();
                            Log.i(TAG, "RESPONSE BODY-----" + response.body());
                            if (loginActivity != null) {
                                //TODO
                                //Start activity
                                //loginActivity.loginSuccess();
                            }
                        } else {
                            ResponseBody responseBody = response.errorBody();
                            try {
                                Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                                ErrorResponse errorResponse = converter.convert(responseBody);
                                if (loginActivity != null) {
                                    //wywolanie komunikatu z bledami
                                    loginActivity.showError(errorResponse.error);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        call = null;
                        if (loginActivity != null) {
                            //wywolanie komunikatu z problemami
                            loginActivity.showError(t.getLocalizedMessage());
                        }

                    }
                });
            }
    }

    private void updateProgress() {
        if (loginActivity != null) {
            loginActivity.showProgress(call != null);
        }
    }


}
