package fr.fifoube.main.capabilities;

import fr.fifoube.packets.PacketMoneyData;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class PlayerMoneyHolder extends MoneyHolder {

    private ServerPlayer player;

    public PlayerMoneyHolder(ServerPlayer player) {
        this.player = player;
    }

    @Override
    public void setMoney(double money) {

        super.setMoney(money);
        if (player.connection != null)
        {
            player.getCapability(CapabilityMoney.MONEY_CAPABILITY)
                    .ifPresent(capa -> {
                        PacketsRegistery.CHANNEL.send(PacketDistributor.PLAYER.with(() -> this.player), new PacketMoneyData(capa.getMoney()));
                    });
        }
    }

    @Override
    public void addMoney(double moneyToAdd) {
        super.addMoney(moneyToAdd);
        if (player.connection != null)
        {
            player.getCapability(CapabilityMoney.MONEY_CAPABILITY)
                    .ifPresent(capa -> {
                        PacketsRegistery.CHANNEL.send(PacketDistributor.PLAYER.with(() -> this.player), new PacketMoneyData(capa.getMoney()));
                    });
        }
    }
}