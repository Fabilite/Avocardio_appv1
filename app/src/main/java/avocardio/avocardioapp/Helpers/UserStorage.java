package avocardio.avocardioapp.Helpers;

import android.content.SharedPreferences;

import avocardio.avocardioapp.Connections.ResReq.LoginResponse;
import avocardio.avocardioapp.Connections.ResReq.RegisterResponse;

//Klasa odpowiada za przechowywanie danych (logowanie)
public class UserStorage {

    public static String USER_HASH ;
    public static String ACCESS_TOKEN;
    private final SharedPreferences sharedPreferences;


    public UserStorage(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public void saveLoginResponse(LoginResponse loginResponse){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_HASH, loginResponse.user_hash);
        editor.putString(ACCESS_TOKEN,loginResponse.access_token);
        editor.commit();
    }

    public void saveRegisterResponse(RegisterResponse registerResponse){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_HASH, registerResponse.user_hash);
        editor.commit();
    }

    public void clearAll(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public String getAccesToken() { return sharedPreferences.getString(ACCESS_TOKEN, "");}

    public String getUserHash() { return sharedPreferences.getString(USER_HASH,"");}

    //zapamietywanie danych logowania
//    public void login(UserResponse userResponse){
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(GUACAMOLE, userResponse.getGuacamole());
//        editor.apply();
//    }
//
//    public boolean isHasToLogin() {
//        sharedPreferences.getString(GUACAMOLE,"");
//        return sharedPreferences.getString(GUACAMOLE, "").isEmpty();
//    }

}
