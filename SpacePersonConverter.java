import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

public class SpacePersonConverter {
    private static final HashMap<Character, Character> spacePersonAlphabet = new HashMap<>();

    static {
        spacePersonAlphabet.put('a', '~');
        spacePersonAlphabet.put('b', '!');
        spacePersonAlphabet.put('c', '@');
        spacePersonAlphabet.put('d', '#');
        spacePersonAlphabet.put('e', '$');
        spacePersonAlphabet.put('f', '%');
        spacePersonAlphabet.put('g', '^');
        spacePersonAlphabet.put('h', '&');
        spacePersonAlphabet.put('i', '*');
        // Add more mappings for the rest of the alphabet
    }

    public static void main(String[] args) {
        // Prompt the user for the English string
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the English string: ");
        String englishString = scanner.nextLine();

        // Create a new Space Person alphabet
        HashMap<Character, Character> newSpacePersonAlphabet = createNewSpacePersonAlphabet();

        // Convert to Space Person string using the new alphabet
        String spacePersonString = convertToSpacePersonString(englishString, newSpacePersonAlphabet);
        System.out.println("Space Person string: " + spacePersonString);

        // Calculate SHA256 hash value for the Space Person string
        String hashValue = calculateSHA256(spacePersonString);
        System.out.println("SHA256 hash value: " + hashValue);

        // Apply Caesar cipher with a 5-character shift on the English string
        String caesarCipherResult = caesarCipher(englishString, 5);
        System.out.println("Caesar cipher (5-character shift): " + caesarCipherResult);

        // Brute force all 0-25 shifts of the English string
        bruteForceCaesarCipher(englishString);

        scanner.close();
    }

    private static HashMap<Character, Character> createNewSpacePersonAlphabet() {
        HashMap<Character, Character> newAlphabet = new HashMap<>();
        // Use a different set of symbols (~!@#$%^&*) for the new Space Person alphabet
        char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] symbols = "~!@#$%^&*".toCharArray();

        for (int i = 0; i < letters.length; i++) {
            newAlphabet.put(letters[i], symbols[i % symbols.length]);
        }

        return newAlphabet;
    }

    private static String convertToSpacePersonString(String englishString, HashMap<Character, Character> alphabet) {
        StringBuilder result = new StringBuilder();
        for (char c : englishString.toCharArray()) {
            result.append(alphabet.getOrDefault(Character.toLowerCase(c), c));
        }
        return result.toString();
    }

    private static String calculateSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String caesarCipher(String input, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            result.append(shiftCharacter(c, shift));
        }
        return result.toString();
    }

    private static char shiftCharacter(char c, int shift) {
        if (Character.isLetter(c)) {
            char base = Character.isUpperCase(c) ? 'A' : 'a';
            return (char) ((c - base + shift) % 26 + base);
        } else {
            return c;
        }
    }

    private static void bruteForceCaesarCipher(String input) {
        System.out.println("Brute forcing Caesar cipher:");
        for (int shift = 0; shift < 26; shift++) {
            System.out.println("Shift " + shift + ": " + caesarCipher(input, shift));
        }
    }
}
