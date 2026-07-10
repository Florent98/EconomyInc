package fr.fifoube.main.economy;

public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw"),
    PAY_SENT("pay_sent"),
    PAY_RECEIVED("pay_received"),
    PLOT_BUY("plot_buy"),
    SELLER_BUY("seller_buy"),
    SELLER_RECOVERY("seller_recovery"),
    GOLD_CONVERT("gold_convert"),
    MOB_REWARD("mob_reward"),
    ADMIN("admin"),
    FEE("fee");

    private final String id;

    TransactionType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static TransactionType fromId(String id) {
        for (TransactionType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return ADMIN;
    }
}
