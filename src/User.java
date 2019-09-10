import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class User {
    private String masterPassword;
    private ArrayList<Account> accounts = new ArrayList<>();

    public User(String masterPassword) {
        setMasterPassword(masterPassword);
    }

    public User(File file) {
        try {
            Scanner scanner = new Scanner(file);
            masterPassword = scanner.nextLine();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(masterPassword);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        Objects.requireNonNull(masterPassword);
        this.masterPassword = masterPassword;
    }
}
