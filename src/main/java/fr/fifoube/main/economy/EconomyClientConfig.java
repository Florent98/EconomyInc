package fr.fifoube.main.economy;

import fr.fifoube.main.config.ConfigFile;

public record EconomyClientConfig(
        boolean enableAtmWithdrawFee,
        double atmWithdrawFeePercent,
        double atmConfirmThreshold
) {

    public static EconomyClientConfig defaults() {
        return fromServer();
    }

    public static EconomyClientConfig fromServer() {
        return new EconomyClientConfig(
                ConfigFile.enableAtmWithdrawFee,
                ConfigFile.atmWithdrawFeePercent,
                ConfigFile.atmConfirmThreshold
        );
    }

    public boolean areAtmFeesEnabled() {
        return enableAtmWithdrawFee && atmWithdrawFeePercent > 0;
    }

    public double effectiveAtmWithdrawFeePercent() {
        return areAtmFeesEnabled() ? atmWithdrawFeePercent : 0.0;
    }

    public long calculateFee(long amount) {
        if (!areAtmFeesEnabled()) {
            return 0;
        }
        return MoneyFormat.toWhole(amount * atmWithdrawFeePercent / 100.0);
    }

    public long maxWithdrawableBalance(long balance) {
        if (!areAtmFeesEnabled()) {
            return balance;
        }
        double divisor = 1.0 + atmWithdrawFeePercent / 100.0;
        return (long) Math.floor(balance / divisor);
    }
}
