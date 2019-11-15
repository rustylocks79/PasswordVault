import java.util.Arrays;

public class Account {

    private String id;
    private char[] username, password;

    public Account(char[] chars) {
        int index0 = CharUtilities.indexOf(chars, ';');
        int index1 = CharUtilities.indexOf(chars, ';', index0 + 1);
        id = new String(Arrays.copyOfRange(chars, 0, index0));
        username = Arrays.copyOfRange(chars, index0 + 1, index1);
        password = Arrays.copyOfRange(chars, index1 + 1, chars.length);
    }

    public Account(String id, char[] username, char[] password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void clean() {
        Arrays.fill(username, (char) 0);
        Arrays.fill(password, (char) 1);
    }

    public char[] toCharArray() {
        final char[] id = this.id.toCharArray();
        char[] result = new char[id.length + username.length + password.length + 2];
        System.arraycopy(id, 0, result, 0, id.length);
        result[id.length] = ';';
        System.arraycopy(username, 0, result, id.length + 1, username.length);
        result[id.length + 1 + username.length] = ';';
        System.arraycopy(password, 0, result, id.length + 1 + username.length + 1, password.length);
        return result;
    }

    public String getId() {
        return id;
    }

    public char[] getUsername() {
        return username;
    }

    public char[] getPassword() {
        return password;
    }
}