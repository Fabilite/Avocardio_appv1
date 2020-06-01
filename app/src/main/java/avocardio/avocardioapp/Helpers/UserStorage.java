package avocardio.avocardioapp.Helpers;

import android.content.SharedPreferences;

import avocardio.avocardioapp.Connections.ResReq.LoginResponse;
import avocardio.avocardioapp.Connections.ResReq.RegisterResponse;

//Klasa odpowiada za przechowywanie danych (logowanie)
public class UserStorage {

    public static String USER_HASH = "user_hash";
    public static String ACCESS_TOKEN = "access_token";
    public static String ONBARDING_CLICKED = "onbarding_clicked";
    public static String LANGUAGE = "language";

    private final SharedPreferences sharedPreferences;


    public UserStorage(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public void saveLoginData(LoginResponse loginResponse){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_HASH,loginResponse.user_hash);
        editor.putString(ACCESS_TOKEN,loginResponse.access_token);
        editor.apply();
    }

    public void saveRegisterResponse(RegisterResponse registerResponse){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_HASH, registerResponse.user_hash);
        editor.apply();
    }

    public void saveOnBoardingClick(int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ONBARDING_CLICKED,value);
        editor.apply();
    }

    public void setLANGUAGE(String language){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANGUAGE,language);
        editor.apply();
    }

    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_HASH);
        editor.remove(ACCESS_TOKEN);
        editor.remove(ONBARDING_CLICKED);
        editor.apply();
    }

    public int getOnbardingClicked(){
        return sharedPreferences.getInt(ONBARDING_CLICKED, 1);
    }

    public String getLangugae(){return  sharedPreferences.getString(LANGUAGE,"PL");
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
    public boolean isHasToLogin() {
        return sharedPreferences.getString(ACCESS_TOKEN, "").isEmpty();
    }

}
