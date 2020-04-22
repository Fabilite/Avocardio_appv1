package avocardio.avocardioapp.Activities.Register;

import android.widget.Toast;

import java.io.IOException;
import java.lang.annotation.Annotation;

import avocardio.avocardioapp.Connections.Api.AvocardioApi;
import avocardio.avocardioapp.Connections.ResReq.RegisterRequest;
import avocardio.avocardioapp.Connections.ResReq.RegisterResponse;
import avocardio.avocardioapp.Connections.ResReq.ErrorResponse;
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


    public void register(String email, String password,String firstname, String brithday,String sex,String newsletter) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = email;
        registerRequest.firstname = firstname;
        registerRequest.country = "PL";
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

        if (registerResponseCall == null) {
            registerResponseCall = avocardioApi.postRegister(registerRequest);
            registerResponseCall.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    registerResponseCall = null;
                    if (response.isSuccessful()) {
                        RegisterResponse registerResponse = response.body();
                        userStorage.save(registerResponse);
                        if (registerActivity_2 != null) {
                            registerActivity_2.registerSucessful();

                        } else {
                            ResponseBody responseBody = response.errorBody();

                            try {
                                Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                                ErrorResponse errorResponse = converter.convert(responseBody);
                                Toast.makeText(registerActivity_2, errorResponse.error + "TESTTTTT" , Toast.LENGTH_SHORT).show();
                                if (registerActivity_2 != null) {
                                    Toast.makeText(registerActivity_2, errorResponse.error , Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        {
                            //obsluga bledow
                            switch (response.code()) {
                                case 403:
                                    Toast.makeText(registerActivity_2, "This e-mail is already taken", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(registerActivity_2, ":( Server broken :(", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(registerActivity_2, " :( Server broken :(", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(registerActivity_2, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    registerResponseCall = null;
                    //upDateProgress();

                    if (registerActivity_2 != null) {
                        registerActivity_2.showError(t.getLocalizedMessage());
                    }
                }
            });
        }

    }

//    private void upDateProgress() {
//        if (registerActivity_2 == null) {
//            registerActivity_2.showProgress(userResponseCall != null);
//        }
//    }
}
