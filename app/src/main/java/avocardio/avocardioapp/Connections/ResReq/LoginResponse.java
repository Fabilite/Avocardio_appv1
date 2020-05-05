package avocardio.avocardioapp.Connections.ResReq;

public class LoginResponse {


    public String user_hash;
    public String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public String getUser_hash() {
        return user_hash;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "user_hash='" + user_hash + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
