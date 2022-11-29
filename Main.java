import java.sql.*;
import dbtools.*;
import interfaces.*;

public class Main {
    public static void main(String args[]) {
        Database dbase = new Database();
        try {
            dbase.getConnection();
        } catch (SQLException sql_e) {
            System.out.println(sql_e);
            System.exit(0);
        } catch (ClassNotFoundException class_e) {
            System.out.println();
            System.exit(0);
        }

        MainMenu menu = new MainMenu(dbase);
        menu.initMainMenu();
    }
}
