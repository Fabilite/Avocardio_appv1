package avocardio.avocardioapp.Connections.Api;

import avocardio.avocardioapp.Connections.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AvocardioApi {


    @Headers({"Content-Type: application/json"})
    @GET("/")
    Call<UserResponse> getAuthorization(
            @Header("x-imei") String imei,
            @Header("x-guacamole") String guacamole,
            @Header("authorization") String authorization);


    @Headers({"Content-Type: application/json"})
    @GET("/")
    Call<UserResponse> getFirstAuthorization(
            @Header("x-imei") String input,
            @Header("x-guacamole") String inputfood);


    @Headers({"Content-Type: application/json",
    "x-imei: 352937084822763",
    "x-guacamole: 158568977849378",
    "Authorization: e7d55ec3acdfcbeacdf2d1ab9654cbde"})
    @POST("user/new")
    Call<UserResponse> postRegister(@Body RegisterRequest request);

}