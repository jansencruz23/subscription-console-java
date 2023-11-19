import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import plans.BasePlan;

public class Customer {
	
	private int customerId;
	private String customerName;
	private String address;
	private String email;
	private BasePlan subType;
	private boolean subConti;
	
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
		String filePath = "D:\\User\\Jansen\\Self Study\\2023 - 11 - NOVEMBER\\Java\\Subscription Management System\\customers.csv"; // Define your file path here

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(formatCustomerToCSV(customer));
            System.out.println("user registered successfully");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private String formatCustomerToCSV(Customer customer) {
        return customer.getLastCustomerId() + ","
                + customer.getCustomerName() + ","
                + customer.getAddress() + ","
                + customer.getEmail() + ","
                + customer.getSubType().getClass().getSimpleName() + ","
                + customer.isSubConti() + "\n";
    }
	
	private int getLastCustomerId() {
        int lastId = 0;
        String filePath = "D:\\User\\Jansen\\Self Study\\2023 - 11 - NOVEMBER\\Java\\Subscription Management System\\customers.csv"; // Define your file path here

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if (parts[0].equals("customerId")) {
                	continue;
                }
                
                int id = Integer.parseInt(parts[0]);
                
                if (id > lastId) {
                    lastId = id;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return lastId + 1;
    }
}