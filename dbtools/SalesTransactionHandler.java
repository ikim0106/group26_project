package dbtools;

import java.io.*;
import java.sql.*;
import java.util.Calendar;

public class SalesTransactionHandler {
    Database dbase = null;
    private String partname;
    private int partavailablequantity;
    private int partcount;
    private int salespersoncount;

    public SalesTransactionHandler(Database dbase) {
        this.dbase = dbase;
    }

    public void performTransaction(int partid, int salespersonid) {
        // filter for invalid partid and salespersonid
        try {
            Statement stmt = this.dbase.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM part");
            rs.next();
            partcount = rs.getInt(1);
        } catch(SQLException sql_e) {System.out.println(sql_e);}
        if(partid > partcount) {
            System.out.printf("Error: Part ID %d does not exist.\n", partid);
            return;            
        }
        try {
            Statement stmt = this.dbase.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM salesperson");
            rs.next();
            salespersoncount = rs.getInt(1);
        } catch(SQLException sql_e) {System.out.println(sql_e);}
        if(salespersonid > salespersoncount) {
            System.out.printf("Error: Salesperson ID %d does not exist.\n", salespersonid);
            return;               
        }
        // check whether part is available
        try {
            PreparedStatement stmt = this.dbase.dbConnection.prepareStatement("SELECT pname, pavailablequantity FROM part WHERE pid = ?");
            stmt.setInt(1, partid);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            partname = rs.getString(1);
            partavailablequantity = rs.getInt(2);
            // System.out.printf("%s, %d", partname, partavailablequantity);
        } catch(SQLException sql_e) {System.out.println(sql_e);}
        if(partavailablequantity==0) {
            System.out.printf("Error! Product: %s(id: %d) is out of stock\n", partname, partid);
            return;
        }
        // add new transaction to table
        int transactioncount = 0;
        try {
            Statement stmt = this.dbase.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM transaction");
            rs.next();
            transactioncount = rs.getInt(1);
        } catch(SQLException sql_e) {System.out.println(sql_e);}
        java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            PreparedStatement stmt = this.dbase.dbConnection.prepareStatement("INSERT INTO transaction (tid, pid, sid, tdate) VALUES(?, ?, ?, ?)");
            stmt.setInt(1, transactioncount+1);
            stmt.setInt(2, partid);
            stmt.setInt(3, salespersonid);
            stmt.setDate(4, today);
            stmt.execute();
        } catch(SQLException sql_e) {System.out.println(sql_e);}
        // update part with new available quantity
        partavailablequantity--; 
        try {
            PreparedStatement stmt = this.dbase.dbConnection.prepareStatement("UPDATE part SET pavailablequantity = ? WHERE pid = ?");
            stmt.setInt(1, partavailablequantity);
            stmt.setInt(2, partid);
            stmt.execute();
        } catch(SQLException sql_e) {System.out.println(sql_e);}
        // output the message 
        System.out.printf("Product: %s(id: %d) Remaining Quality: %d\n\n", partname, partid, partavailablequantity);
    }

}