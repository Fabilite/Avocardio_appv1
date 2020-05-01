package avocardio.avocardioapp.Connections.ResReq;

public class ErrorResponse {

    public int error_code;
    public String error_message;

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "error_code=" + error_code +
                ", error_message='" + error_message + '\'' +
                '}';
    }
}
