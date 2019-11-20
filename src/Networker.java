import javax.crypto.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;

public class Networker {
    private Certificate createValidCertificate(String certFilePath) throws FileNotFoundException, CertificateException {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate receiverCertificate = cf.generateCertificate(new FileInputStream(certFilePath));
            Certificate caCert = cf.generateCertificate(new FileInputStream("caCert.crt"));
            receiverCertificate.verify(caCert.getPublicKey());
            return receiverCertificate;
        } catch (CertificateException | InvalidKeyException | SignatureException | NoSuchProviderException e) {
            throw new CertificateException("The provided certificate is not valid. ");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
        return null;
    }

    public void share(String certFilePath, String resultPath, Account account) throws FileNotFoundException, CertificateException {
        try {
            Certificate cert = createValidCertificate(certFilePath);
            Cipher rsaCipher = Cipher.getInstance("RSA");
            rsaCipher.init(Cipher.WRAP_MODE, cert);
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecretKey aesKey = keygen.generateKey();
            String wrappedKey = Base64.getEncoder().encodeToString(rsaCipher.doFinal(aesKey.getEncoded()));
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
            char[] plainChars = account.toCharArray();

        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException e) {
            System.err.println("This program is improperly configured. ");
            System.err.println("Please report this on https://chickenonaraft.com/");
            System.exit(-1);
        }
    }
}
