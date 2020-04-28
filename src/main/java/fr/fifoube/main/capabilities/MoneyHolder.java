/*******************************************************************************
 *******************************************************************************/

package fr.fifoube.main.capabilities;

public class MoneyHolder implements IMoney {

	private double money = 0;
	private boolean linked = false;
	
	@Override
	public double getMoney() {
		
		return this.money;
	}

	@Override
	public void setMoney(double money) {
		
		this.money = money;
		
	}

	@Override
	public boolean getLinked() {
		
		return this.linked;
	}

	@Override
	public void setLinked(boolean linked) {
		
		this.linked = linked;
		
	}
	
	public void setNewCapaData(double money, boolean linked)
	{
		this.money = money;
		this.linked = linked;
	}

}
