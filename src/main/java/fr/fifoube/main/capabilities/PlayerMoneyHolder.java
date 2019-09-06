package fr.fifoube.main.capabilities;

import fr.fifoube.packets.PacketMoneyData;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.network.PacketDistributor;

public class PlayerMoneyHolder extends MoneyHolder {

	private EntityPlayerMP player;
	
    public PlayerMoneyHolder(EntityPlayerMP player)
    {
        this.player = player;
    }
  
    @Override
    public void setMoney(double money) {
    	// TODO Auto-generated method stub
    	super.setMoney(money);
    	if (player.connection != null)
    	{
    		player.getCapability(CapabilityMoney.MONEY_CAPABILITY)
    		.ifPresent(capa -> PacketsRegistery.CHANNEL.send(
    				PacketDistributor.PLAYER.with(() -> this.player),
    				new PacketMoneyData(capa))
    				);
    	}
    }
    
    @Override
    public void setLinked(boolean linked) {
    	// TODO Auto-generated method stub
    	super.setLinked(linked);
    	if (player.connection != null)
    	{
    		player.getCapability(CapabilityMoney.MONEY_CAPABILITY)
    		.ifPresent(capa -> PacketsRegistery.CHANNEL.send(
    				PacketDistributor.PLAYER.with(() -> this.player),
    				new PacketMoneyData(capa))
    				);
    	}
    }
    @Override
    public void setName(String name) {
    	// TODO Auto-generated method stub
    	super.setName(name);
    	if (player.connection != null)
    	{
    		player.getCapability(CapabilityMoney.MONEY_CAPABILITY)
    		.ifPresent(capa -> PacketsRegistery.CHANNEL.send(
    				PacketDistributor.PLAYER.with(() -> this.player),
    				new PacketMoneyData(capa))
    				);
    	}
    }
    @Override
    public void setOnlineUUID(String onUUID) {
    	// TODO Auto-generated method stub
    	super.setOnlineUUID(onUUID);
    	if (player.connection != null)
    	{
    		player.getCapability(CapabilityMoney.MONEY_CAPABILITY)
    		.ifPresent(capa -> PacketsRegistery.CHANNEL.send(
    				PacketDistributor.PLAYER.with(() -> this.player),
    				new PacketMoneyData(capa))
    				);
    	}
    }

    
}
