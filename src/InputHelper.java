import java.io.Console;
import java.util.Scanner;

public class InputHelper {
    public static int optionMenu(String... options) {
        int result;
        boolean condition;
        Scanner scanner = new Scanner(System.in);
        do {
            for(int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ") " + options[i]);
            }
            System.out.print("Enter: ");
            result = scanner.nextInt();
            condition = result < 1 || result > options.length;
            if(condition) {
                System.out.println("Please enter a number between 1 and " + options.length);
            }
        } while (condition);
        return result - 1;
    }

    public static char[] getValidInput(String prompt) {
        Console console = System.console();
        Scanner scanner = new Scanner(System.in);
        char[] result;
        boolean condition;
        do {
            System.out.print(prompt);
            if (System.console() != null) {
                result = console.readPassword();
            } else {
                result = scanner.nextLine().toCharArray();
            }
            condition = CharHelper.contains(result, ';');
            if(condition) {
                System.out.println("Input can't contain a ';'. ");
                System.out.print("Press enter to continue");
                scanner.nextLine();
            }
        } while (condition);
        return result;
    }
}