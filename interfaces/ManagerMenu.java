package interfaces;
import java.sql.*;
import dbtools.*;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.*;

public class ManagerMenu {
    private Database dbase;

    private String[] tables = {"category", "manufacturer", "part", "salesperson", "transaction"};

    public ManagerMenu(Database dbase) {
        this.dbase = dbase;
    }

    public void listSalesman(boolean asc) throws SQLException {
        System.out.println("| ID | Name | Moobile Phone | Years of Experience |");
        Statement stmt = this.dbase.dbConnection.createStatement();
        ResultSet rs;
        if (asc == true){
            String Query = "SELECT slp.sid, slp.sname, slp.sphonenumber, slp.sexperience FROM salesperson slp ORDER BY slp.sexperience ASC;";
            rs = stmt.executeQuery(Query);
        }
        else{
            String Query = "SELECT slp.sid, slp.sname, slp.sphonenumber, slp.sexperience FROM salesperson slp ORDER BY slp.sexperience DESC;";
            rs = stmt.executeQuery(Query);
        }
        while(rs.next()) {
            int salespersonid = rs.getInt(1);
            String salespersonname = rs.getString(2);
            int salespersonphonenumber = rs.getInt(3);
            int salespersonexperience = rs.getInt(4);
            System.out.printf("| %d | %s | %d | %d |\n", salespersonid, salespersonname, salespersonphonenumber, salespersonexperience);
            // System.out.println(categoryname);
        }
    }

    public void transactionNum(int lower, int upper) throws SQLException {
        System.out.println("| ID | Name | Years of Experience | Number of Transaction |");
        Statement stmt = this.dbase.dbConnection.createStatement();
        ResultSet rs;
        String Query = String.format("SELECT s.sid, s.sname, s.sexperience, count(*) as trnum FROM transaction t INNER JOIN salesperson s on s.sid = t.sid WHERE s.sexperience >= %d AND s.sexperience < %d GROUP BY s.sid ORDER BY s.sid DESC;", lower, upper);
        rs = stmt.executeQuery(Query);
        while(rs.next()) {
            int salespersonid = rs.getInt(1);
            String salespersonname = rs.getString(2);
            int salespersonexperience = rs.getInt(3);
            int trnum= rs.getInt(4);
            System.out.printf("| %d | %s | %d | %d |\n", salespersonid, salespersonname, salespersonexperience, trnum);
        }
    }

    public void totalValue() throws SQLException {
        System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
        
        // System.out.printf("Processing...");
        // Path currentRelativePath = Paths.get("");
        // String s = currentRelativePath.toAbsolutePath().toString() + '/' + pathname + '/';
        // File folder = new File(s);
        // File[] listOfFiles = folder.listFiles();

        // for (File file : listOfFiles) {
        //     if (file.isFile()) {
        //         String fileName = file.getName();
        //         if(fileName.startsWith("category")) {
        //             // System.out.println(file.getName());
        //             CategoryHandler ch = new CategoryHandler(this.dbase);
        //             ch.handleCategoryFile(file);
        //         }
        //         else if(fileName.startsWith("manufacturer")) {
        //             ManufacturerHandler ch = new ManufacturerHandler(this.dbase);
        //             ch.handleManufacturerFile(file);
        //         }
        //         else if(fileName.startsWith("part")) {
        //             PartHandler ch = new PartHandler(this.dbase);
        //             ch.handlePartFile(file);
        //         }
        //         else if(fileName.startsWith("salesperson")) {
        //             SalespersonHandler ch = new SalespersonHandler(this.dbase);
        //             ch.handleSalespersonFile(file);
        //         }
        //         else if(fileName.startsWith("transaction")) {
        //             TransactionHandler th = new TransactionHandler(this.dbase);
        //             th.handeTransactionFile(file);
        //         }
        //     }
        // }
        // System.out.println("Done! Data is inputted to the database!");
    } 

    public void nPopular(int num) throws SQLException {
        System.out.println("| Part ID | Part Name | No. of Transaction |");
        
        Statement stmt = this.dbase.dbConnection.createStatement();
        ResultSet rs;
        String Query = String.format("SELECT p.pid, p.pname, count(*) as trnum FROM transaction t INNER JOIN part p on p.pid = t.pid GROUP BY p.pid ORDER BY trnum DESC LIMIT %d;", num);
        rs = stmt.executeQuery(Query);
        while(rs.next()) {
            int partid = rs.getInt(1);
            String partname = rs.getString(2);
            int trnum = rs.getInt(3);
            System.out.printf("| %d | %s | %d |\n", partid, partname, trnum);
        }
    }

    public void initManagerMenu() {
        while(true){
            System.out.println("\n-----Operations for manager menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. List all salespersons");
            System.out.println("2. Count the no. of sales record of each salesperson under a specific range on years of experience");
            System.out.println("3. Show the total sales value of each manufacturer");
            System.out.println("4. Show the N most popular part");
            System.out.println("5. Return to the main menu");
            System.out.printf("Enter your choice: ");
            Scanner reader = new Scanner(System.in);
            int n = reader.nextInt();
            // reader.close();
            if(n==1) {
                while (true){
                    System.out.println("Choose ordering:");
                    System.out.println("1. By ascending order");
                    System.out.println("2. By descending order");
                    System.out.printf("Choose the list ordering: ");
                    int m = reader.nextInt();
                    if (m==1){
                        try {listSalesman(true);break;} 
                        catch(SQLException sql_e) {System.out.println(sql_e);break;}
                    }
                    else if (m==2){
                        try {listSalesman(false);break;} 
                        catch(SQLException sql_e) {System.out.println(sql_e);break;}
                    }
                    else{
                        System.out.println("Unrecognized input. Please try again");
                    }
                }
            }

            else if(n==2) {
                System.out.printf("Type in the lower bound for years of experience: ");
                int w = reader.nextInt();
                System.out.printf("Type in the upper bound for years of experience: ");
                int s = reader.nextInt();
                try {transactionNum(w,s);} 
                catch(SQLException sql_e) {System.out.println(sql_e);}
            }

            else if(n==3) {
                try {totalValue();}
                catch(SQLException sql_e) {System.out.println(sql_e);}
            }

            else if(n==4) {
                System.out.printf("Type in the number of parts: ");
                try{
                    int num = reader.nextInt();
                    if (num <= 0){
                        System.out.println("Input should be larger than 0");
                    }
                    else{  
                        try {nPopular(num);}
                        catch(SQLException sql_e) {System.out.println(sql_e);}
                    }
                }
                catch(InputMismatchException e){System.out.println("Input should be an integer");}
                
            }
            
            else if(n==5) return;
        }
    }
}
