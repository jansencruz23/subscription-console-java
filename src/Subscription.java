import java.util.Date;

import interfaces.ISubscription;

public class Subscription implements ISubscription {
	
	private double subPrice;
	private Date date;
	private int subCounter;
	
	public double getSubPrice() {
		return subPrice;
	}
	
	public void setSubPrice(double subPrice) {
		this.subPrice = subPrice;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getSubCounter() {
		return subCounter;
	}
	
	public void setSubCounter(int subCounter) {
		this.subCounter = subCounter;
	}

	@Override
	public void addDuration() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateType() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewSubHis() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewSubStat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelSub() {
		// TODO Auto-generated method stub
		
	}
}