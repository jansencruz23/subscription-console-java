package main;

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
	private Date subDue;
	
	private CSVHelper csvHelper;
	
	public Customer(CSVHelper csvHelper) {
		this.csvHelper = csvHelper;
	}
	
	public Customer(int customerId, String customerName, String address,
		String email, BasePlan subType, boolean subConti, CSVHelper csvHelper) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.address = address;
		this.email = email;
		this.subType = subType;
		this.subConti = subConti;
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

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public void registerCustomer(Customer customer) {
		csvHelper.insertCustomer(customer);
	}
}