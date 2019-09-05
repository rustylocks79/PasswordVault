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
        File file = new File("C:\\rar.txt");
        if(file.exists()) {
            user = new User(file);
        } else {
            user = new User("password");
        }
        result = InputHelper.optionMenu("Reset Master Password", "Add Account");

        user.saveToFile(file);
    }
}