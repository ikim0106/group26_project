package dbtools;

import java.io.*;
import java.sql.*;

public class ManufacturerHandler {
    private int mid;
    private String mname;
    private String maddress;
    private int mphonenumber;

    Database dbase = null;

    public ManufacturerHandler(Database dbase) {
        this.dbase = dbase;
    }

    public void handleManufacturerFile(File mFile) {
        try {
            FileInputStream fstream = new FileInputStream(mFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                String[] toParse = strLine.split("\t");
                this.mid = Integer.parseInt(toParse[0]);
                this.mname = toParse[1];
                this.maddress = toParse[2];
                this.mphonenumber = Integer.parseInt(toParse[3]);
                // System.out.println("mid: " + this.mid + ", " + "mname: " + this.mname + ", "
                // + "maddress: " + this.maddress + ", " + "mphonenumber: " +
                // this.mphonenumber);
                try {
                    PreparedStatement stmt = this.dbase.dbConnection.prepareStatement(
                            "INSERT INTO manufacturer (mid, mname, maddress, mphonenumber) VALUES(?, ?, ?, ?)");
                    stmt.setInt(1, this.mid);
                    stmt.setString(2, this.mname);
                    stmt.setString(3, this.maddress);
                    stmt.setInt(4, this.mphonenumber);
                    stmt.execute();
                } catch (SQLException sql_e) {
                    System.out.println(sql_e);
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void printManufacturer(String tableName) {
        System.out.println("| m_id | m_name | m_address | m_phonenumber |");
        int manufacturerid;
        String manufacturername;
        String manufactureraddress;
        int manufacturerphonenumber;
        try {
            Statement stmt = this.dbase.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT mid, mname, maddress, mphonenumber FROM manufacturer");
            while (rs.next()) {
                manufacturerid = rs.getInt(1);
                manufacturername = rs.getString(2);
                manufactureraddress = rs.getString(3);
                manufacturerphonenumber = rs.getInt(4);
                System.out.printf("| %d | %s | %s | %s |\n", manufacturerid, manufacturername, manufactureraddress,
                        manufacturerphonenumber);
                // System.out.println(categoryname);
            }
        } catch (SQLException sql_e) {
            System.out.println(sql_e);
        }
    }

}