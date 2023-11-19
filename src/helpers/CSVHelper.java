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
	//Automatically detect the csv file database used for the application
	private String filePath = new File("customers.csv").getAbsolutePath();

	//A function that reflects an output if the creation of an account is success
	public void insertCustomer(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(formatCustomerToCSV(customer, false));
            System.out.println("Customer is registered successfully");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	//A formatter to arrange the values inside the account in order to the csv file
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
	
	//A method to get the last unique ID of user and increment to 1 to provide new unique ID
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
	
	//A process to retrieve informations inside the database based on the unique ID of the user
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
	
	//A method to check if the input email in the log in page matches of an email inside the csv file
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
		
	//An array that will hold the values inputted during registration
	private Customer createCustomer(String[] parts) {
		var customerId = Integer.parseInt(parts[0]);
        var customerName = parts[1];
        var address = parts[2];
        var email = parts[3];
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
		

	//A function that reflects an output if the update is success
	public void updateCustomer(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(formatCustomerToCSV(customer, true));
            System.out.println("Customer is updated successfully");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	//A function that gets the history on the csv based on the unique ID
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
	
	//To check wether an existing email is currently part of the database
	public boolean isCustomerExisting(Customer customer) {
		return !(getCustomer(customer.getEmail()) == null) || !customer.isSubConti();
	}
}