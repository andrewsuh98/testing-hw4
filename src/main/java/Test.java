import java.util.Random;

public class Test {



    public static void main(String[] args) {

        final int SEED = 0;

        Random random = new Random(SEED);
        int answer = GuessNumber.guessingNumberGame(random);
    }
}
