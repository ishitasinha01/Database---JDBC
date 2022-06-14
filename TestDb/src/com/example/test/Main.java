package com.example.test;

import java.awt.*;
import java.sql.*;

public class Main {
    public static final String DB_NAME = "test.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Ishita Sinha\\Desktop\\java\\TestDb" + DB_NAME;
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";

    // connecting to database jdbc after verision 4.0
    public static void main(String[] args) {

//try(    Connection conn= DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Ishita Sinha\\Desktop\\java\\TestDb\\test.db");// location of db after sqlite
//        Statement statement = conn.createStatement(); // we created the statement object by calling a connection method so the statement is associated with the connection and can only be run against the database we connected to when we created the connection instance
//
//){ // [utting these two statement inside try() is adv is the resourses will be closed when the try catch block is exited
        try { // here we will close resourses explicitly
            Connection conn = DriverManager.getConnection(CONNECTION_STRING); //("jdbc:sqlite:C:\\Users\\Ishita Sinha\\Desktop\\java\\TestDb\\test.db");// location of db after sqlite  WE replaced the hardcoded string with connection string constants
            //conn.setAutoCommit(false); // with this if we make channges they will not be shown in the browser
            Statement statement = conn.createStatement(); // we created the statement object by calling a connection method so the statement is associated with the connection and can only be run against the database we connected to when we created the connection instance
            //INSERTING INTO DB constants
            statement.execute("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "(" + COLUMN_NAME + "text," + COLUMN_PHONE + "integer," + COLUMN_EMAIL + "text" + ")");//contacts(name TEXT, phone INTEGER,email TEXT)"); // REMOVING HARDCODED DATA // create contact table  do not need to add ; at end of atatement beacuse the driver understands that when we call execute we have passed the complete sql statement
//            statement.execute("INSERT INTO" + TABLE_CONTACTS + "(" + COLUMN_NAME + "," + COLUMN_PHONE + "," + COLUMN_EMAIL + " )" + "VALUES ('ishita',4333444,'ncndojcnod@nofjvf')");
//            statement.execute("INSERT INTO" + TABLE_CONTACTS + "(" + COLUMN_NAME + "," + COLUMN_PHONE + "," + COLUMN_EMAIL + " )" + "VALUES ('harry',44499959,'dfnifndj@nofjvf')");
//            statement.execute("INSERT INTO" + TABLE_CONTACTS + "(" + COLUMN_NAME + "," + COLUMN_PHONE + "," + COLUMN_EMAIL + " )" + "VALUES ('Joe',12345,'jbfns@nofjvf')");
//            statement.execute("INSERT INTO" + TABLE_CONTACTS + "(" + COLUMN_NAME + "," + COLUMN_PHONE + "," + COLUMN_EMAIL + " )" + "VALUES ('Jane',345666,'jfdnn@nofjvf')");
//            statement.execute("UPDATE" + TABLE_CONTACTS + "SET" + COLUMN_PHONE + "=488599" + "WHERE" + COLUMN_NAME + "='Jane'");
//            statement.execute("DELETE FROM" + TABLE_CONTACTS + "WHERE" + COLUMN_NAME + "='Joe'"); // can remove these insert statement after the function creation
            // function call
            insertContacts(statement, "Ishita", 12345, "ishita@gmail");
            insertContacts(statement, "Joe", 67890, "joe@gmail");
            insertContacts(statement, "Jane", 98765, "jane@gmail");
            insertContacts(statement, "Tim", 54321, "tim@gmail");

            // INSERTING DATA IN DB hardcoded
//            statement.execute("INSERT INTO contacts(name,phone,email) VALUES ('ishita',3666222,'usuahs1hdwh')");
//            statement.execute("INSERT INTO contacts(name,phone,email) VALUES ('jane',838222,'njsnkjdna922nsk')");
//            statement.execute("INSERT INTO contacts(name,phone,email) VALUES ('hdsa',22333,'sbdjh822j')");
            // UPDATE DATA IN DB once the data inserted it will ramain and every time again those statements execute the data will be inserted again
//              statement.execute("UPDATE contacts SET phone= 33424 where name ='jane'");
//              // DELETE DATA IN DB
//            statement.execute("DELETE FROM contacts WHERE name='ishita'");
//            statement.execute("SELECT * FROM contacts");
//            ResultSet results = statement.getResultSet();// we can use the next two statements instead of these
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_CONTACTS);// HARDCODED contacts");
            while (results.next()) {
                System.out.println(results.getString(COLUMN_NAME) + " " + results.getString(COLUMN_PHONE) + " " + results.getString(COLUMN_EMAIL));// WRITING CONTANTS DECLARED ABOVE INSTEAD OF COLUMN NAME
            }
            results.close(); // in this code we class execute to perform select and then get result to set the results and we're then looping through each record and then we're printing out name phone and email
            statement.close(); // we get value of a col by callling appropriate get method
            conn.close();// closing resourses , get data on rum window
        } catch (SQLException e) {
            System.out.println("something went wrong " + e.getMessage());
            e.printStackTrace();// after writing hardcoded text
        }
    }

    //METHOD TO AVOID REPETETION OF INSERT STATEMENT
    private static void insertContacts(Statement statement, String name, int phone, String email) throws SQLException {
        statement.execute("INSERT INTO " + TABLE_CONTACTS + "(" + COLUMN_NAME + "," + COLUMN_PHONE + "," + COLUMN_EMAIL + " )" + "VALUES ('" + name + "'," + phone + ", '" + email + "') ");
    }
}
