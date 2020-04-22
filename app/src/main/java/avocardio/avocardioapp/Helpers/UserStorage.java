package avocardio.avocardioapp.Helpers;

import android.content.SharedPreferences;

import avocardio.avocardioapp.Connections.ResReq.RegisterResponse;

//Klasa odpowiada za przechowywanie danych (logowanie)
public class UserStorage {

    public static String GUACAMOLE;
    public static String USER_HASH;
    private final SharedPreferences sharedPreferences;
    public boolean hasToLogin;

    public UserStorage(SharedPreferences sharedPreferences){

        this.sharedPreferences = sharedPreferences;
    }

    //zapamietywanie danych logowania
    public void save(RegisterResponse registerResponse){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_HASH, registerResponse.getUser_hash());
        editor.apply();
    }

    public boolean isHasToLogin() {
        sharedPreferences.getString(GUACAMOLE,"");
        return sharedPreferences.getString(GUACAMOLE, "").isEmpty();
    }

    public void logOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getGuacamoleInfo(){
        return sharedPreferences.getString(GUACAMOLE, "");
    }

    public String getUserHash(){return sharedPreferences.getString(USER_HASH,"");}

}
