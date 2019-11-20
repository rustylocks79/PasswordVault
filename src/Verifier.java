import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Verifier {
    private String salt;
    private String hash;

    public Verifier(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(file));
        String line = scanner.nextLine();
        salt = line.substring(0, User.SALT_LENGTH);
        hash = line.substring(User.SALT_LENGTH);
    }

    public boolean verify(char[] masterPassword) {
        String checkHash = User.getSaltedMasterPasswordHash(salt, masterPassword);
        return checkHash.equals(hash);
    }
}
