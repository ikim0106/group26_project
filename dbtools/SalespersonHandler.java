package dbtools;

import java.io.*;
import java.sql.*;

public class SalespersonHandler {
    private int sid;
    private String sname;
    private String saddress;
    private int sphonenumber;
    private int sexperience;
    
    Database dbase = null;

    public SalespersonHandler(Database dbase) {
        this.dbase = dbase;
    }

    public void handleSalespersonFile(File sFile) {
        try {
            FileInputStream fstream = new FileInputStream(sFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null)   {
                String[] toParse = strLine.split("\t");
                this.sid = Integer.parseInt(toParse[0]);
                this.sname = toParse[1];
                this.saddress = toParse[2];
                this.sphonenumber = Integer.parseInt(toParse[3]);
                this.sexperience = Integer.parseInt(toParse[4]);
                // System.out.println("mid: " + this.mid + ", " + "mname: " + this.mname + ", " + "maddress: " + this.maddress + ", " + "mphonenumber: " + this.mphonenumber);
                try {
                    PreparedStatement stmt = this.dbase.dbConnection.prepareStatement("INSERT INTO salesperson (sid, sname, saddress, sphonenumber, sexperience) VALUES(?, ?, ?, ?, ?)");
                    stmt.setInt(1, this.sid);
                    stmt.setString(2, this.sname);
                    stmt.setString(3, this.saddress);
                    stmt.setInt(4, this.sphonenumber);
                    stmt.setInt(5, this.sexperience);
                    stmt.execute();
                } catch (SQLException sql_e) {
                    System.out.println(sql_e);
                }
            }
            in.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void printSalesperson(String tableName) {
        System.out.println("| s_id | s_name | s_address | sphonenumber | sexperience |");
        int salespersonid;
        String salespersonname;
        String salespersonaddress;
        int salespersonphonenumber;
        int salespersonexperience;
        try {
            Statement stmt = this.dbase.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sid, sname, saddress, sphonenumber, sexperience FROM salesperson");
            while(rs.next()) {
                salespersonid = rs.getInt(1);
                salespersonname = rs.getString(2);
                salespersonaddress = rs.getString(3);
                salespersonphonenumber = rs.getInt(4);
                salespersonexperience = rs.getInt(5);
                System.out.printf("| %d | %s | %s | %d | %d |\n", salespersonid, salespersonname, salespersonaddress, salespersonphonenumber, salespersonexperience);
                // System.out.println(categoryname);
            }
        } catch(SQLException sql_e) {System.out.println(sql_e);}
    }
}
