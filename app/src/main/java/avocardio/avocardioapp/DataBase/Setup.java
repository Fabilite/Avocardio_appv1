package avocardio.avocardioapp.DataBase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "setup")
public class Setup {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "language")
    private String language;

    @Property(nameInDb = "onBoardingActive")
    private int onBoardingActive;

    @Generated(hash = 1109204889)
    public Setup(Long id, String language, int onBoardingActive) {
        this.id = id;
        this.language = language;
        this.onBoardingActive = onBoardingActive;
    }

    @Generated(hash = 1774925356)
    public Setup() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getOnBoardingActive() {
        return this.onBoardingActive;
    }

    public void setOnBoardingActive(int onBoardingActive) {
        this.onBoardingActive = onBoardingActive;
    }

}
