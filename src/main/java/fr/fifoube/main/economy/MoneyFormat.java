package fr.fifoube.main.economy;

public final class MoneyFormat {

    private MoneyFormat() {
    }

    public static long toWhole(double amount) {
        return Math.round(amount);
    }

    public static long toWholeFloor(double amount) {
        return (long) Math.floor(amount);
    }

    public static String display(double amount) {
        return String.valueOf(toWhole(amount));
    }
}
