import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main {
    private static int MAX_PASSWORD_LENGTH = 24;
    private static int MIN_PASSWORD_LENGTH = 12;

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int length = random.nextInt(MAX_PASSWORD_LENGTH) + MIN_PASSWORD_LENGTH;
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (char) (random.nextInt(78) + 48);
        }
        return new String(chars);
    }

    public static boolean checkInput(String input) {
        boolean validated = false;
        if (!input.contains(",") && !input.contains(";")) {
            validated = true;
        }
        return validated;
    }

    public static void resetMasterPassword(User user, Scanner scanner) {

        System.out.println("Are you sure? The process cannot be reversed");
        int result2 = InputHelper.optionMenu("Yes", "No");

        if (result2 == 0) {

            boolean methodloop = true;

            while (methodloop) {
                System.out.print("Please input new master password: ");
                String masterpassword = scanner.nextLine();

                if (checkInput(masterpassword)) {
                    user.setMasterPassword(masterpassword);
                    methodloop = false;
                } else {
                    System.out.println("Input cannot contain commas or semicolons, please try again");
                    System.out.print("Press enter to continue");
                    scanner.nextLine();
                }
            }

        } else {
            System.out.println("Very well, process cancelled");
            System.out.print("Press enter to continue");
            scanner.nextLine();
        }

    }

    public static void addAccount(User user, Scanner scanner) {

        String acctID = "";
        String acctUser = "";
        String acctPass = "";

        boolean idLoop = true;
        while (idLoop) {
            System.out.print("Please enter account ID: ");
            String newID = scanner.nextLine();
            if (!checkInput(newID)) {
                System.out.println("Input cannot contain commas or semicolons, please try again");
                System.out.print("Press enter to continue");
                scanner.nextLine();
            } else {
                idLoop = false;
                acctID = newID;
            }
        }

        boolean userLoop = true;
        while (userLoop) {
            System.out.print("Please enter account Username: ");
            String newUsername = scanner.nextLine();
            if (!checkInput(newUsername)) {
                System.out.println("Input cannot contain commas or semicolons, please try again");
                System.out.print("Press enter to continue");
                scanner.nextLine();
            } else {
                userLoop = false;
                acctUser = newUsername;
            }
        }

        int resultpass = InputHelper.optionMenu("Generate Random Password", "Input Custom Password");
        String newPassword;

        if (resultpass == 0) {
            newPassword = generateRandomPassword();
            System.out.println("Password generated: " + newPassword + "\n");
            acctPass = newPassword;
        } else {
            boolean passLoop = true;
            while (passLoop) {
                System.out.print("Please enter account Password: ");
                newPassword = scanner.nextLine();
                if (!checkInput(newPassword)) {
                    System.out.println("Input cannot contain commas or semicolons, please try again");
                    System.out.print("Press enter to continue");
                    scanner.nextLine();
                } else {
                    passLoop = false;
                    acctPass = newPassword;
                }
            }
        }

        Account newAccount = new Account(acctID, acctUser, acctPass);
        user.getAccounts().add(newAccount);

        System.out.println("Account added");
        System.out.print("Press enter to continue");
        String proceed = scanner.nextLine();

    }

    public static void retrieveAccount(User user, Scanner scanner) {

        System.out.print("Please enter the ID of the account you wish to view: ");
        String retrieveID = scanner.nextLine();
        Account targetAccount = null;

        for (Account acc : user.getAccounts()) {
            if (acc.getId().equals(retrieveID)) {
                targetAccount = acc;
            }
        }

        if (targetAccount == null) {
            System.out.println("Sorry, that account does not exist, please try again later");
            System.out.print("Press enter to continue");
            String proceed = scanner.nextLine();
        } else {
            System.out.println("Account Username: " + targetAccount.getUsername());
            System.out.println("Account Password: " + targetAccount.getPassword());
            System.out.print("Press enter to continue");
            String proceed = scanner.nextLine();
        }

    }

    public static void shareAccount(User user, Scanner scanner) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        System.out.print("Please enter the ID of the account you wish to share: ");
        String shareID = scanner.nextLine();

        Account targetAccount = null;

        for (Account acc : user.getAccounts()) {
            if (acc.getId().equals(shareID)) {
                targetAccount = acc;
            }
        }

        if (targetAccount == null) {
            System.out.println("Sorry, that account does not exist, please try again later");
            System.out.print("Press enter to continue");
            String proceed = scanner.nextLine();
        } else {
            targetAccount.saveToFile("share.txt");
            System.out.println("File created in local directory: share.txt");
            System.out.print("Press enter to continue");
            String proceed = scanner.nextLine();
        }

    }

    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, InvalidAlgorithmParameterException {
        int result = -1;
        Scanner scanner = new Scanner(System.in);
        User user = null;
        File file = new File("save.txt");
        if (file.exists()) {
            user = new User(file);
        } else {
            user = new User("password");
        }

        boolean passwordloop = true;

        while (passwordloop) {
            System.out.print("Please enter password: ");
            String checkpassword = scanner.nextLine();

            if (checkpassword.equals(user.getMasterPassword())) {
                System.out.println("\nPASSWORD VERIFIED\nUSER AUTHENTICATED\nREMOVING SECURITY RESTRICTIONS");
                passwordloop = false;
            } else {
                System.out.println("\nPassword incorrect, please try again\n");
            }

        }

        boolean menuLoop = true;

        while (menuLoop) {

            System.out.println("\nPASSWORD VAULT v1 - MAIN MENU\n");
            result = InputHelper.optionMenu("Reset Master Password", "Add Account", "Retrieve Account", "Share Account", "Exit");

            switch (result) {

                case 0:
                    resetMasterPassword(user, scanner);
                    break;
                case 1:
                    addAccount(user, scanner);
                    break;
                case 2:
                    retrieveAccount(user, scanner);
                    break;
                case 3:
                    shareAccount(user, scanner);
                    break;
                case 4:
                    menuLoop = false;
                    System.out.println("\nThank you for using Password Vault v1");
                    break;
            }

        }
        user.saveToFile("save.txt");
    }
}