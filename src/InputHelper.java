import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static int optionMenu(String... options) {
        int result;
        boolean condition;
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
        char[] result;
        boolean condition;
        do {
            System.out.println(prompt);
            result = scanner.nextLine().toCharArray(); //TODO: replace
            condition = CharHelper.contains(result, ';');
            if(condition) {
                System.out.println("Input can't contain a ';'. ");
            }
        } while (condition);
        return result;
    }
}