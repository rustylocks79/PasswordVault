import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final int MAX_PASSWORD_LENGTH = 24;
    private static final int MIN_PASSWORD_LENGTH = 12;

    //for created accounts
    public static char[] getMasterPassword(Verifier verifier, Console console) throws NoSuchAlgorithmException {
        char[] password = null;

        for(int i=0; i<3; i++) {
            System.out.print("Please enter password: ");
            if (System.console() != null) {
                password = console.readPassword();
            } else {
                Scanner scanner = new Scanner(System.in);
                password = scanner.nextLine().toCharArray();
            }

            if(verifier.verify(password)) {
                System.out.println("\nPASSWORD VERIFIED\nUSER AUTHENTICATED\nREMOVING SECURITY RESTRICTIONS");
                return password;
            } else {
                System.out.println("\nPassword incorrect, please try again\n");
            }
        }
        return null;
    }

    //for new accounts
    public static char[] createMasterPassword(Console console) {
        char[] password = null;

        System.out.print("Welcome! Please enter your master password for the program: ");
        if (System.console() != null) {
            password = console.readPassword();
        } else {
            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine().toCharArray();
        }

        return password;
    }

    public static void resetMasterPassword(User user, BufferedReader reader) throws IOException {

        System.out.println("Are you sure? The process cannot be reverted");
        int result2 = InputHelper.optionMenu("Yes", "No");

        if (result2 == 0) {

            boolean methodloop = true;
            Console console =  System.console();

            while (methodloop) {
                System.out.print("Please input new master password: ");
                char[] masterpassword = null;
                if (System.console() != null) {
                    masterpassword = console.readPassword();
                } else {
                    Scanner scanner = new Scanner(System.in);
                    masterpassword = scanner.nextLine().toCharArray();
                }
                    user.resetMastPassword(masterpassword);
                    Arrays.fill(masterpassword,'0');
                    methodloop = false;
            }

        } else {
            System.out.println("Very well, process cancelled");
            System.out.print("Press enter to continue");
            reader.readLine();
        }

    }

    public static void addAccount(User user, BufferedReader reader) throws IOException {


        System.out.print("Please enter the ID of the account you wish to add: ");
        String targetID = reader.readLine();

        if(!user.hasAccount(targetID)) {
            char[] username = InputHelper.getValidInput("Please enter account username: ");

            int resultpass = InputHelper.optionMenu("Generate Random Password", "Input Custom Password");
            char[] password = null;

            if (resultpass == 0) {
                SecureRandom secureRandom = new SecureRandom();
                int length = secureRandom.nextInt(MAX_PASSWORD_LENGTH-MIN_PASSWORD_LENGTH) + MIN_PASSWORD_LENGTH;
                password = CharHelper.generateSecureRandomString(length);
            } else {
                password = InputHelper.getValidInput("Please enter account password: ");
            }

            Account acc = new Account(targetID,username,password);
            user.addAccount(acc);
            acc.clean();
        } else {
            System.out.println("Sorry, that account already exists, please try again later");
            System.out.print("Press enter to continue");
            String proceed = reader.readLine();
        }
    }

    public static void retrieveAccount(User user, BufferedReader reader) throws IOException {

        System.out.print("Please enter the ID of the account you wish to view: ");
        String retrieveID = reader.readLine();

        if(user.hasAccount(retrieveID)) {
            Account targetAccount = user.getAccount(retrieveID);
            System.out.print("Account Username: ");
            System.out.println(targetAccount.getUsername());
            System.out.print("Account Password: ");
            System.out.println(targetAccount.getPassword());
            System.out.print("Press enter to continue");
            String proceed = reader.readLine();
            targetAccount.clean();
        } else {
            System.out.println("Sorry, that account does not exist, please try again later");
            System.out.print("Press enter to continue");
            String proceed = reader.readLine();
        }

    }

    public static void shareAccount(User user, BufferedReader reader) throws IOException {

        System.out.print("Please enter the ID of the account you wish to share: ");
        String shareID = reader.readLine();

        if(user.hasAccount(shareID)) {
            Account targetAccount = user.getAccount(shareID);
            System.out.print("Please enter the file path for the receiver's certificate: ");
            String recCert = reader.readLine();
            try {
                Networker.share(recCert, "share.txt", targetAccount);
            } catch (CertificateException e) {
                System.err.println("Receiver certificate not valid");
            } catch (IOException e) {
                System.err.println("File could not be found");
                System.err.println(recCert);
            }
            targetAccount.clean();
        } else {
            System.out.println("Sorry, that account does not exist, please try again later");
            System.out.print("Press enter to continue");
            String proceed = reader.readLine();
        }
    }

    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, InvalidAlgorithmParameterException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Verifier verifier = null;
        User user = null;
        File file = new File("save.txt");
        char[] masPass = null;
        Console console =  System.console();

        //getting or creating user
        {
            if (file.exists()) {
                verifier = new Verifier(file);
                masPass = getMasterPassword(verifier, console);
                if(masPass != null) {
                    user = new User(file, masPass);
                    Arrays.fill(masPass,'0');
                } else {
                    System.err.println("Incorrect Password, exiting program");
                    System.exit(-1);
                }
            } else {
                masPass = createMasterPassword(console);
                user = new User(masPass);
                Arrays.fill(masPass,'0');
                verifier = new Verifier(user);
            }
        }

        boolean menuLoop = true;
        while (menuLoop) {

            int result = -1;
            System.out.println("\nJEB'S PASSWORD VAULT - MAIN MENU\n");
            result = InputHelper.optionMenu("Reset Master Password", "Add Account", "Retrieve Account", "Share Account", "Exit");
            switch (result) {
                case 0:
                    if(getMasterPassword(verifier,console) == null)
                    {
                        System.err.println("Incorrect password, exiting program");
                        System.exit(-1);
                    }
                    resetMasterPassword(user, reader);
                    verifier = new Verifier(user);
                    break;
                case 1:
                    if(getMasterPassword(verifier,console) == null)
                    {
                        System.err.println("Incorrect password, exiting program");
                        System.exit(-1);
                    }
                    addAccount(user, reader);
                    break;
                case 2:
                    if(getMasterPassword(verifier,console) == null)
                    {
                        System.err.println("Incorrect password, exiting program");
                        System.exit(-1);
                    }
                    retrieveAccount(user, reader);
                    break;
                case 3:
                    if(getMasterPassword(verifier,console) == null)
                    {
                        System.err.println("Incorrect password, exiting program");
                        System.exit(-1);
                    }
                    shareAccount(user, reader);
                    break;
                case 4:
                    menuLoop = false;
                    System.out.println("\nThank you for using JEB's Password Vault");
                    break;
            }

        }
        user.saveToFile(new File("save.txt"));
    }
}