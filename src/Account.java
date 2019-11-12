import javax.crypto.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Account {

    private String id,username,password;

    public Account(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Account(String fileLine) {
        String[] tokens = fileLine.split(",");
        if(tokens.length != 3) { throw new IllegalArgumentException("File Line not properly formatted. "); }
        id = tokens[0];
        username = tokens[1];
        password = tokens[2];
    }

    public void saveToFile(String filePath) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(",").append(username).append(",").append(password);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return id + "," + username + "," + password;
    }
}