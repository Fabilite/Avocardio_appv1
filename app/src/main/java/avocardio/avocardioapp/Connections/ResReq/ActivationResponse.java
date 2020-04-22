package avocardio.avocardioapp.Connections.ResReq;

public class ActivationResponse {


    public String user_hash;
    public String activation_code;
    public String accept;

    @Override
    public String toString() {
        return "ActivationResponse{" +
                "user_hash='" + user_hash + '\'' +
                ", activation_code='" + activation_code + '\'' +
                '}';
    }
}
