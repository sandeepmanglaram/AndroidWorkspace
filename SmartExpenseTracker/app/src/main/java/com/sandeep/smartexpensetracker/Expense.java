package com.sandeep.smartexpensetracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "expense")
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int expenseid;
    private String title;
    private String amount;
    private String category;
    private String note;
    private String date;

    public Expense(String title, String amount, String category, String note){
        this.date = Util.getCurrentDate();
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.note = note;
    }

    public int getExpenseid() {
        return expenseid;
    }

    public void setExpenseid(int expenseid) {
        this.expenseid = expenseid;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}




