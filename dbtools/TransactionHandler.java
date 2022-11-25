package dbtools;

import java.text.SimpleDateFormat;
import java.io.*;
import java.sql.*;
import java.util.Calendar;

public class TransactionHandler {
    private int tid;
    private int pid;
    private int sid;
    private String tdateString;
    private Calendar tdate;

    Database dbase = null;

    public TransactionHandler(Database dbase) {
        this.dbase = dbase;
    }

    public void handeTransactionFile(File tFile) {
        try {
            FileInputStream fstream = new FileInputStream(tFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null)   {
                String[] toParse = strLine.split("\t");
                this.tid = Integer.parseInt(toParse[0]);
                this.pid = Integer.parseInt(toParse[1]);
                this.sid = Integer.parseInt(toParse[2]);
                this.tdateString = toParse[3];

                String[] dateStuff = this.tdateString.split("/");
                this.tdate = Calendar.getInstance();
                this.tdate.set(Integer.parseInt(dateStuff[2]), Integer.parseInt(dateStuff[1]), Integer.parseInt(dateStuff[0]));
                long brev = this.tdate.getTimeInMillis();

                try {
                    PreparedStatement stmt = this.dbase.dbConnection.prepareStatement("INSERT INTO transaction (tid, pid, sid, tdate) VALUES(?, ?, ?, ?)");
                    stmt.setInt(1, this.tid);
                    stmt.setInt(2, this.pid);
                    stmt.setInt(3, this.sid);
                    stmt.setDate(4, new Date(brev));
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

    public void printTransaction(String tableName) {
        System.out.println("| t_id | p_id | s_id | t_date |");
        int transactionid;
        int partid;
        int salespersonid;
        Date wut;
        final String TO_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(TO_FORMAT);
        
        try {
            Statement stmt = this.dbase.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT tid, pid, sid, tdate FROM transaction");
            while(rs.next()) {
                transactionid = rs.getInt(1);
                partid = rs.getInt(2);
                salespersonid = rs.getInt(3);
                wut = rs.getDate(4);

                String dateStr = sdf.format(wut);

                System.out.printf("| %d | %d | %d | %s |\n", transactionid, partid, salespersonid, dateStr);
                // System.out.println(categoryname);
            }
        } catch(SQLException sql_e) {System.out.println(sql_e);}
    }
}
