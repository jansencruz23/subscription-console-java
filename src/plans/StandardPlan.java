package plans;

public class StandardPlan extends GoldPlan {
	
	public StandardPlan() {
		setPrice(150);
	}
	
	@Override
	public double getTotalPrice(int weeks) {
		return super.getTotalPrice(weeks);
	}
}