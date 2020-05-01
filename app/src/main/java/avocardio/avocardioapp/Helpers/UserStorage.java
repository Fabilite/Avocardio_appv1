package avocardio.avocardioapp.Helpers;

import android.content.SharedPreferences;

import avocardio.avocardioapp.Connections.ResReq.RegisterResponse;

//Klasa odpowiada za przechowywanie danych (logowanie)
public class UserStorage {

    public static String USER_HASH;
    private final SharedPreferences sharedPreferences;

    public UserStorage(SharedPreferences sharedPreferences){

        this.sharedPreferences = sharedPreferences;
    }

    public void save(RegisterResponse registerResponse){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_HASH, registerResponse.getUser_hash());
        editor.apply();
    }

    public void logOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getUserHash(){return sharedPreferences.getString(USER_HASH,"");}

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
