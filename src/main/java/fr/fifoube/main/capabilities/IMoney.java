package fr.fifoube.main.capabilities;


public interface IMoney {

	double getMoney();
	void setMoney(double money);
	boolean getLinked();
	void setLinked(boolean linked);
	String getName();
	void setName(String name);
	String getOnlineUUID();
	void setOnlineUUID(String onUUID);
	
	void setSyncValues(double money, boolean linked, String name, String onUUID);
}
