import java.io.File;
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
        File file = new File("rar.txt");
        if(file.exists()) {
            user = new User(file);
        } else {
            user = new User("password");
        }
        result = InputHelper.optionMenu("Reset Master Password", "Add Account", "Share Account", "Exit");

        if(result == 0) { //Reset Master Password

            System.out.println("Are you sure? The process cannot be reversed");
            int result2 = InputHelper.optionMenu("Yes","No");

            if(result2 == 0) {

            } else {
                System.out.println("Very well, process cancelled");
            }

        } else if (result == 1) { //Add Account

            System.out.print("Please enter account ID: ");
            String newID = scanner.nextLine();
            System.out.print("Please enter account Username: ");
            String newUsername = scanner.nextLine();
            System.out.print("Please enter account Password: ");
            String newPassword = scanner.nextLine();

            Account newAccount = new Account(newID, newUsername, newPassword);

        } else if (result == 2){ //Share Account



        } else { //Exit

        }

        user.saveToFile(file);
    }
}