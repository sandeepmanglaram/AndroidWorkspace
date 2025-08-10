package com.sandeep.smartexpensetracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewExpensesActivity extends AppCompatActivity implements View.OnClickListener{
    String TAG = "com.sandeep.smartexpensetracker.ViewExpensesActivity";
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    TextView txtCurrentDate;
    Button btnCalender;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_expenses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtCurrentDate = findViewById(R.id.txtDate);
        txtCurrentDate.setText(String.valueOf(Util.getCurrentDate()));
        btnCalender = findViewById(R.id.btnCalender);
        btnCalender.setOnClickListener(this);
        mContext = this;
        // Load data from the database
        loadExpenses();
    }

    private void loadExpenses() {
/*
        // Get the data from the Room Database
        List<Expense> expenses = DatabaseManager.getDatabaseInstance(this).getDB().expenseDAO().getTotalExpense();


        // Set up the adapter with the data
        adapter = new ExpenseAdapter(expenses);
        recyclerView.setAdapter(adapter);*/

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Expense> expenses = DatabaseManager.getDatabaseInstance(getApplicationContext()).getDB().expenseDAO().getTotalExpense();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Set up the adapter with the fetched data
                        adapter = new ExpenseAdapter(expenses);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch(v.getId()) {
            case R.id.btnCalender: {
                Log.d(TAG, "btnCalender");
                openCalendar();
            }
        }
    }

    private void openCalendar() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    long timestamp = Util.convertDateToTimestamp(selectedDayOfMonth, selectedMonth + 1, selectedYear);
                    String selectedDate = selectedDayOfMonth + "-" + (selectedMonth + 1) + "-" + selectedYear;
                    txtCurrentDate.setText(selectedDate);
                    loadExpenseforDate(selectedDate);
                },

                year, month, day);
        datePickerDialog.show();
    }

    private void loadExpenseforDate(String selectedDate) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Expense> expenses = DatabaseManager.getDatabaseInstance(mContext).getDB().expenseDAO().getExpenseforDate(selectedDate);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ExpenseAdapter(expenses);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }
}