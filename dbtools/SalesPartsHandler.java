package dbtools;

import java.sql.*;

public class SalesPartsHandler {
    Database dbase = null;

    public SalesPartsHandler(Database dbase) {
        this.dbase = dbase;
    }

    public void printSalesParts(int searchCriteria, String searchKey, int searchOrder) {
        // Run query
        // Output results
        System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
        int partid;
        String partname;
        String manufacturername;
        String categoryname;
        int partavailablequantity;
        int partwarrantyperiod;
        int partprice;
        String newQuery = "SELECT P.pid, P.pname, M.mname, C.cname, P.pavailablequantity, P.pwarrantyperiod, P.pprice FROM "
                + "part P, manufacturer M, category C WHERE " + "P.mid = M.mid AND " + "P.cid = C.cid AND ";
        try {
            if (searchCriteria == 1) {
                newQuery = newQuery + "P.pname = ?";
            } else if (searchCriteria == 2) {
                newQuery = newQuery + "M.mname = ?";
            }
            if (searchOrder == 1) {
                newQuery = newQuery + " ORDER BY P.pprice ASC";
            } else if (searchOrder == 2) {
                newQuery = newQuery + " ORDER BY P.pprice DESC";
            }
            PreparedStatement stmt = this.dbase.dbConnection.prepareStatement(newQuery);
            stmt.setString(1, searchKey);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                partid = rs.getInt(1);
                partname = rs.getString(2);
                manufacturername = rs.getString(3);
                categoryname = rs.getString(4);
                partavailablequantity = rs.getInt(5);
                partwarrantyperiod = rs.getInt(6);
                partprice = rs.getInt(7);
                System.out.printf("| %d | %s | %s | %s | %d | %d | %d |\n", partid, partname, manufacturername,
                        categoryname, partavailablequantity, partwarrantyperiod, partprice);
            }
        } catch (SQLException sql_e) {
            System.out.println(sql_e);
        }
    }
}