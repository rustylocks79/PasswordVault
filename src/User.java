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
            int size = scanner.nextInt();
            scanner.nextLine();
            for(int i = 0; i < size; i++) {
                accounts.add(new Account(scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(masterPassword + "\n");
            writer.write(accounts.size() + "\n");
            for(Account account : accounts) {
                writer.write(account.toString() + "\n");
            }
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

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}