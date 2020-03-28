package avocardio.avocardioapp.Connections;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ConnectionApi {


    @Headers({"Content-Type: application/json"})
    @GET("/")
    Call<LoginResponse> getAuthorization(
            @Header("x-imei") String imei,
            @Header("x-guacamole") String guacamole,
            @Header("authorization") String authorization);


    @Headers({"Content-Type: application/json"})
    @GET("/")
    Call<LoginResponse> getFirstAuthorization(
            @Header("x-imei") String input,
            @Header("x-guacamole") String inputfood);
}