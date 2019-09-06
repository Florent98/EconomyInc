package fr.fifoube.main.capabilities;


public class MoneyHolder implements IMoney {

	private double money;
	private boolean linked = false;
	private String name = "";
	private String onlineUUID = "";
 
    
	@Override
	public double getMoney() {
		// TODO Auto-generated method stub
		return this.money;
	}

	@Override
	public void setMoney(double money) {
		this.money = money;
		
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
		// TODO Auto-generated method stub
		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	public String getOnlineUUID() {
		// TODO Auto-generated method stub
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
