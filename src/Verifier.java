import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

public class Verifier {
    public static final int SALT_LENGTH = 20;

    private String salt;
    private String hash;

    public Verifier(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(file));
        String line = scanner.nextLine();
        salt = line.substring(0, SALT_LENGTH);
        hash = line.substring(SALT_LENGTH);
    }

    public static String getSaltedMasterPasswordHash(String salt, char[] masterPassword) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        char[] toEncrypt = new char[SALT_LENGTH + 256];
        System.arraycopy(salt.toCharArray(), 0, toEncrypt, 0, salt.length());
        System.arraycopy(masterPassword, 0, toEncrypt, salt.length(), masterPassword.length);
        md.update(CharHelper.charsToBytes(toEncrypt));
        String checkHash = Base64.getEncoder().encodeToString(md.digest());
        Arrays.fill(toEncrypt, (char) 0);
        return checkHash;
    }

    public boolean verify(char[] masterPassword) throws NoSuchAlgorithmException {
        String checkHash = getSaltedMasterPasswordHash(salt, masterPassword);
        return checkHash.equals(hash);
    }


}
