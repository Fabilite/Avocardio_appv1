package avocardio.avocardioapp.Connections;

public class UserResponse {

    private String guacamole = "";

    public String getGuacamole() {

        return guacamole;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "guacamole='" + guacamole + '\'' +
                '}';
    }
}
