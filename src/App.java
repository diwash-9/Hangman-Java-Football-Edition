import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String filePath = "words.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        List<String> words = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isBlank()) {
                words.add(line.trim());
            }
        }
        Random rand = new Random();
        String randomWord = words.get(rand.nextInt(words.size()));

        String word = randomWord.toLowerCase();
        ArrayList<String> wordState = new ArrayList<>();
        int wrongGuesses = 0;

        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            wordState.add("_");
        }
        System.out.println("***********************");
        System.out.println("WELCOME TO HANGMAN GAME (FOOTBALL VERSION)");
        System.out.println("***********************");
        while (wrongGuesses < 6) {
            printCurrentWordState(wordState);
            Character userGuess;
            System.out.print("Guess a letter: ");
            userGuess = scanner.next().toLowerCase().charAt(0);

            System.out.println("***********************");
            System.out.println("You guessed: " + userGuess);
            System.out.println("***********************");
            if (word.contains(String.valueOf(userGuess))) {
                // System.out.println("Correct Guess");
                System.out.println("Correct guess. Current Hangman State");
                System.out.println(printHangmanArt(wrongGuesses));
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == userGuess) {
                        wordState.set(i, String.valueOf(userGuess));
                    }

                }
                if (!wordState.contains("_")) {
                    System.out.println("You won!!");
                    System.out.println("The word was: " + word);
                    break;
                }
            }

            else {
                wrongGuesses += 1;
                System.out.println("Wrong guess. Current Hangman State");
                System.out.println(printHangmanArt(wrongGuesses));

            }
            if (wrongGuesses == 6) {
                System.out.println("GAME OVER!");
                System.out.println("The word was: " + word);
            }
        }
    }

    static void printCurrentWordState(ArrayList<String> wordState) {
        System.out.println(String.join(" ", wordState));
        System.out.println();
    }

    static String printHangmanArt(int wrongGuesses) {
        return switch (wrongGuesses) {
            case 0 -> """
                      +---+
                      |   |
                          |
                          |
                          |
                          |
                    =========
                    """;

            case 1 -> """
                      +---+
                      |   |
                      O   |
                          |
                          |
                          |
                    =========
                    """;

            case 2 -> """
                      +---+
                      |   |
                      O   |
                      |   |
                          |
                          |
                    =========
                    """;

            case 3 -> """
                      +---+
                      |   |
                      O   |
                     /|   |
                          |
                          |
                    =========
                    """;

            case 4 -> """
                      +---+
                      |   |
                      O   |
                     /|\\  |
                          |
                          |
                    =========
                    """;

            case 5 -> """
                      +---+
                      |   |
                      O   |
                     /|\\  |
                     /    |
                          |
                    =========
                    """;

            case 6 -> """
                      +---+
                      |   |
                      O   |
                     /|\\  |
                     / \\  |
                          |
                    =========
                    """;

            default -> "";
        };
    }
}
