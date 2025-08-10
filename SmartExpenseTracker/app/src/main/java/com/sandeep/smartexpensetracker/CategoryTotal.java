package com.sandeep.smartexpensetracker;

public class CategoryTotal {
    public String category;
    public double total_amount;

    // Constructor, getters, and setters
    public CategoryTotal(String category, double total_amount) {
        this.category = category;
        this.total_amount = total_amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}

