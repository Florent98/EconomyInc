package fr.fifoube.main.capabilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyHolder implements IMoney {

	private double money;
	private boolean linked = false;
	private String name = "";
	private String onlineUUID = "";
 
    
	@Override
	public double getMoney() {
		return this.money;
	}

	@Override
	public void setMoney(double money) {
		this.money = round(money, 2);
		
	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	@Override
	public boolean getLinked() {
		return linked;
	}

	@Override
	public void setLinked(boolean linked) {

		this.linked = linked;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	public String getOnlineUUID() {
		return this.onlineUUID;
	}

	@Override
	public void setOnlineUUID(String onUUID) {

		this.onlineUUID = onUUID;
	}
	
	@Override 
	public void setSyncValues(double money, boolean linked, String name, String onUUID)
	{
		this.money = money;
		this.linked = linked;
		this.name = name;
		this.onlineUUID = onUUID;
	}

	
}
