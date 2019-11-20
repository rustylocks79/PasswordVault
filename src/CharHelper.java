import java.security.SecureRandom;

public class CharHelper {
    public static char[] generateSecureRandomString(int length) {
        SecureRandom random = new SecureRandom();
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (char) (random.nextInt(78) + 48);
        }
        return chars;
    }

    public static boolean contains(char[] chars, char search) {
        for(char c: chars) {
            if(c == search) return true;
        }
        return false;
    }

    public static int indexOf(char[] chars, char c, int start) {
        for (int i = start; i < chars.length; i++) {
            if(chars[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(char[] chars, char c) {
        return indexOf(chars, c, 0);
    }

    public static byte[] charsToBytes(char[] chars) {
        byte[] bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }
        return bytes;
    }

    public static char[] bytesToChars(byte[] bytes) {
        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            chars[i] = (char) bytes[i];
        }
        return chars;
    }
}