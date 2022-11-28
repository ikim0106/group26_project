CSCI3170 Introduction to Database Systems
Term 1, 2022-23

** Group number ** 
26 

** Group members ** 
Khachatur DALLAKYAN (1155149128)
Pran KITTIVORAPAT (1155152291)	
Inho KIM (1155116159)

** List of files **
1. interfaces folder
    a. MainMenu.java
        - create main menu interface
        - function to connect to administrator/salesperson/manager interfaces
        - function to exit the program
    b. AdminMenu.java
        - create administrator menu interface
        - function to create and delete all tables
        - connect to Administrator Tools (see below) to load data from dataset and output content of the specified table
        - function to return to main menu
    c. SalesMenu.java
        - create sales menu interface
        - connect to Salesperson Tools (see below) to search for parts and perform transaction
        - function to return to main menu
    d. ManagerMenu.java
        - create manager menu interface
        - perform manager functions: 
            listSalesman -- list all salespersons in order of sales experience
            transactionNum -- count no. of sales record of each salesperson within the specified range of experience
            totalValue -- output total sales value of each manufacturer in descending order
            nPopular -- show N most popular parts
        - function to return to main menu
2. dbtools folder
    A. Database Setup
        a. Database.java
            - connect to MySQL database
    B. Administrator Tools
        a. CategoryHandler.java
            - handleCategoryFile -- load category data from dataset and add to category table
            - printCategory -- output category table in the specified format
        b. ManufacturerHandler.java
            - handleManufacturerFile -- load manufacturer data from dataset and add to manufacturer table
            - printManufacturer -- output manufacturer table in the specified format
        c. PartHandler.java
            - handlePartFile -- load part data from dataset and add to part table
            - printPart -- output part table in the specified format
        d. SalespersonHandler.java
            - handleSalespersonFile -- load salesperson data from dataset and add to salesperson table
            - printSalesperson -- output salesperson table in the specified format
        e. TransactionHandler.java
            - handleTransactionFile -- load transaction data from dataset and add to transaction table
            - printTransaction -- output transaction table in the specified format
    C. Salesperson Tools
        a. SalesPartHandler.java
            - handles function "search for parts" according to search criteria "part name" or "manufacturer name"
            - output selected parts by price, in ascending or descending order according to selected ordering style
        b. SalesTransactionHandler.java
            - handles function "sell a part" by checking if part is available
            - if part is available, add transaction to transaction table and output remaining quality of the part
    D. Manager Tools 
        // already implemented within ManagerMenu.java

3. Others
    a. Makefile
        - for compilation and execution purposes, refer to the method below.
    b. Main.java
        - program starter

** Method of compilation and execution **
1. makefile commands
    A. compile code:
        make comp
    B. run code:
        make run
    C. clear old .class files: 
        make clear
    D. clear, comp, and run:
        make all
    E. if you run into an error about some .class files not existing:
        make comp
        make all