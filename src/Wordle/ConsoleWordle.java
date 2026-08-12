package Wordle;
import java.util.*;

public class ConsoleWordle {
    private static final String[] WORD_BANK = {
        "APPLE", "BRAIN", "CRANE", "DRIVE", "EAGER", "FLAME", "GRASS"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String secret = WORD_BANK[random.nextInt(WORD_BANK.length)];
        int attempts = 0;

        System.out.println("Welcome to Wordle!");
        System.out.println("You have 6 tries to guess the 5-letter word.");
        System.out.println("Format:");
        System.out.println("A  = Correct letter, correct position (Green)");
        System.out.println("A* = Correct letter, wrong position (Yellow)");
        System.out.println("_  = Wrong letter\n");

        while (attempts < 6) {
            System.out.print("Guess " + (attempts + 1) + ": ");
            String guess = scanner.nextLine().toUpperCase();

            if (guess.length() != 5) {
                System.out.println("Please enter a 5-letter word.");
                continue;
            }

            String result = getGameString(secret, guess);
            System.out.println("Result: " + result);

            if (guess.equals(secret)) {
                System.out.println("You win! 🎉");
                return;
            }

            attempts++;
        }
        
        System.out.println("You lost! The word was: " + secret);
        scanner.close();
    }

    private static String getGameString(String secret, String guess) {
        String[] placeholder = new String[5];
        boolean[] secretUsed = new boolean[5];
        boolean[] guessUsed = new boolean[5];

        // Green pass - right letter, right position
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == secret.charAt(i)) {
                placeholder[i] = String.valueOf(guess.charAt(i));
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        // Yellow pass - right letter, wrong position
        for (int i = 0; i < 5; i++) {
            if (guessUsed[i]) continue;
            for (int j = 0; j < 5; j++) {
                if (!secretUsed[j] && guess.charAt(i) == secret.charAt(j)) {
                    placeholder[i] = guess.charAt(i) + "*";
                    secretUsed[j] = true;
                    guessUsed[i] = true;
                    break;
                }
            }
        }

        // X pass - wrong letters
        for (int i = 0; i < 5; i++) {
            if (placeholder[i] == null) {
                placeholder[i] = "_ ";
            }
        }

        return String.join("", placeholder);
    }
}
