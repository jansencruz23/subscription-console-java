package main;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;
import helpers.CSVHelper;
import plans.*;

public class Main {

	private Scanner scan = new Scanner(System.in);
	private CSVHelper csvHelper = new CSVHelper();
	private Subscription subscription = new Subscription(csvHelper);
	
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
		System.out.print("Enter your email: ");
		var email = scan.nextLine();
		var customer = csvHelper.getCustomer(email);
		
		if (customer == null) {
			System.out.println("Email not found!");
			login();
		}
		
		promptUserLoggedIn(customer);
	}
	
	private void register() {
		var customer = new Customer(csvHelper);
		
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
		
		System.out.println("Subscription Time: ");
		System.out.println("1 - 1 week");
		System.out.println("2 - 1 month");
		System.out.println("3 - 1 year");
		var date = scan.nextLine();
		LocalDate currentDate = LocalDate.now();
		LocalDate subDue = null;
		
		switch(date) {
		case "1":
			subDue = currentDate.plusWeeks(1);
			break;
		case "2":
			subDue = currentDate.plusMonths(1);
			break;
		case "3":
			subDue = currentDate.plusYears(1);
			break;
		default:
			System.out.println("Invalid input");
			register();
			break;
		}
		
		customer.setSubType(subType);
		customer.setSubConti(true);
		customer.setCustomerId(csvHelper.getLastCustomerId());
		customer.setSubDue(subDue);
		customer.registerCustomer(customer);
		
		promptUserLoggedIn(customer);
	}
	
	private void promptUserLoggedIn(Customer customer) {
		System.out.println("Hello " + customer.getCustomerName() + "!");
		System.out.println("What would you like to do?");
		System.out.println("1 - View Subscription Status");
		System.out.println("2 - View Subscription History");
		System.out.println("3 - Update Subscription Type");
		System.out.println("4 - Add Subscription Duration");
		System.out.print("Enter input: ");
		var input = scan.nextLine();
		
		var id = customer.getCustomerId();
		
		switch (input) {
		case "1":
			subscription.viewSubStat(id);
			break;
		case "2":
			subscription.viewSubHis(id);
			break;
		case "3":
			subscription.updateType(id);
			break;
		case "4":
			subscription.addDuration(id);
			break;
		default:
			System.out.println("Invalid input");
			promptUserLoggedIn(customer);
			break;
		}
		
		promptUserLoggedIn(customer);
	}
}