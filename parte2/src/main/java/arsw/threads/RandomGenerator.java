package arsw.threads;

import java.util.Random;

public class RandomGenerator {

    private static final Random random = new Random(System.currentTimeMillis());

    public static int nextInt(int max) {
        return random.nextInt(max);
    }

}
