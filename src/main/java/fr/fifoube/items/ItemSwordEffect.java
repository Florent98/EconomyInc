package fr.fifoube.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;

public class ItemSwordEffect extends ItemSword {

	public ItemSwordEffect(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
			
		if(entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityLiving = (EntityLivingBase)entity;
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 3600, 1, true, true));
			return true;
		}
		return false;	
	}
	

}
