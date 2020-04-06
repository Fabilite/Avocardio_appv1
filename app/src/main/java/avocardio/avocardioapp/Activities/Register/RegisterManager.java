package avocardio.avocardioapp.Activities.Register;

import avocardio.avocardioapp.Connections.Api.AvocardioApi;
import avocardio.avocardioapp.Connections.UserResponse;
import avocardio.avocardioapp.UserStorage;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RegisterManager {

    private RegisterActivity registerActivity;
    private final UserStorage userStorage;
    private final AvocardioApi avocardioApi;
    private final Retrofit retrofit;
    private Call<UserResponse> userResponseCall;

    public RegisterManager(UserStorage userStorage, AvocardioApi avocardioApi, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.avocardioApi = avocardioApi;
        this.retrofit = retrofit;
    }

    public void onAttach(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
        //upDateProgress();

    }

    public void onStop() {
        this.registerActivity = null;
    }


//    public void register(String email, String password, String firstname, String country, String language, String sex, String birthday) {
//        RegisterRequest registerRequest = new RegisterRequest();
//
//        registerRequest.email = email;
//        registerRequest.firstname = firstname;
//        registerRequest.country = country;
//        registerRequest.password = password;
//        registerRequest.language = language;
//        registerRequest.sex = sex;
//        registerRequest.birthday = birthday;
//
//        if (userResponseCall == null) {
//            userResponseCall = avocardioApi.postRegister(registerRequest);
//            upDateProgress();
//            userResponseCall.enqueue(new Callback<UserResponse>() {
//                @Override
//                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//
//                    userResponseCall = null;
//                    upDateProgress();
//
//                    if (response.isSuccessful()) {
//                        if (registerActivity != null) {
//                            registerActivity.registerSucessful();
//                        } else {
//                            Converter<ResponseBody, ErrorResponse> responseBodyObjectConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
//                            try {
//                                ErrorResponse errorResponse = responseBodyObjectConverter.convert(response.errorBody());
//                                if (registerActivity != null) {
//                                    registerActivity.showError(errorResponse.error);
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UserResponse> call, Throwable t) {
//                    userResponseCall = null;
//                    upDateProgress();
//
//                    if (registerActivity != null) {
//                        registerActivity.showError(t.getLocalizedMessage());
//                    }
//                }
//            });
//        }
//
//    }

//    private void upDateProgress() {
//        if (registerActivity == null) {
//            registerActivity.showProgress(userResponseCall != null);
//        }
//    }
}
