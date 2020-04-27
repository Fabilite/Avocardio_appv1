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
import retrofit2.http.Query;

public interface AvocardioApi {

    @Headers("Content-Type: application/json")
    @POST("user/new")
    Call<RegisterResponse> postRegister(@Body RegisterRequest request);

    @Headers("Content-Type: application/json")
    @POST("user/authorization")
    Call<LoginResponse> getLogin(@Body LoginRequest request);

    @Headers("Content-Type: application/json")
    @POST("user/activation")
    Call<ActivationResponse> getActivations(@Body ActivationRequest request);

    @Headers("Content-Type: application/json")
    @POST("user/passwordReset")
    Call<PasswordResponse> getResetCode(@Body EmailRequest request);

    @Headers("Content-Type: application/json")
    @POST("user/passwordReset")
    Call<PasswordResponse> postReset(@Body PasswordRequset requset);

    @Headers("Content-Type: application/json")
    @GET("user/userData")
    Call<LoginResponse> getUserData(@Query("user_hash") String requset);


}