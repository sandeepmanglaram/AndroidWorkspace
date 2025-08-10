package com.sandeep.smartexpensetracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ExpenseDAO {
    @Insert
    void insert(Expense expense);

    @Query("SELECT * FROM expense")
    List<Expense> getTotalExpense();

    @Query("SELECT * FROM expense WHERE date == :date")
    List<Expense> getExpenseforDate(String date);

    @Query("SELECT SUM(amount) FROM expense")
    double getTotalAmount();

    @Query("SELECT SUM(amount) FROM expense WHERE date = :todayTimestamp")
    double getTotalAmountForToday(String todayTimestamp);

    @Query("SELECT category, SUM(amount) AS total_amount FROM expense GROUP BY category")
    List<CategoryTotal> getSumGroupedByCategory();

}


