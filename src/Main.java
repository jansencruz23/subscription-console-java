import java.util.Scanner;
import plans.*;

public class Main {

	private Scanner scan = new Scanner(System.in);
	private Subscription subscription = new Subscription();
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {		
		System.out.println("Welcome to Subscription Management System");
		System.out.println("What would you like to do?");
		System.out.println("1 - Login");
		System.out.println("2 - Register");
		System.out.print("Enter input: ");
		var input = scan.nextLine();
		
		switch(input) {
			case "1": 
				login();
				break;
			case "2":
				register();
				break;
			default:
				System.out.println("Invalid input");
				new Main();
				break;
		}
	}
	
	private void login() {
		
	}
	
	private void register() {
		var customer = new Customer();
		
		System.out.print("Name: ");
		customer.setCustomerName(scan.nextLine());
		
		System.out.print("Address: ");
		customer.setAddress(scan.nextLine());
		
		System.out.print("Email: ");
		customer.setEmail(scan.nextLine());
		
		System.out.println("Subscription Type: ");
		System.out.println("1 - Standard Plan");
		System.out.println("2 - Student Plan");
		System.out.println("3 - Gold Plan");
		var plan = scan.nextLine();
		BasePlan subType = null;
		
		switch(plan) {
		case "1":
			subType = new StandardPlan();
			break;
		case "2":
			subType = new StudentPlan();
			break;
		case "3":
			subType = new GoldPlan();
			break;
		default:
			System.out.println("Invalid input");
			register();
			break;
		}
		customer.setSubType(subType);
		customer.setSubConti(true);
		
		customer.registerCustomer(customer);
	}
}