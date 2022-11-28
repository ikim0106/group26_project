package interfaces;

import java.sql.*;
import dbtools.*;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class AdminMenu {
    private Database dbase;

    private String[] tables = {"category", "manufacturer", "part", "salesperson", "transaction"};

    public AdminMenu(Database dbase) {
        this.dbase = dbase;
    }

    public void createTables() throws SQLException {
        System.out.printf("Processing...");
        Statement stmt = this.dbase.dbConnection.createStatement();
        String[] tablesToCreate = {
            "CREATE TABLE category (cid INTEGER(1) NOT NULL PRIMARY KEY, cname VARCHAR(20) NOT NULL)",
            "CREATE TABLE manufacturer (mid INTEGER(2) NOT NULL PRIMARY KEY, mname VARCHAR(20) NOT NULL, maddress VARCHAR(50) NOT NULL, mphonenumber INT(8) NOT NULL)",
            "CREATE TABLE part (pid INTEGER(3) NOT NULL PRIMARY KEY, pname VARCHAR(20) NOT NULL, pprice INTEGER(5) NOT NULL, mid INTEGER(2) NOT NULL, cid INTEGER(1) NOT NULL, pwarrantyperiod INTEGER(2) NOT NULL, pavailablequantity INTEGER(2) NOT NULL)",
            "CREATE TABLE salesperson (sid INTEGER(2) NOT NULL PRIMARY KEY, sname VARCHAR(20) NOT NULL, saddress VARCHAR(50) NOT NULL, sphonenumber INTEGER(8) NOT NULL, sexperience INTEGER(1) NOT NULL)",
            "CREATE TABLE transaction (tid INTEGER(4) NOT NULL PRIMARY KEY, pid INTEGER(3) NOT NULL, sid INTEGER(2) NOT NULL, tdate DATE NOT NULL)",
        };

        for(int i=0; i<tablesToCreate.length; i++) {
            stmt.executeUpdate(tablesToCreate[i]);
        }
        System.out.println("Done! Database is initialized!\n");
    }

    public void deleteTables() throws SQLException {
        System.out.printf("Processing...");
        Statement stmt = this.dbase.dbConnection.createStatement();
        for(int i=0; i<tables.length; i++) {
            String removeQuery = "DROP TABLE " + tables[i];
            stmt.executeUpdate(removeQuery);
        }
        System.out.println("Done! Database is removed!\n");
    }

    public void addFiles(String pathname) throws SQLException {
        System.out.printf("Processing...");
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString() + '/' + pathname + '/';
        File folder = new File(s);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                if(fileName.startsWith("category")) {
                    // System.out.println(file.getName());
                    CategoryHandler ch = new CategoryHandler(this.dbase);
                    ch.handleCategoryFile(file);
                }
                else if(fileName.startsWith("manufacturer")) {
                    ManufacturerHandler mh = new ManufacturerHandler(this.dbase);
                    mh.handleManufacturerFile(file);
                }
                else if(fileName.startsWith("part")) {
                    PartHandler ph = new PartHandler(this.dbase);
                    ph.handlePartFile(file);
                }
                else if(fileName.startsWith("salesperson")) {
                    SalespersonHandler sh = new SalespersonHandler(this.dbase);
                    sh.handleSalespersonFile(file);
                }
                else if(fileName.startsWith("transaction")) {
                    TransactionHandler th = new TransactionHandler(this.dbase);
                    th.handleTransactionFile(file);
                }
            }
        }
        System.out.println("Done! Data is inputted to the database!\n");
    } 

    public void getTable(String tableName) throws SQLException {
        System.out.println("Content of table " + tableName+ ":");
        if(tableName.startsWith("category")) {
            CategoryHandler ch = new CategoryHandler(this.dbase);
            ch.printCategory(tableName);
        }
        else if(tableName.startsWith("manufacturer")) {
            ManufacturerHandler mh = new ManufacturerHandler(this.dbase);
            mh.printManufacturer(tableName);
        }
        else if(tableName.startsWith("part")) {
            PartHandler ph = new PartHandler(this.dbase);
            ph.printPart(tableName);
        }
        else if(tableName.startsWith("salesperson")) {
            SalespersonHandler sh = new SalespersonHandler(this.dbase);
            sh.printSalesperson(tableName);
        }
        else if(tableName.startsWith("transaction")) {
            TransactionHandler th = new TransactionHandler(this.dbase);
            th.printTransaction(tableName);
        }
        System.out.printf("\n");
    }

    public void initAdminMenu() {
        while(true){
            System.out.println("\n-----Operations for administrator menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Create all tables");
            System.out.println("2. Delete all tables");
            System.out.println("3. Load from datafile");
            System.out.println("4. Show content of a table");
            System.out.println("5. Return to the main menu");
            System.out.printf("Enter your choice: ");
            Scanner reader = new Scanner(System.in);
            int n = reader.nextInt();
            // reader.close();
            if(n==1) {
                try {createTables();} 
                catch(SQLException sql_e) {System.out.println(sql_e);}
            }

            else if(n==2) {
                try {deleteTables();} 
                catch(SQLException sql_e) {System.out.println(sql_e);}
            }

            else if(n==3) {
                System.out.printf("Type in the Source Data Folder Path: ");
                String pathname = reader.next();

                try {addFiles(pathname);}
                catch(SQLException sql_e) {System.out.println(sql_e);}
            }

            else if(n==4) {
                System.out.printf("Which table would you like to show: ");
                String tableName = reader.next();

                try {getTable(tableName);}
                catch(SQLException sql_e) {System.out.println(sql_e);}
            }
            
            return;
        }
    }
}
