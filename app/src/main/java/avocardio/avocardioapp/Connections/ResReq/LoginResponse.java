package avocardio.avocardioapp.Connections.ResReq;

import java.util.List;

import avocardio.avocardioapp.Activities.Main.UserData;

public class LoginResponse {


    public String user_hash;
    public String access_token;

    private List<UserData> user_data = null;

    public String getAccess_token() {
        return access_token;
    }

    public String getUser_hash() {
        return user_hash;
    }

    public List<UserData> getUser_data() {
        return user_data;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "user_hash='" + user_hash + '\'' +
                ", access_token='" + access_token + '\'' +
                ", user_data=" + user_data +
                '}';
    }
}
