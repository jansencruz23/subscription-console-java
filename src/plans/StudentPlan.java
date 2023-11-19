package plans;

public class StudentPlan extends BasePlan {
	
	public StudentPlan() {
		setPrice(120);
	}
	
	@Override
	public double getTotalPrice(int weeks) {
		return super.getTotalPrice(weeks);
	}
}
