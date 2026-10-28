package objects.stats;

import core.Simulation;
import core.Updatable;
import objects.FloatingText.FloatingNumber;

public class Stats  {
    // ==== variables ====
    private static int amountProductsSold      = 0;
    private static double amountMoneyEarned    = 0;

    // needs to be a float to be used correctly
    private float xTextLocation = 1153;
    private float yTextLocation2 = 72;
    private float yTextLocation1 = 42;


    // ==== constructor ====
    public static void IncrementProductSold(int amount){
        amountProductsSold += amount;
    }

    public static void IncrementMoneyEarned(double amount){
        amountMoneyEarned += amount;
    }

    public static int GetProductsSold() {
        return amountProductsSold;
    }

    public static double GetMoneyEarned() {
        return amountMoneyEarned;
    }

}
