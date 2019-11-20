import java.io.File;
import java.io.IOException;
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
        {
            Verifier verifier = new Verifier(new File("save.txt"));
            //boolean res = verifier.isValidCertificate("C:\\CompSci\\School\\CMPSC_444\\PasswordVault\\receiverCertificate.crt");
            //System.out.println(res);
        }


        File file = new File("save.txt");
        User user = null;
        if(file.exists()) {
            Verifier verifier = new Verifier(file);
            if(verifier.verify("password".toCharArray())) {
                user = new User(file, "password".toCharArray());
            } else {
                System.out.println("Wrong");
                System.exit(-1);
            }
        } else {
            user = new User("password".toCharArray());
            Account inAccount0 = new Account("id", "username".toCharArray(), "password".toCharArray());
            Account inAccount1 = new Account("root", "admin".toCharArray(), "rosebud".toCharArray());
            Account inAccount2 = new Account("ben", "bdwarner99".toCharArray(), "Ipunchkittens6".toCharArray());

            user.addAccount(inAccount0);
            user.addAccount(inAccount1);
            user.addAccount(inAccount2);

            inAccount0.clean();
            inAccount1.clean();
            inAccount2.clean();
        }

//        MessageDigest md1 = MessageDigest.getInstance("SHA-256");
//        String msg1 = "YouWillNeverGuessMyPasswords.txt";
//        md1.update(msg1.getBytes());
//        user.resetMastPassword(md1.digest());

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

        user.saveToFile(new File("save.txt"));
        user.clean();
    }
}
