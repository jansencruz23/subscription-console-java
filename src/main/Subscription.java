package main;

import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;
import helpers.CSVHelper;
import interfaces.ISubscription;
import plans.*;

public class Subscription implements ISubscription {
	
	private Scanner scan = new Scanner(System.in);
	private double subPrice;
	private Date date;
	private int subCounter;
	
	private CSVHelper csvHelper;
	
	public Subscription(CSVHelper csvHelper) {
		this.csvHelper = csvHelper;
	}
	
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
	public void addDuration(int id) {
		var customer = csvHelper.getCustomer(id);
		
		System.out.println("Add duration to your Subscription:");
		System.out.println("1 - 1 week");
		System.out.println("2 - 1 month");
		System.out.println("3 - 1 year");
		System.out.print("Enter input: ");
		var input = scan.nextLine();
		
		var previousDue = customer.getSubDue();
		LocalDate currentDue = previousDue;
		
		switch (input) {
		case "1": 
			currentDue = previousDue.plusWeeks(1);
			break;
		case "2":
			currentDue = previousDue.plusMonths(1);
			break;
		case "3":
			currentDue = previousDue.plusYears(1);
			break;
		default:
			System.out.println("Invalid input");
			addDuration(id);
			break;
		}		
		
		customer.setCustomerId(id);
		customer.setSubDue(currentDue);
		csvHelper.updateCustomer(customer);
	}

	@Override
	public void updateType(int id) {
		var customer = csvHelper.getCustomer(id);
		
		System.out.println("Update your Subscription Type:");
		System.out.println("1 - Standard Plan");
		System.out.println("2 - Student Plan");
		System.out.println("3 - Gold Plan");
		System.out.print("Enter input: ");
		
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
			updateType(id);
			break;
		}
		
		customer.setCustomerId(id);
		customer.setSubType(subType);
		csvHelper.updateCustomer(customer);
	}

	@Override
	public void viewSubHis(int id) {
		var customer = csvHelper.getCustomer(id);
		var customers = csvHelper.getHistory(id);
		System.out.println("Name: " + customer.getCustomerName());
		System.out.println("Email: " + customer.getEmail());
		
		for (var history : customers) {
			System.out.println("Subscription Type: " + history.getSubType().getClass().getSimpleName() + " -- "
					+ "Subscription Due: " + history.getSubDue());
		}
	}

	@Override
	public void viewSubStat(int id) {
		var customer = csvHelper.getCustomer(id);
		
		System.out.println("Name: " + customer.getCustomerName());
		System.out.println("Email: " + customer.getEmail());
		System.out.println("Subscription Type: " + customer.getSubType().getClass().getSimpleName());
		System.out.println("Subscription Due: " + customer.getSubDue());
	}

	@Override
	public void cancelSub(int id) {
		var customer = csvHelper.getCustomer(id);
		
		customer.setSubConti(false);
		csvHelper.updateCustomer(customer);
		new Main();
	}
}