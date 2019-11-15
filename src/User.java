import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class User {
    private Map<String, String> accounts = new HashMap<>();
    private byte[] hashedMasterPassword;

    public User(String filePath, byte[] hashedMasterPassword) throws FileNotFoundException {
        this.hashedMasterPassword = hashedMasterPassword;
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int split = line.indexOf(';');
            accounts.put(line.substring(0, split), line.substring(split + 1));
        }
        scanner.close();
    }

    public void saveToFile(String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for(String id : accounts.keySet()) {
            writer.write(id);
            writer.write(";");
            writer.write(accounts.get(id));
            writer.write("\n");
        }
        writer.close();
    }

    public boolean hasAccount(String id) {
        return accounts.containsKey(id);
    }

    public Account getAccount(String id)  {
        if(!hasAccount(id)) {
            throw new NoSuchElementException("No account with id: " + id);
        } else {
            return new Account(decrypt(accounts.get(id), hashedMasterPassword, id));
        }
    }

    public void addAccount(Account account) {
        if(hasAccount(account.getId())) {
            throw new IllegalArgumentException("User already has an account with id: " + account.getId());
        }
        accounts.put(account.getId(), encrypt(account.toCharArray(), hashedMasterPassword, account.getId()));
    }

    public void resetMastPassword(byte[] hashedMasterPassword) {
        byte[] oldMasterPassword = this.hashedMasterPassword;
        this.hashedMasterPassword = hashedMasterPassword;
        for(String id : accounts.keySet()) {
            accounts.put(id, encrypt(decrypt(accounts.get(id), oldMasterPassword, id), hashedMasterPassword, id));
        }
    }

    private String encrypt(char[] plainText, byte[] masterPassword, String id) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(id.getBytes(StandardCharsets.UTF_8));
            byte[] iv = Arrays.copyOf(md.digest(), 16);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(masterPassword, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
//            byte[] plainBytes = new String(plainText).getBytes(StandardCharsets.UTF_8); //TODO: cheating
            byte[] plainBytes = CharUtilities.charsToBytes(plainText);
            byte[] cipherBytes = cipher.doFinal(plainBytes);
            return Base64.getEncoder().encodeToString(cipherBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
        return null;
    }

    private char[] decrypt(String cipherText, byte[] masterPassword, String id) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(id.getBytes(StandardCharsets.UTF_8));
            byte[] iv = Arrays.copyOf(md.digest(), 16);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(masterPassword, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
            byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
            byte[] plainBytes = cipher.doFinal(cipherBytes);
//            return new String(plainBytes, StandardCharsets.UTF_8).toCharArray(); //TODO: cheating
            return CharUtilities.bytesToChars(plainBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
        return null;
    }
}