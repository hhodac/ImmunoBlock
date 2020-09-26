package util;

public class StringUtils {
    public static String stringToHex(String input) {
        // Step-1 - Convert ASCII string to char array
        char[] ch = input.toCharArray();

        // Step-2 Iterate over char array and cast each element to Integer.
        StringBuilder builder = new StringBuilder();
        for (char c : ch) {
            builder.append(Integer.toHexString(c).toUpperCase());
        }

        return builder.toString();
    }

    public static String hexToString(String hex) throws Exception {
        if (hex.length() % 2 != 0) {
            throw new Exception("Invalid hex string.");
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hex.length(); i = i + 2) {
            // Step-1 Split the hex string into two character group
            String s = hex.substring(i, i + 2);
            // Step-2 Convert the each character group into integer using valueOf method
            int n = Integer.valueOf(s, 16);
            // Step-3 Cast the integer value to char
            builder.append((char) n);
        }

        return builder.toString();
    }
}
