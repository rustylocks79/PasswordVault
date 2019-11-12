import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class User {
    private String masterPassword;
    private ArrayList<Account> accounts = new ArrayList<>();

    public User(String masterPassword) {
        setMasterPassword(masterPassword);
    }

    public User(File file) throws FileNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(file);

        byte[] secret_key = Base64.getDecoder().decode(scanner.nextLine());
        byte[] iv = Base64.getDecoder().decode(scanner.nextLine());
        byte[] cipherText = Base64.getDecoder().decode(scanner.nextLine());

        IvParameterSpec receiver_iv = new IvParameterSpec(iv);
        SecretKey receiver_secret = new SecretKeySpec(secret_key, "AES");

        Cipher receiverCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        receiverCipher.init(Cipher.DECRYPT_MODE, receiver_secret, receiver_iv);

        String plaintext = new String(receiverCipher.doFinal(cipherText), "UTF-8");
        String[] lines = plaintext.split(";");
        masterPassword = lines[0];
        int size = Integer.parseInt(lines[1]);
        for(int i = 0; i < size; i++) {
            accounts.add(new Account(lines[2 + i]));
        }
        scanner.close();
    }

    public void saveToFile(String filePath) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        StringBuilder builder = new StringBuilder();
        builder.append(masterPassword)
                .append(";")
                .append(accounts.size())
                .append(";");
        for(Account account : accounts) {
            builder.append(account.toString()).append(";");
        }
        String plaintext = builder.toString();

        // Create Key
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey aesKey = keygen.generateKey();

        // Encrypt Message
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] secret = aesKey.getEncoded();
        byte[] iv = cipher.getIV();
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF-8"));

        //Write Message
        FileWriter writer = new FileWriter(filePath);
        writer.write(Base64.getEncoder().encodeToString(secret));
        writer.write("\n");
        writer.write(Base64.getEncoder().encodeToString(iv));
        writer.write("\n");
        writer.write(Base64.getEncoder().encodeToString(ciphertext));
        writer.close();
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