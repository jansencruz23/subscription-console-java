package plans;

public class BasePlan {

	private double price;
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getTotalPrice(int weeks) {
		return weeks * price;
	}
}