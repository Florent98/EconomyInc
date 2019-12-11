package fr.fifoube.world.saveddata;

import java.util.ArrayList;
import java.util.List;

public class PlotsData {

	public String name = "";
	public String owner = "";
	public int xPosFirst;
	public int zPosFirst;
	public int xPosSecond;
	public int zPosSecond;
	public int yPos;
	public double price;
	public boolean bought;
	
	public PlotsData(String nameIn, String ownerIn, int xPosFirstIn, int zPosFirstIn, int xPosSecondIn, int zPosSecondIn, int yPosIn, double priceIn, boolean boughtIn) 
	{
		this.name = nameIn;
		this.owner = ownerIn;
		this.xPosFirst = xPosFirstIn;
		this.zPosFirst = zPosFirstIn;
		this.xPosSecond = xPosSecondIn;
		this.zPosSecond = zPosSecondIn;
		this.yPos = yPosIn;
		this.price = priceIn;
		this.bought = boughtIn;
	}

	public List<String> getList()
	{
		List<String> list = new ArrayList<String>();
		list.add(this.name);
		list.add(this.owner);
		list.add(String.valueOf(this.xPosFirst));
		list.add(String.valueOf(this.zPosFirst));
		list.add(String.valueOf(this.xPosSecond));
		list.add(String.valueOf(this.zPosSecond));
		list.add(String.valueOf(this.yPos));
		list.add(String.valueOf(this.price));
		list.add(String.valueOf(this.bought));
		return list;
	}
	
	public boolean getBought()
	{
		return this.bought;
	}
}
