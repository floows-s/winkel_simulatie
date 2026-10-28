package objects.stats;

public class Stats {
    // ==== variables ====
    private static int amountProductsSold      = 0;
    private static double amountMoneyEarned    = 0;

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
