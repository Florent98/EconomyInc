/*******************************************************************************
 *******************************************************************************/

package fr.fifoube.main.capabilities;

public class MoneyHolder implements IMoney {

	private double money = 0;
	
	@Override
	public double getMoney() {
		
		return this.money;
	}

	@Override
	public void setMoney(double money) {
		
		this.money = money;
		
	}
}
