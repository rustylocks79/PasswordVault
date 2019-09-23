import java.util.Scanner;

public class InputHelper {
    public static Scanner scanner = new Scanner(System.in);

    public static int optionMenu(String... options) {
        int result = -1;
        do {
            for(int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ") " + options[i]);
            }
            System.out.print("Enter: ");
            result = scanner.nextInt();
            if(result < 1 || result > options.length) {
                System.out.println("Please enter a number between 1 and " + options.length);
            }
        } while (result < 1 || result > options.length);
        return result - 1;
    }
}