import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Test {



    public static void main(String[] args) {

        final int SEED = 0;

        // setup random
        Random random = new Random(SEED);

        // setup input channel
        String inputString = String.format("%d\n%d\n%d\n%d\n%d\n", -1, -1, -1, -1, -1);
        final byte[] data = inputString.getBytes(StandardCharsets.UTF_8);
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data));

        // redirect the standard channels
        System.setIn(in);

        // call the guessingNumberGame
        int answer = GuessNumber.guessingNumberGame(random);

        System.out.println("answer: " + answer);

        in = new BufferedInputStream(new ByteArrayInputStream(data));
        System.setIn(in);
        answer = GuessNumber.guessingNumberGame(random);

        System.out.println("answer: " + answer);
    }
}
