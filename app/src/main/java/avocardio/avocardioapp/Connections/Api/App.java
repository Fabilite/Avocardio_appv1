package avocardio.avocardioapp.Connections.Api;

import android.app.Application;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import avocardio.avocardioapp.Activities.ActivationAcount.ActivationManager;
import avocardio.avocardioapp.Activities.Login.LoginManager;
import avocardio.avocardioapp.Activities.Main.MainManager;
import avocardio.avocardioapp.Activities.Password.PasswordManager;
import avocardio.avocardioapp.Activities.Register.RegisterManager;
import avocardio.avocardioapp.Helpers.Generates;
import avocardio.avocardioapp.Helpers.UserStorage;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Klasa pomaga zapobiegać usuwaniu instancij Activity
//Tworzy jedną instancję na cały czas pracy aplikacji
public class App extends Application {

    private MainManager mainManager;
    private LoginManager loginManager;
    private RegisterManager registerManager;
    private ActivationManager activationManager;
    private PasswordManager passwordManager;
    private UserStorage userStorage;
    private Retrofit retrofit;
    private AvocardioApi avocardioApi;
    private Generates generates = new Generates();
    final String urlTest = "https://avocardio.hopto.org/avocardio/";
    //final String url = "";

    @Override
    public void onCreate() {
        super.onCreate();
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest = request.newBuilder()
                                .addHeader("x-api-key", generates.getAvocS())
                                .addHeader("x-api-secret", generates.getGuacS())
                                .addHeader("x-access-token",userStorage.getAccesToken())
                                .addHeader("authorization", generates.getSHA512(generates.getAvocS() + generates.getGuacS())).build();
                        return chain.proceed(newRequest);
                    }
                }).addNetworkInterceptor(loggingInterceptor).build();

//        client.readTimeoutMillis();
//        client.connectTimeoutMillis();
//        client.writeTimeoutMillis();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(urlTest);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(client);
        retrofit = builder.build();

        avocardioApi = retrofit.create(AvocardioApi.class);
        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        loginManager = new LoginManager(userStorage, avocardioApi, retrofit);
        registerManager = new RegisterManager(userStorage, avocardioApi, retrofit);
        activationManager = new ActivationManager(userStorage, avocardioApi, retrofit);
        passwordManager = new PasswordManager(userStorage, avocardioApi, retrofit);
        mainManager = new MainManager(userStorage, avocardioApi, retrofit);
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

    public ActivationManager getActivationManager() {
        return activationManager;
    }

    public PasswordManager getPasswordManager() {
        return passwordManager;
    }

    public MainManager getMainManager() {
        return mainManager;
    }
}
