package interfaces;

import java.util.Scanner;
import dbtools.*;

public class MainMenu {
    private Database dbase;

    public MainMenu(Database dbase) { //get dbase params from Main
        this.dbase = dbase;
    }

    public void initMainMenu() {
        while(true) {
            System.out.println("Connection established");
            System.out.println(this.dbase.toString());
            System.out.println("\n-----Main menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Operations for administrator");
            System.out.println("2. Operations for salesperson");
            System.out.println("3. Operations for manager");
            System.out.println("4. Exit this program");
            System.out.printf("Enter your choice: ");
    
            Scanner reader = new Scanner(System.in);
            int n = reader.nextInt();
            // reader.close();
            // System.out.println(String.format("You chose %d", n));
            if(n==1) {
                // System.out.println("Initializing admin interface...");
                AdminMenu admin = new AdminMenu(dbase);
                admin.initAdminMenu();
            }
            else if(n==2) {
                SalesMenu sales = new SalesMenu(dbase);
                sales.initSalesMenu();
            }
            else if(n==3) {
                // System.out.println("Initializing manager interface...");
                ManagerMenu admin = new ManagerMenu(dbase);
                admin.initManagerMenu();
            }
            else if(n==4) return;
        }
    }
}

