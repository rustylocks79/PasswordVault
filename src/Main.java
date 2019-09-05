import java.util.Random;

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
        System.out.println(generateRandomPassword());
    }
}