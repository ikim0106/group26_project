package interfaces;

import java.sql.*;
import dbtools.*;
import java.util.Scanner;

public class AdminMenu {
    private Database dbase;

    private String[] tables = {"category", "manufacturer", "part", "salesperson", "transaction"};

    public AdminMenu(Database dbase) {
        this.dbase = dbase;
    }

    public void createTables() throws SQLException {
        System.out.printf("Processing...");
        Statement stmt = dbase.dbConnection.createStatement();
        String[] tablesToCreate = {
            "CREATE TABLE category (cid INTEGER(1) UNSIGNED NOT NULL, cname VARCHAR(20) NOT NULL, PRIMARY KEY (cid))",
            "CREATE TABLE manufacturer (mid INTEGER(2) UNSIGNED NOT NULL, mname VARCHAR(20) NOT NULL, maddress VARCHAR(50) NOT NULL, mphonenumber INT(8) UNSIGNED NOT NULL, PRIMARY KEY(mid))",
            "CREATE TABLE part (pid INTEGER(3) UNSIGNED NOT NULL, pname VARCHAR(20) NOT NULL, pprice INTEGER(5) UNSIGNED NOT NULL, mid INTEGER(2) UNSIGNED NOT NULL, cid INTEGER(1) UNSIGNED NOT NULL, pwarrantyperiod INTEGER(2) UNSIGNED NOT NULL, pavailablequantity INTEGER(2) UNSIGNED NOT NULL, PRIMARY KEY(pid))",
            "CREATE TABLE salesperson (sid INTEGER(2) UNSIGNED NOT NULL, sname VARCHAR(20) NOT NULL, saddress VARCHAR(50) NOT NULL, sphonenumber INTEGER(8) UNSIGNED NOT NULL, sexperience INTEGER(1) NOT NULL, PRIMARY KEY(sid))",
            "CREATE TABLE transaction (tid INTEGER(4) UNSIGNED NOT NULL, pid INTEGER(3) UNSIGNED NOT NULL, sid INTEGER(2) UNSIGNED NOT NULL, tdate DATE NOT NULL, PRIMARY KEY(tid))",
        };

        for(int i=0; i<tablesToCreate.length; i++) {
            stmt.executeUpdate(tablesToCreate[i]);
        }
        System.out.println("Done! Database is initialized!");
    }

    public void deleteTables() throws SQLException {
        System.out.printf("Processing...");
        Statement stmt = dbase.dbConnection.createStatement();
        for(int i=0; i<tables.length; i++) {
            String removeQuery = "DROP TABLE " + tables[i];
            stmt.executeUpdate(removeQuery);
        }
        System.out.println("Done! Database is removed!");
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
                try {
                    createTables();
                } catch(SQLException sql_e) {
                    System.out.println(sql_e);
                }
            }

            else if(n==2) {
                try {
                    deleteTables();
                } catch(SQLException sql_e) {
                    System.out.println(sql_e);
                }
            }
            
            else if(n==5) return;
        }
    }
}
