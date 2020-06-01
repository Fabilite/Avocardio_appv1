package avocardio.avocardioapp.Activities.Main;

public class UserData {

    public String id;
    public String hash;
    public String email;
    public String password;
    public String firstname;
    public String lastname;
    public String phone;
    public String country;
    public String language;
    public String sex;
    public String brithday;
    public String newsletter;
    public String activation_mail;
    public String active;
    public String onboarding;
    public String os;
    String data[];


    @Override
    public String toString() {
        return "UserData{" +
                "id='" + id + '\'' +
                ", hash='" + hash + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", language='" + language + '\'' +
                ", sex='" + sex + '\'' +
                ", brithday='" + brithday + '\'' +
                ", newsletter='" + newsletter + '\'' +
                ", activation_mail='" + activation_mail + '\'' +
                ", active='" + active + '\'' +
                ", onboarding='" + onboarding + '\'' +
                ", os='" + os + '\'' +
                '}';
    }
}
