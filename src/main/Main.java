package main;
import java.time.LocalDate;
import java.util.Scanner;
import helpers.CSVHelper;
import interfaces.ISubscription;
import plans.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	private Scanner scan = new Scanner(System.in);
	private CSVHelper csvHelper = new CSVHelper();
	private ISubscription subscription = new Subscription(csvHelper);

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
   	private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);
	
	public static void main(String[] args) {
		new Main();
	}
	
	//Opening prompt - User will choose between log in and register
	public Main() {		
		System.out.println("Welcome to Subscription Management System");
		System.out.println("What would you like to do?");
		System.out.println("1 - Login");
		System.out.println("2 - Register");
		System.out.println("exit - Close Program");
		System.out.print("Enter input: ");
		var input = scan.nextLine();
		
		switch(input) {
			case "1": 
				login();
				break;
			case "2":
				register();
				break;
			case "exit":
				System.exit(0);
				break;
			default:
				System.out.println("Invalid input");
				new Main();
				break;
		}
	}
	
	//Log in prompt - Verify wether the user has existing info in the csv database or not
	private void login() {
		System.out.println("\nLog in Page\n---------------------");
		System.out.print("Enter your email: ");
		var email = scan.nextLine();
		var customer = csvHelper.getCustomer(email);
		
		if (customer == null) {
			System.out.println("Email not found!");
			login();
		}
		
		if (!customer.isSubConti()) {
			System.out.println("Your subscription is cancelled");
			new Main();
			return;
		}
		
		var subDue = customer.getSubDue();
		if (LocalDate.now().isAfter(subDue)) {
			System.out.println("Your subscription has ended");
			subscription.addDuration(customer.getCustomerId());
			promptUserLoggedIn(customer);
			return;
		}
		
		promptUserLoggedIn(customer);
	}

	//To validate wether the email input is in correct format
	public static boolean isValidEmail(String email){
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	//Register prompt - This will allow the user to create a simple account and will update the csv database
	private void register() {
		var customer = new Customer(csvHelper);
		System.out.println("\nRegistration Form\n---------------------");
		
		System.out.print("Name: ");
		customer.setCustomerName(scan.nextLine());
		
		System.out.print("Address: ");
		customer.setAddress(scan.nextLine());
		
		System.out.print("Email: ");
		String email = scan.nextLine();
		while (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please try again.");
            System.out.print("Email: ");
            email = scan.nextLine();
        }
		customer.setEmail(email);
		
		System.out.println("Subscription Type: ");
		System.out.println("1 - Standard Plan");
		System.out.println("2 - Student Plan");
		System.out.println("3 - Gold Plan");
		var plan = scan.nextLine();
		BasePlan subType = null;
		
		//The user can choose their subscription plan for the application
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
		
		//This will automatically calculate when is the end date of the subscription
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
	
	//This is a method that will allow the user to the main page and access the application features
	private void promptUserLoggedIn(Customer customer) {
		System.out.println("\nMain Menu\n---------------------\n" + "Hello " + customer.getCustomerName() + "!");
		System.out.println("What would you like to do?");
		System.out.println("1 - View Subscription Status");
		System.out.println("2 - View Subscription History");
		System.out.println("3 - Update Subscription Type");
		System.out.println("4 - Add Subscription Duration");
		System.out.println("5 - Cancel Subscription");
		System.out.println("6 - Log out");
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
		case "5":
			subscription.cancelSub(id);
			break;
		case "6":
			System.out.println("");
			new Main();
			break;
		default:
			System.out.println("Invalid input");
			promptUserLoggedIn(customer);
			break;
		}
		
		promptUserLoggedIn(customer);
	}
}