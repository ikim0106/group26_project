package dbtools;

import java.io.*;
import java.sql.*;

public class CategoryHandler {
    private int cid;
    private String cname;

    Database dbase = null;

    public CategoryHandler(Database dbase) {
        this.dbase = dbase;
    }

    public void handleCategoryFile(File cFile) {
        try {
            FileInputStream fstream = new FileInputStream(cFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null)   {
                String[] toParse = strLine.split("\t");
                this.cid = Integer.parseInt(toParse[0]);
                this.cname = toParse[1];
                // System.out.println("cid: " + this.cid + ", " + "cname: " + this.cname);
                try {
                    PreparedStatement stmt = this.dbase.dbConnection.prepareStatement("INSERT INTO category (cid, cname) VALUES(?, ?)");
                    stmt.setInt(1, this.cid);
                    stmt.setString(2, this.cname);
                    stmt.execute();
                } catch (SQLException sql_e) {
                    System.out.println(sql_e);
                }
            }

            in.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void printCategory(String tableName) {
        System.out.println("| c_id | c_name |");
        int categoryid;
        String categoryname;
        try {
            Statement stmt = this.dbase.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT cid, cname FROM category");
            while(rs.next()) {
                categoryid = rs.getInt(1);
                categoryname = rs.getString(2);
                System.out.printf("| %d | %s |\n", categoryid, categoryname);
                // System.out.println(categoryname);
            }
        } catch(SQLException sql_e) {System.out.println(sql_e);}
    }
}
