package avocardio.avocardioapp.Connections;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;

import avocardio.avocardioapp.Activities.Login.LoginActivity;
import avocardio.avocardioapp.Connections.Api.AuthorizationPojo;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserManager {

    AuthorizationPojo authorization = new AuthorizationPojo();
    private LoginActivity loginActivity;
    private static String TAG = "-------Connection - class-------";
    private final String url = "https://api.avocardio.app/";

    public void onAttach(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void onStop() {
        this.loginActivity = null;
    }

    public void login() {
        Log.d(TAG, "-------FirstConnection - metoda-------");
        //pozwala podgladac debuger
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl(url);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(client);
        Retrofit retrofit = builder.build();

        ConnectionApi connectionApi = retrofit.create(ConnectionApi.class);
        Call<LoginResponse> call = connectionApi.getFirstAuthorization(authorization.getImei(), authorization.getGuacamole());
        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse body = response.body();
                    Log.d(TAG, "Response: " + body.toString());
                    authorization.setGuacamole(body.getGuacamole());

                    if (loginActivity != null) {
                        loginActivity.loginSuccess();
                    }
                } else {
                    ResponseBody responseBody = response.errorBody();
                    try {
                        Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                        ErrorResponse errorResponse = converter.convert(responseBody);
                        if (loginActivity != null) {
                            loginActivity.showError(errorResponse.error);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (loginActivity != null) {
                    loginActivity.showError(t.getLocalizedMessage());
                }
            }
        });
    }
}
