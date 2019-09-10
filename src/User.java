import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String masterPassword;
    private ArrayList<Account> accounts = new ArrayList<>();

    public User(String masterPassword) {
        setMasterPassword(masterPassword);
    }

    public User(File file) {

    }

    public void saveToFile(File file) {

    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        Objects.requireNonNull(masterPassword);
        this.masterPassword = masterPassword;
    }
}
