import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String filePath = "words.txt";

        // Read words from file
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isBlank())
                words.add(line.trim());
        }
        reader.close();

        // Pick random word
        Random rand = new Random();
        String word = words.get(rand.nextInt(words.size())).toLowerCase();

        // Initialize game state
        ArrayList<String> wordState = new ArrayList<>();
        for (int i = 0; i < word.length(); i++)
            wordState.add("_");

        int wrongGuesses = 0;
        Set<Character> guessedLetters = new HashSet<>();
        int turn = 1;
        String lastCommentary = "";

        // Welcome
        System.out.println("***********************");
        System.out.println("WELCOME TO HANGMAN GAME (FOOTBALL VERSION) ⚽");
        System.out.println("***********************");
        System.out.println("Press Enter to start...");
        scanner.nextLine();

        // Game loop
        while (wrongGuesses < 6) {
            clearScreen();

            // Display essentials
            System.out.println("***********************");
            System.out.println("TURN: " + turn + " | Wrong guesses: " + wrongGuesses + "/6");
            System.out.println("***********************\n");

            System.out.println("Word: " + String.join(" ", wordState) + "\n");
            printKeyboard(guessedLetters);

            System.out.println("Hangman:");
            System.out.println(printHangmanArt(wrongGuesses));

            if (!lastCommentary.isEmpty()) {
                System.out.println("Commentary: " + lastCommentary + "\n");
            }

            // Player input
            System.out.print("Guess a letter: ");
            String input = scanner.nextLine().toLowerCase();
            if (input.isBlank())
                continue; // skip if empty
            char userGuess = input.charAt(0);

            // Check repeated guesses
            if (guessedLetters.contains(userGuess)) {
                lastCommentary = "⚠️ You already guessed '" + userGuess + "'. Try a new letter!";
                continue;
            }
            guessedLetters.add(userGuess);

            // Correct or wrong guess
            if (word.contains(String.valueOf(userGuess))) {
                lastCommentary = "✅ " + generateCorrectCommentary(userGuess);
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == userGuess)
                        wordState.set(i, String.valueOf(userGuess));
                }
            } else {
                wrongGuesses++;
                lastCommentary = "❌ " + generateWrongCommentary(userGuess);
            }

            // Check win
            if (!wordState.contains("_")) {
                clearScreen();
                System.out.println("***********************");
                System.out.println("🎉 YOU WIN! The word was: " + word.toUpperCase() + " ⚽");
                System.out.println("***********************\n");
                System.out.println(printHangmanArt(wrongGuesses));
                break;
            }

            // Check lose
            if (wrongGuesses == 6) {
                clearScreen();
                System.out.println("***********************");
                System.out.println("⛔ FULL TIME! GAME OVER! The word was: " + word.toUpperCase() + " ⚽");
                System.out.println("***********************\n");
                System.out.println(printHangmanArt(wrongGuesses));
                break;
            }

            turn++;
            // System.out.println("Press Enter to continue...");
            // scanner.nextLine();
        }

        scanner.close();
    }

    // ------------------ FOOTBALL COMMENTARY ------------------
    static String generateCorrectCommentary(char guess) {
        String[] comments = {
                "Brilliant strike! Letter '" + guess + "' revealed! ⚽",
                "GOAL! You've uncovered a letter! 🔥",
                "What a pass! Letter '" + guess + "' is in! 🙌",
                "Perfect shot! Letter '" + guess + "' revealed!",
                "The crowd cheers! Letter '" + guess + "' appears!"
        };
        return comments[new Random().nextInt(comments.length)];
    }

    static String generateWrongCommentary(char guess) {
        String[] comments = {
                "Offside! Letter '" + guess + "' is wrong! ❌",
                "Missed! Referee blows the whistle! ⚠️",
                "Foul! Wrong guess: '" + guess + "'! 🚫",
                "Saved by the keeper! Letter '" + guess + "' is incorrect! 🧤",
                "The crowd groans! Letter '" + guess + "' isn't here!"
        };
        return comments[new Random().nextInt(comments.length)];
    }
    // ----------------------------------------------------------

    // Print keyboard with guessed letters in brackets
    static void printKeyboard(Set<Character> guessedLetters) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        System.out.print("Letters: ");
        for (char c : alphabet.toCharArray()) {
            if (guessedLetters.contains(c))
                System.out.print("[" + c + "] ");
            else
                System.out.print(c + " ");
        }
        System.out.println("\n");
    }

    // Hangman ASCII art
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

    // Clear the console (simulate fresh terminal)
    static void clearScreen() {
        for (int i = 0; i < 50; i++)
            System.out.println();
    }
}
