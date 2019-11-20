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
    public static final int SALT_LENGTH = 20;

    private Map<String, String> accounts = new HashMap<>();
    private byte[] key, hashedMasterPassword;
    private String salt, saltedHashedMasterPassword;

    public User(char[] masterPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(CharHelper.charsToBytes(masterPassword));
            this.hashedMasterPassword = md.digest();

            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecretKey aesKey = keygen.generateKey();
            key = aesKey.getEncoded();

            salt = new String(CharHelper.generateSecureRandomString(20));
            saltedHashedMasterPassword = hashSaltedMasterPassword(salt, masterPassword);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
    }

    public User(File file, char[] masterPassword) throws FileNotFoundException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(CharHelper.charsToBytes(masterPassword));
            this.hashedMasterPassword = md.digest();

            Scanner scanner = new Scanner(new FileInputStream(file));
            String headLine = scanner.nextLine();
            salt = headLine.substring(0, SALT_LENGTH);
            saltedHashedMasterPassword = headLine.substring(SALT_LENGTH);
            this.key = readKeyFromFile(scanner, hashedMasterPassword);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int split = line.indexOf(';');
                accounts.put(line.substring(0, split), line.substring(split + 1));
            }
            scanner.close();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
    }

    public void saveToFile(File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(salt);
        writer.write(saltedHashedMasterPassword);
        writer.write("\n");
        writeKeyToFile(writer, key, hashedMasterPassword);
        for(String id : accounts.keySet()) {
            writer.write(id);
            writer.write(";");
            writer.write(accounts.get(id));
            writer.write("\n");
        }
        writer.close();
    }

    public void clean() {
        Arrays.fill(key, (byte) 0);
        Arrays.fill(hashedMasterPassword, (byte) 0);
    }

    public boolean hasAccount(String id) {
        return accounts.containsKey(id);
    }

    public Account getAccount(String id)  {
        if(!hasAccount(id)) {
            throw new NoSuchElementException("No account with id: " + id);
        } else {
            char[] accountText = decrypt(accounts.get(id), key, id);
            Account result = new Account(accountText);
            Arrays.fill(accountText, (char) 0);
            return result;
        }
    }

    public void addAccount(Account account) {
        if(hasAccount(account.getId())) {
            throw new IllegalArgumentException("User already has an account with id: " + account.getId());
        }
        accounts.put(account.getId(), encrypt(account.toCharArray(), key, account.getId()));
    }

    public void resetMastPassword(char[] masterPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(CharHelper.charsToBytes(masterPassword));
            this.hashedMasterPassword = md.digest();
            this.saltedHashedMasterPassword = hashSaltedMasterPassword(salt, masterPassword);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
    }

    private static void writeKeyToFile(FileWriter writer, byte[] key, byte[] hashedMasterPassword) throws IOException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(hashedMasterPassword, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] cipherBytes = cipher.doFinal(key);
            writer.write(Base64.getEncoder().encodeToString(cipherBytes));
            Arrays.fill(cipherBytes, (byte) 0);

            writer.write(";");

            byte[] iv = cipher.getIV();
            writer.write(Base64.getEncoder().encodeToString(iv));
            Arrays.fill(iv, (byte) 0);

            writer.write("\n");
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
    }

    private static String encrypt(char[] plainText, byte[] key, String id) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(id.getBytes(StandardCharsets.UTF_8));
            byte[] iv = Arrays.copyOf(md.digest(), 16);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
            byte[] plainBytes = CharHelper.charsToBytes(plainText);
            byte[] cipherBytes = cipher.doFinal(plainBytes);
            Arrays.fill(plainBytes, (byte) 0);
            return Base64.getEncoder().encodeToString(cipherBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
        return null;
    }

    private static byte[] readKeyFromFile(Scanner scanner, byte[] hashedMasterPassword) {
        try {
            String[] components = scanner.nextLine().split(";");
            byte[] cipherBytes = Base64.getDecoder().decode(components[0]);
            byte[] iv = Base64.getDecoder().decode(components[1]);

            SecretKey key = new SecretKeySpec(hashedMasterPassword, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            return cipher.doFinal(cipherBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static char[] decrypt(String cipherText, byte[] key, String id) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(id.getBytes(StandardCharsets.UTF_8));
            byte[] iv = Arrays.copyOf(md.digest(), 16);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
            byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
            byte[] plainBytes = cipher.doFinal(cipherBytes);
            char[] plainText =  CharHelper.bytesToChars(plainBytes);
            Arrays.fill(plainBytes, (byte) 0);
            return plainText;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
        return null;
    }

    public static String hashSaltedMasterPassword(String salt, char[] masterPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            char[] toEncrypt = new char[SALT_LENGTH + 256];
            System.arraycopy(salt.toCharArray(), 0, toEncrypt, 0, salt.length());
            System.arraycopy(masterPassword, 0, toEncrypt, salt.length(), masterPassword.length);
            md.update(CharHelper.charsToBytes(toEncrypt));
            String checkHash = Base64.getEncoder().encodeToString(md.digest());
            Arrays.fill(toEncrypt, (char) 0);
            return checkHash;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
        return null;
    }

    public String getSalt() {
        return salt;
    }

    public String getSaltedHashedMasterPassword() {
        return saltedHashedMasterPassword;
    }
}