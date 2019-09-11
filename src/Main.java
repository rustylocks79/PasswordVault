import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static int MAX_PASSWORD_LENGTH = 20;
    private static int MIN_PASSWORD_LENGTH = 6;

    public static String generateRandomPassword() {
        Random random = new Random();
        int length = random.nextInt(MAX_PASSWORD_LENGTH) + MIN_PASSWORD_LENGTH;
        char[] chars = new char[length];
        for(int i = 0; i < length; i++) {
            chars[i] = (char) (random.nextInt(89) + 33);
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        int result = -1;
        Scanner scanner = new Scanner(System.in);
        User user = null;
        URL url = Main.class.getResource("save.txt");
        File file = new File(url.getPath());
        if(file.exists()) {
            user = new User(file);
        } else {
            user = new User("password");
        }

        boolean passwordloop = true;

        while(passwordloop) {
            System.out.print("Please enter password: ");
            String checkpassword = scanner.nextLine();

            if(checkpassword.equals(user.getMasterPassword())) {
                System.out.println("\nPASSWORD VERIFIED\nUSER AUTHENTICATED\nREMOVING SECURITY RESTRICTIONS");
                passwordloop = false;
            } else {
                System.out.println("\nPassword incorrect, please try again\n");
            }

        }

        boolean menuLoop = true;

        while(menuLoop) {

            System.out.println("\nPASSWORD VAULT v1 - MAIN MENU\n");
            result = InputHelper.optionMenu("Reset Master Password", "Add Account", "Retrieve Account", "Share Account", "Exit");

            if (result == 0) { //Reset Master Password

                System.out.println("Are you sure? The process cannot be reversed");
                int result2 = InputHelper.optionMenu("Yes", "No");

                if (result2 == 0) {

                    System.out.print("Please input new master password: ");
                    String masterpassword = scanner.nextLine();
                    user.setMasterPassword(masterpassword);
                } else {
                    System.out.println("Very well, process cancelled");
                }

            } else if (result == 1) { //Add Account

                System.out.print("Please enter account ID: ");
                String newID = scanner.nextLine();
                System.out.print("Please enter account Username: ");
                String newUsername = scanner.nextLine();

                int resultpass = InputHelper.optionMenu("Generate Random Password", "Input Custom Password");
                String newPassword;

                if(resultpass == 0) {
                    newPassword = generateRandomPassword();
                    System.out.println("Password generated: " + newPassword + "\n");
                } else {
                    System.out.print("Please enter account Password: ");
                    newPassword = scanner.nextLine();
                }

                Account newAccount = new Account(newID, newUsername, newPassword);
                user.getAccounts().add(newAccount);

                System.out.println("Account added");

            } else if(result == 2) { //Retrieve Account

                System.out.print("Please enter the ID of the account you wish to view: ");
                String retrieveID = scanner.nextLine();
                Account targetAccount = null;

                for(Account acc:user.getAccounts()) {
                    if(acc.getId().equals(retrieveID)) {
                        targetAccount = acc;
                    }
                }

                if(targetAccount == null) {
                    System.out.println("Sorry, that account does not exist, please try again later");
                } else {
                    System.out.println("Account Username: " + targetAccount.getUsername());
                    System.out.println("Account Password: " + targetAccount.getPassword());
                }

            } else if (result == 3) { //Share Account

                System.out.print("Please enter the ID of the account you wish to share: ");
                String shareID = scanner.nextLine();

                Account targetAccount = null;

                for(Account acc:user.getAccounts()) {
                    if(acc.getId().equals(shareID)) {
                        targetAccount = acc;
                    }
                }

                if(targetAccount == null) {
                    System.out.println("Sorry, that account does not exist, please try again later");
                } else {
                    targetAccount.writeToFile("share.txt");
                }

            } else { //Exit
                menuLoop = false;
                System.out.println("\nThank you for using Password Vault v1");
            }


        }
        user.saveToFile(file);
    }
}