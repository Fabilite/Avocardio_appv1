package avocardio.avocardioapp.Connections;

public class LoginResponse {

    private String guacamole;

    public String getGuacamole() {
        return guacamole;
    }

    public void setGuacamole(String guacamole) {
        this.guacamole = guacamole;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "guacamole='" + guacamole + '\'' +
                '}';
    }
}
