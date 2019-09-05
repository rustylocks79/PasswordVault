import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String password;
    private ArrayList<Account> accounts = new ArrayList<>();

    public User(String password) {
        setPassword(password);
    }

    public User(File file) {

    }

    public void saveToFile(File file) {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Objects.requireNonNull(password);
        this.password = password;
    }
}
