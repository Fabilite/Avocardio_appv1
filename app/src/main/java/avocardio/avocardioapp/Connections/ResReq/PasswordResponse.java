package avocardio.avocardioapp.Connections.ResReq;

public class PasswordResponse {

    public String email;
    public String password;
    public String succes;

    @Override
    public String toString() {
        return "PasswordResponse{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", succes='" + succes + '\'' +
                '}';
    }
}
