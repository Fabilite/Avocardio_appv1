package avocardio.avocardioapp.Connections.ResReq;

import java.util.Arrays;

public class LoginResponse {

    public String email;
    public String password;
    public String[] user_data;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", user_data=" + Arrays.toString(user_data) +
                '}';
    }
}
