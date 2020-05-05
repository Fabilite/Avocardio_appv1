package avocardio.avocardioapp.Connections.Api;

import avocardio.avocardioapp.Connections.ResReq.ActivationRequest;
import avocardio.avocardioapp.Connections.ResReq.ActivationResponse;
import avocardio.avocardioapp.Connections.ResReq.EmailRequest;
import avocardio.avocardioapp.Connections.ResReq.LoginRequest;
import avocardio.avocardioapp.Connections.ResReq.LoginResponse;
import avocardio.avocardioapp.Connections.ResReq.PasswordRequset;
import avocardio.avocardioapp.Connections.ResReq.PasswordResponse;
import avocardio.avocardioapp.Connections.ResReq.RegisterRequest;
import avocardio.avocardioapp.Connections.ResReq.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AvocardioApi {

    //REJESTRACJA
    @Headers("Content-Type: application/json")
    @POST("user/new")
    Call<RegisterResponse> postRegister(@Body RegisterRequest request);

    // LOGOWANIE
    @Headers("Content-Type: application/json")
    @POST("user/authorization")
    Call<LoginResponse> getLogin(@Body LoginRequest request);

    //AKTYWACJA KONTA
    @Headers("Content-Type: application/json")
    @POST("user/activation")
    Call<ActivationResponse> getActivations(@Body ActivationRequest request);

    //RESETOWANIE / WYSYLANIE E-MAIL
    @Headers("Content-Type: application/json")
    @POST("user/passwordReset")
    Call<PasswordResponse> getResetCode(@Body EmailRequest request);

    //RESETOWANIE / WYSYLANIE KODU
    @Headers("Content-Type: application/json")
    @POST("user/passwordReset")
    Call<PasswordResponse> postReset(@Body PasswordRequset requset);

    //POBIERANIE DANYCH UZYTKOWNIKA
    @Headers("Content-Type: application/json")
    @GET("user/userData")
    Call<LoginResponse> getUserData(@Query("/") String requset);

    @Headers("Content-Type: application/json")
    @GET("user/userData/{user_hash}")
    Call<LoginResponse> gettUserData(@Path("user_hash") String user);

}