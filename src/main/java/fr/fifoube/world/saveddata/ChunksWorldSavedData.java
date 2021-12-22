/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.world.saveddata;

import fr.fifoube.main.ModEconomyInc;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

import java.util.ArrayList;
import java.util.List;

public class ChunksWorldSavedData extends WorldSavedData {

    public static final String DATA_NAME = ModEconomyInc.MOD_ID + "_PlotsChunkData";
    List<PlotsChunkData> listContainer = new ArrayList<PlotsChunkData>();

    public ChunksWorldSavedData() {
        super(DATA_NAME);
    }

    public ChunksWorldSavedData(String name) {
        super(name);
    }

    @Override
    public void read(CompoundNBT nbt) {
        /** PLOTS DATA **/
        ListNBT tagListContainer = nbt.getList("listContainer", NBT.TAG_LIST);
        List<ChunkPos> listChunk = new ArrayList<ChunkPos>();
        for (int i = 0; i < tagListContainer.size(); i++) {
            ListNBT tagList = (ListNBT) tagListContainer.get(i);
            for (int j = 0; j < tagList.size(); j++) {
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
    public CompoundNBT write(CompoundNBT compound) {
        /** PLOTS DATA **/
        ListNBT tagListContainer = new ListNBT();
        for (int i = 0; i < listContainer.size(); i++) {
            ListNBT tagList = new ListNBT();
            for (int j = 0; j < listContainer.get(i).getList().size(); j++) {
                String s = listContainer.get(i).getList().get(j);
                if (s != null) {
                    tagList.add(StringNBT.valueOf(s));
                }
            }
            tagListContainer.add(tagList);
        }
        compound.put("listContainer", tagListContainer);
        return compound;
    }


    public List<PlotsChunkData> getListContainer() {
        return this.listContainer;
    }

}
