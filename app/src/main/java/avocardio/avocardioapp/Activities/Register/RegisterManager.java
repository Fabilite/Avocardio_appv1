package avocardio.avocardioapp.Activities.Register;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;

import avocardio.avocardioapp.Connections.Api.AvocardioApi;
import avocardio.avocardioapp.Connections.ResReq.ErrorResponse;
import avocardio.avocardioapp.Connections.ResReq.RegisterRequest;
import avocardio.avocardioapp.Connections.ResReq.RegisterResponse;
import avocardio.avocardioapp.Helpers.UserStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterManager {

    private RegisterActivity_1 registerActivity_1;
    private RegisterActivity_2 registerActivity_2;
    private final UserStorage userStorage;
    private final AvocardioApi avocardioApi;
    private final Retrofit retrofit;
    private Call<RegisterResponse> registerResponseCall;

    public RegisterManager(UserStorage userStorage, AvocardioApi avocardioApi, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.avocardioApi = avocardioApi;
        this.retrofit = retrofit;
    }

    public void onAttach_1(RegisterActivity_1 registerActivity) {
        this.registerActivity_1 = registerActivity;
        //upDateProgress();
    }

    public void onAttach_2(RegisterActivity_2 registerActivity) {
        this.registerActivity_2 = registerActivity;
        //upDateProgress();
    }

    public void onStop_1() {
        this.registerActivity_1 = null;
    }

    public void onStop_2() {
        this.registerActivity_2 = null;
    }

    public void clearUserStorage(){
        userStorage.clearAll();
    }


    public void register(String email, String password, String firstname, String brithday, String sex, String newsletter, String countryCode) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = email;
        registerRequest.firstname = firstname;
        registerRequest.country = countryCode;
        registerRequest.password = password;
        registerRequest.language = "PL";
        registerRequest.sex = sex;
        registerRequest.birthday = brithday;
        registerRequest.lastname = "";
        registerRequest.phone = "";
        registerRequest.activation_mail = "1";
        //PAMIETAJ ZEBY ZMIENIC NA 0
        registerRequest.active = "0";
        registerRequest.newsletter = newsletter;

        //userStorage.deleteAccesToken();

        if (registerResponseCall == null) {
            registerResponseCall = avocardioApi.postRegister(registerRequest);
            registerResponseCall.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response){
                    registerResponseCall = null;
                    //userStorage.deleteAccesToken();
                    if (response.isSuccessful()){
                        RegisterResponse registerResponse = response.body();
                        userStorage.saveRegisterResponse(registerResponse);
                        Log.i("User_hash","-------------------------------------------------------------------");
                        Log.i("User_hash/user storage","-------------------------------------"+ userStorage.getUserHash());
                        Log.i("User_hash/user storage","-------------------------------------"+ userStorage.getAccesToken());
                        Log.i("User_hash/user storage","-------------------------------------"+ userStorage.USER_HASH);
                        Log.i("User_hash/user storage","-------------------------------------"+ userStorage.ACCESS_TOKEN);
                        if (registerActivity_2 != null) {
                            registerActivity_2.registerSucessful();
                        }
                    } else {
                        ResponseBody responseBody = response.errorBody();
                        try {
                            Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                            ErrorResponse errorResponse = converter.convert(responseBody);
                            if (registerActivity_2 != null) {
                                //obsluga bledow
                                switch (errorResponse.error_code) {
//                                    case 403:
//                                        registerActivity_2.popUpError("This e-mail is already taken");
//                                        break;
//                                    case 404:
//                                        registerActivity_2.popUpError(":( Server broken :(");
//                                        break;
//                                    case 500:
//                                        registerActivity_2.popUpError(":( Server broken :(");
//                                        break;
                                    default:
                                        registerActivity_2.popUpError("Something went wrong try again later");
                                        break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    registerResponseCall = null;
                    //upDateProgress();
                    if (registerActivity_2 != null) {
                        registerActivity_2.popUpError(t.getLocalizedMessage());
                    }
                }
            });
        }

    }


}
