package avocardio.avocardioapp.Connections.ResReq;

public class LoginResponse {

    public String email;
    public String password;

    @Override
    public String toString() {
        return "UserResponse{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
