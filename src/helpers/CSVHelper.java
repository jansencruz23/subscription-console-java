package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import main.Customer;

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
}