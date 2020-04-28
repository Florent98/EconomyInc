/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.world.saveddata;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.math.ChunkPos;

public class PlotsChunkData {
	
	public List<ChunkPos> listChunk = new ArrayList<ChunkPos>();
	
	public PlotsChunkData(List<ChunkPos> chunkIn) 
	{
		for (int i = 0; i < chunkIn.size(); i++) 
		{
			this.listChunk.add(chunkIn.get(i));
		}
	}

	public List<String> getList()
	{
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < this.listChunk.size(); i++) 
		{
			list.add(String.valueOf(this.listChunk.get(i)));
		}
		return list;
	}
	
}
