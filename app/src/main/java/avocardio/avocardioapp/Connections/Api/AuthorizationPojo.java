package avocardio.avocardioapp.Connections.Api;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthorizationPojo {
    private static String guacamole = "1e2a3bdd14134d78ed07576a28e7984a";
    private static String imei;

    public String getGuacamole() {
        return guacamole;
    }

    public void setGuacamole(String guacamole) {
        this.guacamole = guacamole;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }


    //pobierania klucza
    public String getAuthorization() {

        String finale = "";

        if (imei.length() == 15) {
            finale = getImei() + getGuacamole() + (getImei().substring(getImei().length() - 3));
        } else {
            throw new IllegalArgumentException("It isn't a Imei!");
        }

        String md5 = getMd5(finale);

        return md5;
    }

    //tworzenie md5
    public static String getMd5(String input) {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
