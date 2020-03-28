package avocardio.avocardioapp.Activities;

import android.app.Application;

import avocardio.avocardioapp.Connections.UserManager;

public class App extends Application {

    private UserManager userManager;


    @Override
    public void onCreate() {
        super.onCreate();
        userManager = new UserManager();
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
