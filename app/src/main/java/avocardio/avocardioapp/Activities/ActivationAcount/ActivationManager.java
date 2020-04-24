package avocardio.avocardioapp.Activities.ActivationAcount;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;

import avocardio.avocardioapp.Connections.ResReq.ActivationResponse;
import avocardio.avocardioapp.Connections.ResReq.ActivationRequest;
import avocardio.avocardioapp.Connections.Api.AvocardioApi;
import avocardio.avocardioapp.Connections.ResReq.ErrorResponse;
import avocardio.avocardioapp.Helpers.UserStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivationManager {

    private ActivationAccountActivity activationAccountActivity;
    private final AvocardioApi avocardioApi;
    private final Retrofit retrofit;
    private final UserStorage userStorage;
    private Call<ActivationResponse> activationResponseCall;

    public ActivationManager(UserStorage userStorage, AvocardioApi avocardioApi, Retrofit retrofit) {
        this.avocardioApi = avocardioApi;
        this.retrofit = retrofit;
        this.userStorage = userStorage;
    }

    //Zapamientuje activity do ktorego jestesmy podpieci
    public void onAttach(ActivationAccountActivity activationAccountActivity) {
        this.activationAccountActivity = activationAccountActivity;
        //updateProgress();
    }

    //Odpinanie od activity
    public void onStop() {
        this.activationAccountActivity = null;
    }

    public void tryToActive(String code) {
        ActivationRequest request = new ActivationRequest();
        request.user_hash = userStorage.getUserHash();
        request.activation_code = code;
        Log.i(ActivationAccountActivity.class.getSimpleName(), "-----------------------------------------------------------User Hash = " + userStorage.getUserHash());
        if (activationResponseCall == null) {
            activationResponseCall = avocardioApi.getActivations(request);

            activationResponseCall.enqueue(new Callback<ActivationResponse>() {
                @Override
                public void onResponse(Call<ActivationResponse> call, Response<ActivationResponse> response) {
                    activationResponseCall = null;
                    if (response.isSuccessful()) {
                        ActivationResponse body = response.body();
                        Log.i(ActivationAccountActivity.class.getSimpleName(), "-----------------------------------------------------------User Hash = " + userStorage.getUserHash());
                        Log.d(ActivationAccountActivity.class.getSimpleName(), "Resp: " + body.toString());
                        if (activationAccountActivity != null) {
                            activationAccountActivity.loginSuccess();
                        } else {
                            ResponseBody responseBody = response.errorBody();
                            try {
                                Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                                ErrorResponse errorResponse = converter.convert(responseBody);
                                Log.d(ActivationAccountActivity.class.getSimpleName(), "Resp: " + errorResponse.toString());
                                if (activationAccountActivity != null) {
                                    //wywolanie komunikatu z bledami
                                    activationAccountActivity.showError(errorResponse.error);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        {
                            //obsluga bledow
                            switch (response.code()) {
                                case 1002:
                                    activationAccountActivity.popUpError("Invalid activation code");
                                    break;
                                default:
                                    activationAccountActivity.popUpError("Something went wrong try again later");
                                    break;
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<ActivationResponse> call, Throwable t) {
                    activationResponseCall = null;
                    if (activationAccountActivity != null) {
                        //wywolanie komunikatu z problemami
                        activationAccountActivity.showError(t.getLocalizedMessage());
                    }

                }

            });
        }

    }

    private void updateProgress() {
        if (activationResponseCall != null) {
            activationAccountActivity.showProgress(activationResponseCall != null);
        }
    }


}
