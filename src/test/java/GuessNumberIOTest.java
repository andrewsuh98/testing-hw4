import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class GuessNumberIOTest {

    private final String INTRO = "A number is chosen between 1 to 100. Guess the number within 5 trials.\n";
    private final String GUESS = "Guess the number: ";
    private final String CORRECT = "Congratulations! You guessed the number.\n";
    String output;

    private String lessThan(int x) {
        return String.format("The number is less than %d\n", x);
    }

    private String greaterThan(int x) {
        return String.format("The number is greater than %d\n", x);
    }

    private String incorrect(int answer) {
        return String.format("You have exhausted 5 trials.\nThe number was %d\n", answer);
    }

    public void executeGame(int[] input, int seed) {
        // setup input channel
        String inputString = String.format("%d\n%d\n%d\n%d\n%d\n", input[0], input[1], input[2], input[3], input[4]);
        byte[] data = inputString.getBytes(StandardCharsets.UTF_8);
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data));

        // setup output channel
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(out);

        // redirect the standard channels
        System.setIn(in);
        System.setOut(outputStream);

        // call the guessingNumberGame
        GuessNumber.guessingNumberGame(new Random(seed));

        // get output and put it in String
        output = out.toString();
    }

    @AfterEach
    public void tearDown() {
        // redirect channels back
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @ParameterizedTest
    @CsvSource({"0, 61", "1, 86", "2, 9", "3, 35", "4, 63", "5, 88", "6, 12", "7, 37", "8, 65", "9, 90"})
    public void testIncorrectAnswerIsGreaterThan(int seed, int answer) {
        int[] input = {answer - 20, answer - 10, answer - 5, answer - 2, answer - 1};
        String expectedOutput = String.format("%s%s%s%s%s%s%s%s%s%s%s%s",
                INTRO,
                GUESS, greaterThan(input[0]),
                GUESS, greaterThan(input[1]),
                GUESS, greaterThan(input[2]),
                GUESS, greaterThan(input[3]),
                GUESS, greaterThan(input[4]),
                incorrect(answer));

        executeGame(input, seed);

        assertEquals(expectedOutput, output);
    }

    /**
     * Test is failing.
     *
     * When the last guess is less than the answer,
     * the method does not output "The number is less than X".
     */
    @ParameterizedTest
    @CsvSource({"0, 61", "1, 86", "2, 9", "3, 35", "4, 63", "5, 88", "6, 12", "7, 37", "8, 65", "9, 90"})
    public void testIncorrectAnswerIsLessThan(int seed, int answer) {
        int[] input = {answer + 20, answer + 10, answer + 5, answer + 2, answer + 1};
        String expectedOutput = String.format("%s%s%s%s%s%s%s%s%s%s%s%s",
                INTRO,
                GUESS, lessThan(input[0]),
                GUESS, lessThan(input[1]),
                GUESS, lessThan(input[2]),
                GUESS, lessThan(input[3]),
                GUESS, lessThan(input[4]),
                incorrect(answer));

        executeGame(input, seed);

        assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @CsvSource({"0, 61", "1, 86", "2, 9", "3, 35", "4, 63", "5, 88", "6, 12", "7, 37", "8, 65", "9, 90"})
    public void testCorrectFirstTry(int seed, int answer) {
        int[] input = {answer, -1, -1, -1, -1};
        String expectedOutput = String.format("%s%s%s",
                INTRO,
                GUESS,
                CORRECT);

        executeGame(input, seed);

        assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @CsvSource({"0, 61", "1, 86", "2, 9", "3, 35", "4, 63", "5, 88", "6, 12", "7, 37", "8, 65", "9, 90"})
    public void testCorrectSecondTry(int seed, int answer) {
        int[] input = {-1, answer, -1, -1, -1};
        String expectedOutput = String.format("%s%s%s%s%s",
                INTRO,
                GUESS, greaterThan(input[0]),
                GUESS,
                CORRECT);

        executeGame(input, seed);

        assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @CsvSource({"0, 61", "1, 86", "2, 9", "3, 35", "4, 63", "5, 88", "6, 12", "7, 37", "8, 65", "9, 90"})
    public void testCorrectThirdTry(int seed, int answer) {
        int[] input = {-1, -1, answer, -1, -1};
        String expectedOutput = String.format("%s%s%s%s%s%s%s",
                INTRO,
                GUESS, greaterThan(input[0]),
                GUESS, greaterThan(input[1]),
                GUESS,
                CORRECT);

        executeGame(input, seed);

        assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @CsvSource({"0, 61", "1, 86", "2, 9", "3, 35", "4, 63", "5, 88", "6, 12", "7, 37", "8, 65", "9, 90"})
    public void testCorrectFourthTry(int seed, int answer) {
        int[] input = {-1, -1, -1, answer, -1};
        String expectedOutput = String.format("%s%s%s%s%s%s%s%s%s",
                INTRO,
                GUESS, greaterThan(input[0]),
                GUESS, greaterThan(input[1]),
                GUESS, greaterThan(input[2]),
                GUESS,
                CORRECT);

        executeGame(input, seed);

        assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @CsvSource({"0, 61", "1, 86", "2, 9", "3, 35", "4, 63", "5, 88", "6, 12", "7, 37", "8, 65", "9, 90"})
    public void testCorrectFifthTry(int seed, int answer) {
        int[] input = {-1, -1, -1, -1, answer};
        String expectedOutput = String.format("%s%s%s%s%s%s%s%s%s%s%s",
                INTRO,
                GUESS, greaterThan(input[0]),
                GUESS, greaterThan(input[1]),
                GUESS, greaterThan(input[2]),
                GUESS, greaterThan(input[3]),
                GUESS,
                CORRECT);

        executeGame(input, seed);

        assertEquals(expectedOutput, output);
    }

    /**
     * Test is failing.
     *
     * When the guess alternates between less than and greater than the answer,
     * the method get fixed onto "The number is less than X".
     * Even when the guess is less than the answer,
     * it does not output "The number is greater then X".
     */
    @ParameterizedTest
    @CsvSource({"0, 61", "1, 86", "2, 9", "3, 35", "4, 63", "5, 88", "6, 12", "7, 37", "8, 65", "9, 90"})
    public void testLessThanAndGreaterThan(int seed, int answer) {
        int[] input = {answer + 5, answer - 5, answer + 1, answer - 1, answer};
        String expectedOutput = String.format("%s%s%s%s%s%s%s%s%s%s%s",
                INTRO,
                GUESS, lessThan(input[0]),
                GUESS, greaterThan(input[1]),
                GUESS, lessThan(input[2]),
                GUESS, greaterThan(input[3]),
                GUESS,
                CORRECT);

        executeGame(input, answer);

        assertEquals(expectedOutput, output);
    }
}
