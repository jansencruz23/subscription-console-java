package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import main.Customer;
import plans.*;

public class CSVHelper {
	private String filePath = "D:\\User\\Jansen\\Self Study\\2023 - 11 - NOVEMBER\\Java\\Subscription Management System\\customers.csv"; // Define your file path here

	public void insertCustomer(Customer customer) {
		
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(formatCustomerToCSV(customer));
            System.out.println("user registered successfully");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private String formatCustomerToCSV(Customer customer) {
        return getLastCustomerId() + ","
                + customer.getCustomerName() + ","
                + customer.getAddress() + ","
                + customer.getEmail() + ","
                + customer.getSubType().getClass().getSimpleName() + ","
                + customer.isSubConti() + "\n";
    }
	
	private int getLastCustomerId() {
        int lastId = 0;
        
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
	
	public Customer getCustomer(int id) {
		Customer customer = null;

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            int customerId = Integer.parseInt(parts[0]);

	            if (customerId == id) {
	                // Found the matching customer ID, create a Customer object
	                String customerName = parts[1];
	                String address = parts[2];
	                String email = parts[3];
	                String subType = parts[4];
	                boolean subConti = Boolean.parseBoolean(parts[5]);
	                
	                BasePlan subPlan = null;
	                
	                switch(subType) {
	                case "StandardPlan":
	                	subPlan = new StandardPlan();
	                	break;
	                case "StudentPlan":
	                	subPlan = new StudentPlan();
	                	break;
	                case "GoldPlan":
	                	subPlan = new GoldPlan();
	                	break;
	                }

	                // Create the Customer object with the retrieved details
	                customer = new Customer(customerId, customerName, address, email, subPlan, subConti, this);
	                
	                // Break the loop as we found the customer with the specified ID
	                break;
	            }
	        }
	    } catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }

	    return customer; 
	}
}