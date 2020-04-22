package avocardio.avocardioapp.Connections.ResReq;

public class RegisterResponse {

    private String response;
    private String user_hash;

    public String getResponse() {
        return response;
    }

    public String getUser_hash() {
        return user_hash;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "response='" + response + '\'' +
                ", user_hash='" + user_hash + '\'' +
                '}';
    }
}
