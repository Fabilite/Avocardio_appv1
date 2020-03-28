package avocardio.avocardioapp.Connections;

import android.util.Log;

import avocardio.avocardioapp.Connections.Api.AuthorizationPojo;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {
    private static String TAG = "-------Connection - class-------";
    AuthorizationPojo authorization = new AuthorizationPojo();

    private final  String url = "https://api.avocardio.app/";

    public void firstConnection() {
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
                    //Pobieranie danych
                    LoginResponse body = response.body();
                    authorization.setGuacamole(body.getGuacamole());
                    Log.d(TAG, "-------onResponse(Callback) - metoda-------");

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    public void login() {
        Log.d(TAG, "-------Login - metoda-------");

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

        Call<LoginResponse> call = connectionApi.getAuthorization(authorization.getImei(), authorization.getGuacamole(), authorization.getAuthorization());
        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "-------onResponse(Callback) - metoda-------");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

}
