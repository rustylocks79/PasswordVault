import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static int MAX_PASSWORD_LENGTH = 24;
    private static int MIN_PASSWORD_LENGTH = 12;

    public static char[] generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int length = random.nextInt(MAX_PASSWORD_LENGTH) + MIN_PASSWORD_LENGTH;
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (char) (random.nextInt(78) + 48);
        }
        return chars;
    }

    public static boolean checkInput(String input) {
        boolean validated = false;
        if (!input.contains(";")) {
            validated = true;
        }
        return validated;
    }

    public static boolean checkInput(char[] input) {
        for(char c:input) {
            if(c == ';') return false;
        }
        return true;
    }

    public static boolean checkMasterPassword(User user) {
        boolean passwordloop = true;
        Console console =  System.console();
        boolean passwordverified = false;

        while (passwordloop) {
            System.out.print("Please enter password: ");
            char[] checkpassword = console.readPassword();

            //TODO fix after password verification is added
//            if (checkpassword.equals(user.getMasterPassword())) {
//                System.out.println("\nPASSWORD VERIFIED\nUSER AUTHENTICATED\nREMOVING SECURITY RESTRICTIONS");
//                Arrays.fill(checkpassword, '0');
//                passwordverified = true;
//                passwordloop = false;
//            } else {
//                System.out.println("\nPassword incorrect, please try again\n");
//            }

        }
        return passwordverified;
    }

    // TODO reset options menu
    public static void resetMasterPassword(User user, BufferedReader reader) throws IOException {

        System.out.println("Are you sure? The process cannot be reverted");
        int result2 = InputHelper.optionMenu("Yes", "No");

        if (result2 == 0) {

            boolean methodloop = true;
            Console console =  System.console();

            while (methodloop) {
                System.out.print("Please input new master password: ");
                char[] masterpassword = console.readPassword();

                if (checkInput(masterpassword)) {
                    //TODO fix after reset function added
                    //user.resetMastPassword(masterpassword);
                    Arrays.fill(masterpassword,'0');
                    methodloop = false;
                } else {
                    System.out.println("Input cannot contain commas or semicolons, please try again");
                    System.out.print("Press enter to continue");
                    reader.readLine();
                }
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
//        checking ids
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

    // TODO retrieve options menu
    public static void retrieveAccount(User user, BufferedReader reader) {

//        System.out.print("Please enter the ID of the account you wish to view: ");
//        String retrieveID = scanner.nextLine();
//        Account targetAccount = null;
//
//        for (Account acc : user.getAccounts()) {
//            if (acc.getId().equals(retrieveID)) {
//                targetAccount = acc;
//            }
//        }
//
//        if (targetAccount == null) {
//            System.out.println("Sorry, that account does not exist, please try again later");
//            System.out.print("Press enter to continue");
//            String proceed = scanner.nextLine();
//        } else {
//            System.out.println("Account Username: " + targetAccount.getUsername());
//            System.out.println("Account Password: " + targetAccount.getPassword());
//            System.out.print("Press enter to continue");
//            String proceed = scanner.nextLine();
//        }

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

        // TODO creating a user and retrieving file
        User user = null;
//        File file = new File("save.txt");
//        if (file.exists()) {
//            user = new User(file);
//        } else {
//            user = new User("password");
//        }

        boolean menuLoop = true;
        while (menuLoop) {

            int result = -1;
            System.out.println("\nJEB'S PASSWORD VAULT - MAIN MENU\n");
            result = InputHelper.optionMenu("Reset Master Password", "Add Account", "Retrieve Account", "Share Account", "Exit");

            switch (result) {

                case 0:
                    if(!checkMasterPassword(user)) {break;}
                    resetMasterPassword(user, reader);
                    break;
                case 1:
                    if(!checkMasterPassword(user)) {break;}
                    addAccount(user, reader);
                    break;
                case 2:
                    if(!checkMasterPassword(user)) {break;}
                    retrieveAccount(user, reader);
                    break;
                case 3:
                    if(!checkMasterPassword(user)) {break;}
                    shareAccount(user, reader);
                    break;
                case 4:
                    menuLoop = false;
                    System.out.println("\nThank you for using JEB's Password Vault");
                    break;
            }

        }
        user.saveToFile("save.txt");
    }
}