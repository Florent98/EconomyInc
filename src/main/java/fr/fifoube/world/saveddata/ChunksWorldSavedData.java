/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.world.saveddata;

import fr.fifoube.main.ModEconomyInc;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;

public class ChunksWorldSavedData extends SavedData {

	public static final String DATA_NAME = ModEconomyInc.MOD_ID + "_PlotsChunkData";
	List<PlotsChunkData> listContainer = new ArrayList<PlotsChunkData>();
	
	public ChunksWorldSavedData() 
	{
	}
	
	public ChunksWorldSavedData(CompoundTag nbt) 
	{
		 /** PLOTS DATA **/
		ListTag tagListContainer = nbt.getList("listContainer", Tag.TAG_LIST);
		 List<ChunkPos> listChunk = new ArrayList<ChunkPos>();
		 for(int i = 0; i < tagListContainer.size(); i++)
		 {
			 ListTag tagList = (ListTag)tagListContainer.get(i);
			 for (int j = 0; j < tagList.size(); j++) 
			 {
				 String pos = tagList.getString(j);
				 int xPos = Integer.valueOf(pos.substring(pos.indexOf("[") + 1, pos.indexOf(",")));
				 int zPos = Integer.valueOf(pos.substring(pos.indexOf(",") + 2, pos.indexOf("]")));
				 ChunkPos chunkPos = new ChunkPos(xPos, zPos);
				 listChunk.add(chunkPos);
			 }
		    PlotsChunkData plotsData = new PlotsChunkData(listChunk);
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
	
	
	public List<PlotsChunkData> getListContainer()
	{
		return this.listContainer;
	}


}
