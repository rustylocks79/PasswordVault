import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static int MAX_PASSWORD_LENGTH = 24;
    private static int MIN_PASSWORD_LENGTH = 12;

    //for created accounts
    public static char[] getMasterPassword(Verifier verifier, Console console) throws NoSuchAlgorithmException {
        char[] password = null;

        for(int i=0; i<3; i++) {
            System.out.print("Please enter password: ");
            if (System.console() != null) {
                password = console.readPassword(); //TODO fix this, scanner is a temporary thing to let program compile
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
    public static char[] createMasterPassword() {
        char[] password = null;

        System.out.println("Welcome! Please enter your master password for the program: ");
        Scanner scanner = new Scanner(System.in);
        password = scanner.nextLine().toCharArray();

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
                    masterpassword = console.readPassword(); //TODO fix this, scanner is a temporary thing to let program compile
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

    // TODO add options menu
    public static void addAccount(User user, BufferedReader reader) {

//        String acctID = "";
//        String acctUser = "";
//        String acctPass = "";
//
//        boolean idLoop = true;
//        while (idLoop) {
//            System.out.print("Please enter account ID: ");
//        //checking ids
//        while(hasAccount(newID))
//        {
//            System.out.println("Please enter a different ID ");
//            newID = scanner.nextLine();
//        }

//            String newID = scanner.nextLine();
//            if (!checkInput(newID)) {
//                System.out.println("Input cannot contain commas or semicolons, please try again");
//                System.out.print("Press enter to continue");
//                scanner.nextLine();
//            } else {
//                idLoop = false;
//                acctID = newID;
//            }
//        }
//
//        boolean userLoop = true;
//        while (userLoop) {
//            System.out.print("Please enter account Username: ");
//            String newUsername = scanner.nextLine();
//            if (!checkInput(newUsername)) {
//                System.out.println("Input cannot contain commas or semicolons, please try again");
//                System.out.print("Press enter to continue");
//                scanner.nextLine();
//            } else {
//                userLoop = false;
//                acctUser = newUsername;
//            }
//        }
//
//        int resultpass = InputHelper.optionMenu("Generate Random Password", "Input Custom Password");
//        String newPassword;
//
//        if (resultpass == 0) {
//            // TODO refactor use of generated password
//            //newPassword = generateRandomPassword();
//            //System.out.println("Password generated: " + newPassword + "\n");
//            //acctPass = newPassword;
//        } else {
//            boolean passLoop = true;
//            while (passLoop) {
//                System.out.print("Please enter account Password: ");
//                newPassword = scanner.nextLine();
//                if (!checkInput(newPassword)) {
//                    System.out.println("Input cannot contain commas or semicolons, please try again");
//                    System.out.print("Press enter to continue");
//                    scanner.nextLine();
//                } else {
//                    passLoop = false;
//                    acctPass = newPassword;
//                }
//            }
//        }
//
//        Account newAccount = new Account(acctID, acctUser, acctPass);
//        user.getAccounts().add(newAccount);
//
//        System.out.println("Account added");
//        System.out.print("Press enter to continue");
//        String proceed = scanner.nextLine();

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

    // TODO sharing options menu
    public static void shareAccount(User user, BufferedReader reader) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

//        System.out.print("Please enter the ID of the account you wish to share: ");
//        String shareID = scanner.nextLine();
//
//        Account targetAccount = null;
//
//        for (Account acc : user.getAccounts()) {
//            if (acc.getId().equals(shareID)) {
//                targetAccount = acc;
//            }
//        }
//
//        if (targetAccount == null) {
//            System.out.println("Sorry, that account does not exist, please try again later");
//            System.out.print("Press enter to continue");
//            String proceed = scanner.nextLine();
//        } else {
//            targetAccount.saveToFile("share.txt");
//            System.out.println("File created in local directory: share.txt");
//            System.out.print("Press enter to continue");
//            String proceed = scanner.nextLine();
//        }

    }

    // TODO main loop
    //TODO error codes
    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, InvalidAlgorithmParameterException {

        {   // This is an example of how to read a char array with buffered reader
//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//
//            char[] arr1 = new char[10];
//
//            System.out.println("Input up to 10 characters: ");
//            reader.read(arr1, 0, 10); //arr1 => target array, 0 => starting index, 10 => max characters
//
//            System.out.println(arr1);
        }

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
                masPass = createMasterPassword();
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