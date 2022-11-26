package interfaces;

import java.sql.*;
import dbtools.*;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class SalesMenu {
    private Database dbase;
    
    public SalesMenu(Database dbase) {
        this.dbase = dbase;
    }

    public void getParts(int searchCriteria, String searchKey, int searchOrder) throws SQLException {
        SalesPartsHandler sph = new SalesPartsHandler(this.dbase);
        sph.printSalesParts(searchCriteria, searchKey, searchOrder);
        System.out.println("End of Query");
    }

    public void addTransaction(int pid, int sid) throws SQLException {       
        SalesTransactionHandler sth = new SalesTransactionHandler(this.dbase);
        sth.performTransaction(pid, sid); 
    }

    public void initSalesMenu() {
        while(true){
            System.out.println("\n-----Operations for salesperson menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Search for parts");
            System.out.println("2. Sell a part");
            System.out.println("3. Return to the main menu");
            System.out.printf("Enter your choice: ");
            Scanner reader = new Scanner(System.in);
            int n = reader.nextInt();
            // reader.close();

            if(n==1) {
                System.out.println("Choose the Search criterion:");
                System.out.println("1. Part Name");
                System.out.println("2. Manufacturer Name");
                System.out.printf("Choose the search criterion: ");
                int searchCriteria = reader.nextInt();

                System.out.printf("Type in the Search Keyword: ");
                String searchKey = reader.nextLine();
                searchKey += reader.nextLine();


                System.out.println("Choose ordering:");
                System.out.println("1. By price, ascending order");
                System.out.println("2. By price, descending order");
                System.out.printf("Choose the search criterion: ");
                int searchOrder = reader.nextInt();

                try {getParts(searchCriteria, searchKey, searchOrder);}
                catch(SQLException sql_e) {System.out.println(sql_e);}
            }

            else if(n==2) {
                System.out.printf("Enter The Part ID: ");
                int pid = reader.nextInt();
                
                System.out.printf("Enter The Salesperson ID: ");
                int sid = reader.nextInt();

                try {addTransaction(pid, sid);}
                catch(SQLException sql_e) {System.out.println(sql_e);}
            }
            
            else if(n==3) return;
        }
    }
}
