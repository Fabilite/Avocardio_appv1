package avocardio.avocardioapp.Connections.ResReq;

public class RegisterResponse {

    public String user_hash;

    public String getUser_Hash() {
        return user_hash;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "user_hash='" + user_hash + '\'' +
                '}';
    }
}

