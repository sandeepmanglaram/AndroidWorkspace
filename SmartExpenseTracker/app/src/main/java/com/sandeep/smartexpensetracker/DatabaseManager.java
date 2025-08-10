package com.sandeep.smartexpensetracker;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.List;

public class DatabaseManager {
    private ExpenseDB db;
    private static DatabaseManager instance = null;
    private DatabaseManager(Context mContext) {
        db = Room.databaseBuilder(mContext,
                        ExpenseDB.class, "expense-database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static DatabaseManager getDatabaseInstance(Context mContext){
        if(instance == null){
            instance = new DatabaseManager(mContext);
        }
        return instance;
    }

    public ExpenseDB getDB(){
        return db;
    }

    /*public List<Expense> fetchAllExpense(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Expense> expenseList;

                // Retrieve all users to verify
               expenseList = db.expenseDAO().getTotalExpense();
                for (Expense e : expenseList) {
                    // Print out user info, including the current date (timestamp)
                    Log.d("Sandeep", "Expense: " + e.getTitle()+" Amount"+e.getAmount()+e.getDate()+e.getNote()+e.getCategory());
                    //System.out.println("Expense: " + e.getTitle()+" Amount"+e.getAmount()+e.getDate());
                }
                return expenseList;
            }
        }).start();

    }*/

    public void insertToDB(Expense expense){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Create a new user with current date
                //Expense expense = new Expense("Grocery", 30, ExpenseCategory.GROCERIES, "August 1st week Grocery");
//                Expense expense = new Expense("Grocery", 30, ExpenseCategory.GROCERIES, "August 1st week Grocery");

                // Insert the user into the database
                db.expenseDAO().insert(expense);

                // Retrieve all users to verify
                List<Expense> expenseList = db.expenseDAO().getTotalExpense();
                for (Expense e : expenseList) {
                    // Print out user info, including the current date (timestamp)
                    Log.d("Sandeep", "Expense: " + e.getTitle()+" Amount"+e.getAmount()+e.getDate()+e.getNote()+e.getCategory());
                    //System.out.println("Expense: " + e.getTitle()+" Amount"+e.getAmount()+e.getDate());
                }
            }
        }).start();
    }
}
