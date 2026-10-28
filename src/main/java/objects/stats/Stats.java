package objects.stats;

import core.Simulation;
import core.Updatable;
import objects.FloatingText.FloatingNumber;

public class Stats  {
    // ==== variables ====
    private static int amountProductsSold      = 0;
    private static double amountMoneyEarned    = 0;



    /// adds or subtracts to the total amount of products sold
    /// @param amount   is a number, negative to remove
    public static void IncrementProductSold(int amount){
        amountProductsSold += amount;
    }

    /// increases or decreases the amount of money earned total
    /// @param amount   is the amount of money earned or spend
    public static void IncrementMoneyEarned(double amount){
        amountMoneyEarned += amount;
    }

    ///
    /// @return the amount of products sold
    public static int GetProductsSold() {
        return amountProductsSold;
    }

    ///
    /// @return the amount of money earned so far
    public static double GetMoneyEarned() {
        return amountMoneyEarned;
    }

}
