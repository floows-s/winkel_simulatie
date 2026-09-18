package utilities;

import java.util.Random;

public class Randomizer {
    private static Random random = new Random();

    public static int nextInt(){
        return random.nextInt();
    }

    public static int nextInt(int origin, int bounds){
        return random.nextInt(origin, bounds);
    }
}
