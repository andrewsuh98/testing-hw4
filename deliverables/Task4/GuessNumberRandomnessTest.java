import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class GuessNumberRandomnessTest {

    private final int SEED = 0;
    private final int REPETITIONS = 10000;
    private final double RANGE = 0.20;
    private final int CEILING = (int) ((REPETITIONS / 100) * (1 + RANGE));
    private final int FLOOR = (int) ((REPETITIONS / 100) * (1 - RANGE));
    final String inputString = String.format("%d\n%d\n%d\n%d\n%d\n", -1, -1, -1, -1, -1);
    final byte[] data = inputString.getBytes(StandardCharsets.UTF_8);
    BufferedInputStream in;

    @AfterAll
    public static void tearDown() {
        System.setOut(System.out);
    }

    @Test
    public void testDistribution() {
        Random random = new Random(SEED);
        int[] reps = new int[100];

        for (int i = 0; i < REPETITIONS; i++) {
            in = new BufferedInputStream(new ByteArrayInputStream(data));
            System.setIn(in);
            int answer = GuessNumber.guessingNumberGame(random);

            assertTrue(answer >= 1 && answer <= 100);

            reps[answer - 1]++;
        }

        for (int i = 0; i < reps.length; i++) {
            assertTrue(reps[i] >= FLOOR && reps[i] <= CEILING);
        }
    }
}
