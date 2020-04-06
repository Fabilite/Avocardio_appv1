package avocardio.avocardioapp.Activities;

import android.app.Application;
import android.preference.PreferenceManager;

import avocardio.avocardioapp.Activities.Login.LoginManager;
import avocardio.avocardioapp.Activities.Register.RegisterManager;
import avocardio.avocardioapp.Connections.Api.AvocardioApi;
import avocardio.avocardioapp.UserStorage;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Klasa pomaga zapobiegać usuwaniu instancij Activity
//Tworzy jedną instancję na cały czas pracy aplikacji
public class App extends Application {

    private LoginManager loginManager;
    private RegisterManager registerManager;
    private UserStorage userStorage;
    private Retrofit retrofit;
    private AvocardioApi avocardioApi;


    @Override
    public void onCreate() {
        super.onCreate();
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl("https://api.avocardio.app/");
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(client);
        retrofit = builder.build();

        avocardioApi = retrofit.create(AvocardioApi.class);


        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        loginManager = new LoginManager(userStorage, avocardioApi, retrofit);
        registerManager = new RegisterManager(userStorage, avocardioApi, retrofit);
    }

    public LoginManager getLoginManager() {

        return loginManager;
    }

    public UserStorage getUserStorage() {

        return userStorage;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public AvocardioApi getAvocardioApi() {
        return avocardioApi;
    }

    public RegisterManager getRegisterManager() {
        return registerManager;
    }
}
