package utilities;

import java.util.Random;

public class Randomizer {
    private static final Random random = new Random();

    public static int nextInt(){
        return random.nextInt();
    }

    public static int nextInt(int origin, int bounds){
        return random.nextInt(origin, bounds);
    }

    public static boolean nextBoolean(){
        return random.nextInt(0, 2) == 1;
    }

    public static boolean nextBoolean(float percentageChangeOnTrue){
        if(percentageChangeOnTrue >= 100) return true;
        if(percentageChangeOnTrue <= 0) return false;

        float result = random.nextFloat(0, 101);
        return result <= percentageChangeOnTrue;
    }
}
