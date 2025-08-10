package com.sandeep.smartexpensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "com.sandeep.smartexpensetracker.MainActivity";
    Button btnAddNewExpense, btnVisualize, btnViewExpenses;
    TextView txtTotalExpense, txtExpenseToday;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String FIRST_LAUNCH_KEY = "firstLaunch";
    private ExpenseDB db;
    private static final int REQUEST_PERMISSION = 100;
    ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        executorService = Executors.newSingleThreadExecutor();
        initializeViews();
        initializeResources();

        if (isFirstLaunch()) {
            runOnceCode();
            setFirstLaunchFlag(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeResources();
    }

    private void initializeResources() {
        Log.d(TAG, "initializeResources");
        DatabaseManager databaseManager = DatabaseManager.getDatabaseInstance(getApplicationContext());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                double total = databaseManager.getDB().expenseDAO().getTotalAmount();
                double todaysTotal = databaseManager.getDB().expenseDAO().getTotalAmountForToday(Util.getCurrentDate());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Set up the adapter with the fetched data
                        txtTotalExpense.setText(String.valueOf(total));
                        txtExpenseToday.setText(String.valueOf(todaysTotal));
                    }
                });
            }
        });
    }

    private void initializeViews() {
        Log.d(TAG, "initializeViews");
        btnAddNewExpense = findViewById(R.id.btnAddExpense);
        btnAddNewExpense.setOnClickListener(this);
        btnVisualize = findViewById(R.id.btnVisualizeExpense);
        btnVisualize.setOnClickListener(this);
        btnViewExpenses = findViewById(R.id.btnViewExpenses);
        btnViewExpenses.setOnClickListener(this);
        txtTotalExpense = findViewById(R.id.txtTotalExpense);
        txtExpenseToday = findViewById(R.id.txtTodaysExpense);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch(v.getId()){
            case R.id.btnAddExpense:{
                Log.d(TAG, "btnAddExpense");
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btnVisualizeExpense:{
                Log.d(TAG, "btnVisualizeExpense");
                Intent intent = new Intent(MainActivity.this, VisualizeExpenseActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btnViewExpenses:{
                Log.d(TAG, "btnViewExpenses");
                Intent intent = new Intent(MainActivity.this, ViewExpensesActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    private boolean isFirstLaunch() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(FIRST_LAUNCH_KEY, true); // Default to true
    }

    private void setFirstLaunchFlag(boolean isFirstLaunch) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_LAUNCH_KEY, isFirstLaunch);
        editor.apply();
    }

    private void runOnceCode() {
        Toast.makeText(this, "This is the first launch! Code executed.", Toast.LENGTH_SHORT).show();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Expense exp1 = new Expense("Electricity", "500", "OTHER", "Monthly Electricity bill");
                exp1.setDate("9-8-2025");
                Expense exp2 = new Expense("Weekend Dinner", "2000", "FOOD", "Dinner");
                exp2.setDate("9-8-2025");
                Expense exp3 = new Expense("Groceries", "1000", "GROCERIES", "Monthly Groceries");
                exp3.setDate("9-8-2025");
                Expense exp4 = new Expense("To Home town", "5000", "TRAVEL", "Travel");
                exp4.setDate("10-8-2025");
                DatabaseManager.getDatabaseInstance(getApplicationContext()).getDB().expenseDAO().insert(exp1);
                DatabaseManager.getDatabaseInstance(getApplicationContext()).getDB().expenseDAO().insert(exp2);
                DatabaseManager.getDatabaseInstance(getApplicationContext()).getDB().expenseDAO().insert(exp3);
                DatabaseManager.getDatabaseInstance(getApplicationContext()).getDB().expenseDAO().insert(exp4);
            }
        });
    }
}