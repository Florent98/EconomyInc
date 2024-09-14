/** 
 *  Copyright 2020, Turrioni Florent, All rights reserved.
 *  
 * 	This program is copyrighted for all the files and code 
 * 	included in this program. No reuse, modification or 
 * 	reselling is authorized without any legal document 
 *  approved by the owner*.
 * 
 * 	*Owner : Turrioni Florent resident in Belgium and 
 *  contactable at florent_turrioni@hotmail.com
 *  
 * */

package fr.fifoube.blocks.blockentity;

import fr.fifoube.gui.container.MenuChanger;
import fr.fifoube.items.ItemsRegistery;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.config.ConfigFile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class BlockEntityChanger extends BlockEntity implements MenuProvider{

	public static MutableComponent NAME = Component.translatable("block.economyinc.block_changer");
	private byte direction;
	public int numbUse;
	public Player user;
	public int timeProcess = ConfigFile.goldChangerDuration;
	public int timePassed = 0;
	public boolean isProcessing;
    protected final ItemStackHandler inventory;
    
    protected BlockEntityChanger(BlockPos pos, BlockState state)
    {
        super(BlockEntityTypeRegistery.TILE_CHANGER.get(), pos, state);
        this.inventory = new ItemStackHandler(3) {
        	@Override
        	protected void onContentsChanged(int slot) {
        		setChanged();
        	}
        };
    }
    
    public ItemStackHandler getInventory() {
    	return this.inventory;
    }

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public CompoundTag getUpdateTag() {
		
		return this.saveWithFullMetadata();
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		
		this.load(pkt.getTag());
	}
		
	public int getNumbUse()
	{
		return this.numbUse;
	}
	
	public void setNumbUse(int numbUse)
	{
		this.numbUse = numbUse;
	}
	
	public Player getEntityPlayer()
	{
		return this.user;
	}
	
	public void setEntityPlayer(Player currentUser)
	{
		this.user = currentUser;
	}

    public byte getDirection()
    {
    	return this.direction;
    }

	public void setDirection(byte direction) 
	{
		this.direction = direction;
	}
	
	public void setTimePassed(int value)
	{
		this.timePassed = value;
	}
	public int getTimePassed()
	{
		return this.timePassed;
	}
	
	public void setIsProcessing(boolean value)
	{
		this.isProcessing = value;
	}
	public boolean getIsProcessing()
	{
		return this.isProcessing;
	}
	
	public void setProcessTime(int value)
	{
		this.timeProcess = value;
	}
	public int getProcessTime()
	{
		return this.timeProcess;
	}
	
	@Override
	protected void saveAdditional(CompoundTag compound) {
		
		super.saveAdditional(compound);
		compound.put("inventory", inventory.serializeNBT());
		compound.putInt("numbUse", this.numbUse);
		compound.putBoolean("isProcessing", this.isProcessing);
		compound.putInt("timePassed", this.timePassed);
	}
	
	@Override
	public void load(CompoundTag compound) {
		
		super.load(compound);
		inventory.deserializeNBT((CompoundTag)compound.get("inventory"));
		this.numbUse = compound.getInt("numbUse");
		this.isProcessing = compound.getBoolean("isProcessing");
		this.timePassed = compound.getInt("timePassed");
	}
	
	public static void serverTicker(Level level, BlockPos pos, BlockState state, BlockEntityChanger changer) {
		
			ItemStack slot0 = changer.inventory.getStackInSlot(0);
			ItemStack slot1 = changer.inventory.getStackInSlot(1);
			ItemStack slot2 = changer.inventory.getStackInSlot(2);
			if(slot0 != null && slot1 != null && slot2 != null)
			if(!level.isClientSide && slot0.getItem() == ItemsRegistery.GOLDNUGGETSUB.get())
			{
					if(slot1.getItem() == ItemsRegistery.CREDITCARD.get())
					{
						 if(slot1.hasTag() && changer.getEntityPlayer() != null)
						    {
					        	String nameCard = slot1.getTag().getString("OwnerUUID");
								String nameGame =  changer.getEntityPlayer().getStringUUID();
								if(nameCard.equals(nameGame))
								{
									if(slot2.isEmpty())
									{
										if(changer.timePassed == 0)
										{
											String w =  String.valueOf(level.getRandom().nextDouble()).substring(0,4);
											if(slot0.hasTag())
											{
												if(!slot0.getTag().contains("weight"))
												{
													slot0.getTag().putString("weight", w);
												}
											}
											else
											{
												slot0.getOrCreateTag().putString("weight", w);
											}
										}
										if(changer.timePassed == changer.timeProcess)
										{
											Player playerIn = changer.user;
											if(playerIn != null)
											playerIn.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
												double fundsPrev = data.getMoney();
												String weight = slot0.getTag().getString("weight");
												double fundsNow = (fundsPrev + (Double.parseDouble(weight) * ConfigFile.multiplierGoldNuggetWeight));
												data.setMoney(fundsNow);
												slot0.split(1);
												ItemStack copyOfCard = slot1.copy();
												slot1.split(1);
												changer.getInventory().insertItem(2, copyOfCard, false);
												changer.timePassed = 0;
												changer.isProcessing = false;
												changer.setChanged();
												ModEconomyInc.LOGGER_MONEY.info(playerIn.getDisplayName().getString() + " has changed gold with the weight ("+ weight +"), the change was at " + (Double.parseDouble(weight) * ConfigFile.multiplierGoldNuggetWeight) + ". Balance was at " + fundsPrev + ", balance is now " + data.getMoney() + "." + "[UUID: " + playerIn.getStringUUID() + "," + changer.getBlockPos() + "]");
											});

										}
										else
										{
											++changer.timePassed;
											changer.isProcessing = true;
											changer.setChanged();
										}
									}
								}
						    }
						}
			}
		
			if(slot0.getItem() == Items.AIR || slot1.getItem() == Items.AIR)
			{
					changer.timePassed = 0;	
					changer.isProcessing = false;
					changer.setChanged();
			}
		}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
		return new MenuChanger(id, inv, this);
	}

	@Override
	public Component getDisplayName() {
		
		return this.NAME;
	}
	
	
}
