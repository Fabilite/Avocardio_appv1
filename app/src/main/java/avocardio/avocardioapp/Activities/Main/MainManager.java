package avocardio.avocardioapp.Activities.Main;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import avocardio.avocardioapp.Connections.Api.AvocardioApi;
import avocardio.avocardioapp.Connections.ResReq.ErrorResponse;
import avocardio.avocardioapp.Connections.ResReq.LoginResponse;
import avocardio.avocardioapp.Helpers.UserStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainManager {

    private MainActivity mainActivity;
    private final UserStorage userStorage;
    private final AvocardioApi avocardioApi;
    private final Retrofit retrofit;
    private Call<LoginResponse> responseCall;

    public MainManager(UserStorage userStorage, AvocardioApi avocardioApi, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.avocardioApi = avocardioApi;
        this.retrofit = retrofit;
    }

    //Zapamientuje activity do ktorego jestesmy podpieci
    public void onAttach(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        //updateProgress();
    }
    public void onStop() {
        this.mainActivity = null;
    }



    public void getUserInformation() {
        String user_hash = userStorage.getUserHash();
        Log.i("User_hash----", " -------------------------------------" + userStorage.getUserHash());
        Log.i("Acces Token----", " -------------------------------------" + userStorage.getAccesToken());
        if (responseCall == null) {
            Log.i("User_hash----", " -------------------------------------" + user_hash);
            responseCall = avocardioApi.gettUserData(userStorage.getUserHash());
            responseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    responseCall = null;
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        if (mainActivity != null) {
                            ArrayList<UserData> user_data  = new ArrayList<>(response.body().getUser_data());
                            mainActivity.loginSuccess(response.body().toString());
                            Log.i(mainActivity.getLocalClassName(), "POPRAWNE WYWOLANIE DANYCH" + response.body().getAccess_token());

                        }
                    } else {
                        ResponseBody responseBody = response.errorBody();
                        try {
                            Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                            ErrorResponse errorResponse = converter.convert(responseBody);
                            if (mainActivity != null) {
                                //wywolanie komunikatu z bledami
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    responseCall = null;
                    if (mainActivity != null) {
                        //wywolanie komunikatu z problemami
                        mainActivity.showError(t.getLocalizedMessage());
                    }
                }
            });
        }

    }

}
