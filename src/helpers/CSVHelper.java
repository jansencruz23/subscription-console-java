package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import main.Customer;
import plans.*;

public class CSVHelper {
	private String filePath = new File("customers.csv").getAbsolutePath();

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
                + customer.isSubConti() + ","
                + customer.getSubDue().toString() + "\n";
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

	            if (parts[0].equals(id+"")) {
	            	customer = createCustomer(parts);
	                break;
	            }
	        }
	    } catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }

	    return customer; 
	}
	
	public Customer getCustomer(String email) {
		Customer customer = null;

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            String customerEmail = parts[3];

	            if (customerEmail.equals(email)) {
	            	customer = createCustomer(parts);
	                break;
	            }
	        }
	    } 
	    catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }

	    return customer; 
	}
	
	private Customer createCustomer(String[] parts) {
		int customerId = Integer.parseInt(parts[0]);
        String customerName = parts[1];
        String address = parts[2];
        String email = parts[2];
        String subType = parts[4];
        boolean subConti = Boolean.parseBoolean(parts[5]);
        LocalDate date = LocalDate.parse(parts[6]);
        
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

        return new Customer(customerId, customerName, address, email, subPlan, subConti, date, this);
	}
}