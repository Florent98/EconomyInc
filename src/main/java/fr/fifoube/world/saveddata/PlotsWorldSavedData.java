/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.world.saveddata;

import fr.fifoube.main.ModEconomyInc;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;

public class PlotsWorldSavedData extends SavedData {

	public static final String DATA_NAME = ModEconomyInc.MOD_ID + "_PlotsData";
	List<PlotsData> listContainer = new ArrayList<PlotsData>();
	
	public PlotsWorldSavedData() 
	{
	}
	
	public PlotsWorldSavedData(CompoundTag nbt) 
	{
		 ListTag tagListContainer = nbt.getList("listContainer", Tag.TAG_LIST);
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
			 ListTag tagList = (ListTag)tagListContainer.get(i);
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
	public CompoundTag save(CompoundTag compound) {
		/** PLOTS DATA **/
		ListTag tagListContainer = new ListTag(); 
		for(int i = 0; i < listContainer.size(); i++)
		{		
			ListTag tagList = new ListTag(); 
			for(int j = 0; j < listContainer.get(i).getList().size(); j++)
			{
		        String s = listContainer.get(i).getList().get(j);
		        if(s != null)
		        {
	                tagList.add(StringTag.valueOf(s));
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
