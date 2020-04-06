package avocardio.avocardioapp;

import android.content.SharedPreferences;

import avocardio.avocardioapp.Connections.UserResponse;

//Klasa odpowiada za przechowywanie danych (logowanie)
public class UserStorage {

    public static String GUACAMOLE;
    private final SharedPreferences sharedPreferences;
    public boolean hasToLogin;

    public UserStorage(SharedPreferences sharedPreferences){

        this.sharedPreferences = sharedPreferences;
    }

    //zapamietywanie danych logowania
    public void login(UserResponse userResponse){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GUACAMOLE, userResponse.getGuacamole());
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

}
