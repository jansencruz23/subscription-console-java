package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.Customer;
import plans.*;

public class CSVHelper {
	private String filePath = new File("customers.csv").getAbsolutePath();

	public void insertCustomer(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(formatCustomerToCSV(customer, false));
            System.out.println("Customer is registered successfully");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private String formatCustomerToCSV(Customer customer, boolean toUpdate) {
		var customerId = toUpdate ? customer.getCustomerId() : getLastCustomerId();
		System.out.println(customer.getSubDue().toString());
        return customerId + ","
                + customer.getCustomerName() + ","
                + customer.getAddress() + ","
                + customer.getEmail() + ","
                + customer.getSubType().getClass().getSimpleName() + ","
                + customer.isSubConti() + ","
                + customer.getSubDue().toString() + "\n";
    }
	
	public int getLastCustomerId() {
        int lastId = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if (parts.length < 1) {
                    continue; 
                }
                
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

	            if (parts.length < 1) {
                    continue;
                }
	            
	            if (parts[0].equals(id+"")) {
	            	customer = createCustomer(parts);
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
	            
	            if (parts.length < 1) {
                    continue; 
	            }
	            
	            String customerEmail = parts[3];
                
	            if (customerEmail.equals(email)) {
	            	customer = createCustomer(parts);
	            }
	        }
	    } 
	    catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }

	    return customer; 
	}
		
	private Customer createCustomer(String[] parts) {
		var customerId = Integer.parseInt(parts[0]);
        var customerName = parts[1];
        var address = parts[2];
        var email = parts[2];
        var subType = parts[4];
        var subConti = Boolean.parseBoolean(parts[5]);
        var date = LocalDate.parse(parts[6]);
        
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
		
	public void updateCustomer(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(formatCustomerToCSV(customer, true));
            System.out.println("Customer is updated successfully");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public Customer[] getHistory(int id) {
		List<Customer> customerList = new ArrayList<>();

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");

	            if (parts.length < 1) {
	                continue;
	            }

	            if (parts[0].equals(String.valueOf(id))) {
	                customerList.add(createCustomer(parts));
	            }
	        }
	    } catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }

	    return customerList.toArray(new Customer[0]);
	}
	
	public boolean isCustomerExisting(Customer customer) {
		return !(getCustomer(customer.getEmail()) == null) || !customer.isSubConti();
	}
}