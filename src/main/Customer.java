package main;

import java.time.LocalDate;
import java.util.Date;
import helpers.CSVHelper;
import plans.BasePlan;

public class Customer {
	
	private int customerId;
	private String customerName;
	private String address;
	private String email;
	private BasePlan subType;
	private boolean subConti;
	private LocalDate subDue;
	
	private CSVHelper csvHelper;
	
	public Customer(CSVHelper csvHelper) {
		this.csvHelper = csvHelper;
	}
	
	public Customer(int customerId, String customerName, String address,
		String email, BasePlan subType, boolean subConti, LocalDate subDue, CSVHelper csvHelper) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.address = address;
		this.email = email;
		this.subType = subType;
		this.subConti = subConti;
		this.subDue = subDue;
		this.csvHelper = csvHelper;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public BasePlan getSubType() {
		return subType;
	}
	
	public void setSubType(BasePlan subType) {
		this.subType = subType;
	}
	
	public boolean isSubConti() {
		return subConti;
	}
	
	public void setSubConti(boolean subConti) {
		this.subConti = subConti;
	}
	
	public LocalDate getSubDue() {
		return subDue;
	}

	public void setSubDue(LocalDate subDue) {
		this.subDue = subDue;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public void registerCustomer(Customer customer) {
		if (csvHelper.isCustomerExisting(customer)) {
			System.out.println("Customer is already existing!");
			new Main();
		}
		csvHelper.insertCustomer(customer);
	}
}