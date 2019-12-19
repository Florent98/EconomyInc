package fr.fifoube.world.saveddata;

import java.util.ArrayList;
import java.util.List;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

public class PlotsWorldSavedData extends WorldSavedData {

	public static final String DATA_NAME = ModEconomyInc.MOD_ID + "_PlotsData";
	List<PlotsData> listContainer = new ArrayList<PlotsData>();
	
	public PlotsWorldSavedData() 
	{
		super(DATA_NAME);
	}
	
	public PlotsWorldSavedData(String name) 
	{
		super(name);
	}

	@Override
	public void read(CompoundNBT nbt) 
	{
		 /** PLOTS DATA **/
		 ListNBT tagListContainer = nbt.getList("listContainer", NBT.TAG_LIST);
		 String name = "";
		 String owner = "";
		 int xPosFirst = 0;
		 int zPosFirst = 0;
		 int xPosSecond = 0;
		 int zPosSecond = 0;
		 int yPos = 0;
		 float price = 0;
		 boolean bought = false;
		 for(int i = 0; i < tagListContainer.size(); i++)
		 {
			ListNBT tagList = (ListNBT)tagListContainer.get(i);
		    name = tagList.getString(0);
		    owner = tagList.getString(1);
		    xPosFirst = Integer.valueOf(tagList.getString(2));
		    zPosFirst = Integer.valueOf(tagList.getString(3));
		    xPosSecond = Integer.valueOf(tagList.getString(4));
		    zPosSecond = Integer.valueOf(tagList.getString(5));
		    yPos = Integer.valueOf(tagList.getString(6));
		    price = Float.valueOf(tagList.getString(7));
		    bought = Boolean.getBoolean(tagList.getString(8));
		    PlotsData plotsData = new PlotsData(name, owner, xPosFirst, zPosFirst, xPosSecond, zPosSecond, yPos, price, bought);
			this.listContainer.add(plotsData);
		 }
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) 
	{
		/** PLOTS DATA **/
		ListNBT tagListContainer = new ListNBT(); 
		for(int i = 0; i < listContainer.size(); i++)
		{		
			ListNBT tagList = new ListNBT(); 
			for(int j = 0; j < listContainer.get(i).getList().size(); j++)
			{
		        String s = listContainer.get(i).getList().get(j);
		        if(s != null)
		        {
	                tagList.add(new StringNBT(s));
		        }
			}
	        tagListContainer.add(tagList);
		}
		compound.put("listContainer", tagListContainer);
		return compound;
	}
	
	
	public List<PlotsData> getListContainer()
	{
		return this.listContainer;
	}

	
	

}
