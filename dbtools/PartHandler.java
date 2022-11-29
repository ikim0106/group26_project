package dbtools;

import java.io.*;
import java.sql.*;

public class PartHandler {
    private int pid;
    private String pname;
    private int pprice;
    private int mid;
    private int cid;
    private int pwarrantyperiod;
    private int pavailablequantity;

    Database dbase = null;

    public PartHandler(Database dbase) {
        this.dbase = dbase;
    }

    public void handlePartFile(File pFile) {
        try {
            FileInputStream fstream = new FileInputStream(pFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                String[] toParse = strLine.split("\t");
                this.pid = Integer.parseInt(toParse[0]);
                this.pname = toParse[1];
                this.pprice = Integer.parseInt(toParse[2]);
                this.mid = Integer.parseInt(toParse[3]);
                this.cid = Integer.parseInt(toParse[4]);
                this.pwarrantyperiod = Integer.parseInt(toParse[5]);
                this.pavailablequantity = Integer.parseInt(toParse[6]);
                // System.out.println("mid: " + this.mid + ", " + "mname: " + this.mname + ", "
                // + "maddress: " + this.maddress + ", " + "mphonenumber: " +
                // this.mphonenumber);
                try {
                    PreparedStatement stmt = this.dbase.dbConnection.prepareStatement(
                            "INSERT INTO part (pid, pname, pprice, mid, cid, pwarrantyperiod, pavailablequantity) VALUES(?, ?, ?, ?, ?, ?, ?)");
                    stmt.setInt(1, this.pid);
                    stmt.setString(2, this.pname);
                    stmt.setInt(3, this.pprice);
                    stmt.setInt(4, this.mid);
                    stmt.setInt(5, this.cid);
                    stmt.setInt(6, this.pwarrantyperiod);
                    stmt.setInt(7, this.pavailablequantity);
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

    public void printPart(String tableName) {
        System.out.println("| p_id | p_name | p_price | m_id | c_id | p_warrantyperiod | p_availablequantity |");
        int partid;
        String partname;
        int partprice;
        int manufacturerid;
        int categoryid;
        int partwarrantyperiod;
        int partavailablequantity;
        try {
            Statement stmt = this.dbase.dbConnection.createStatement();
            ResultSet rs = stmt
                    .executeQuery("SELECT pid, pname, pprice, mid, cid, pwarrantyperiod, pavailablequantity FROM part");
            while (rs.next()) {
                partid = rs.getInt(1);
                partname = rs.getString(2);
                partprice = rs.getInt(3);
                manufacturerid = rs.getInt(4);
                categoryid = rs.getInt(5);
                partwarrantyperiod = rs.getInt(6);
                partavailablequantity = rs.getInt(7);
                System.out.printf("| %d | %s | %d | %d | %d | %d | %d |\n", partid, partname, partprice, manufacturerid,
                        categoryid, partwarrantyperiod, partavailablequantity);
            }
            // System.out.println(categoryname);
        } catch (SQLException sql_e) {
            System.out.println(sql_e);
        }
    }

}
