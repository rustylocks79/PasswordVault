import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tester {
    //This class is for testing the new backend. Please reference for how things work.
    //TODO: remove for final version

    public static void printAccount(Account account) {
        System.out.println(account.getId());
        System.out.println(account.getUsername());
        System.out.println(account.getPassword());
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String msg = "password";
        md.update(msg.getBytes());

        User user = new User("save.txt", md.digest());
        Account inAccount0 = new Account("id", "username".toCharArray(), "password".toCharArray());
        Account inAccount1 = new Account("root", "admin".toCharArray(), "rosebud".toCharArray());
        Account inAccount2 = new Account("ben", "bdwarner99".toCharArray(), "Ipunchkittens6".toCharArray());

        user.addAccount(inAccount0);
        user.addAccount(inAccount1);
        user.addAccount(inAccount2);

        inAccount0.clean();
        inAccount1.clean();
        inAccount2.clean();

        MessageDigest md1 = MessageDigest.getInstance("SHA-256");
        String msg1 = "YouWillNeverGuessMyPasswords.txt";
        md1.update(msg1.getBytes());
        user.resetMastPassword(md1.digest());

        Account account0 = user.getAccount("id");
        printAccount(account0);
        System.out.println();
        Account account1 = user.getAccount("root");
        printAccount(account1);
        System.out.println();
        Account account2 = user.getAccount("ben");
        printAccount(account2);
        System.out.println();

        account0.clean();
        account1.clean();
        account2.clean();

        user.saveToFile("save.txt");
    }
}
