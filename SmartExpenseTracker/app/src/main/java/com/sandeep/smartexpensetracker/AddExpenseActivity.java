package com.sandeep.smartexpensetracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AddExpenseActivity extends AppCompatActivity implements View.OnClickListener{
    String TAG = "com.sandeep.smartexpensetracker.AddExpenseActivity";
    Button btnaddExpense;
    DatabaseManager dbmanager;
    Spinner spinnerCategory;
    EditText etxtTitle, etxtAmount, etxtCategory, etxtNote;
    ExpenseCategory[] categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_add_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
    }

    private void initializeViews() {
        btnaddExpense = findViewById(R.id.btnAddExpense);
        btnaddExpense.setOnClickListener(this);
        dbmanager = DatabaseManager.getDatabaseInstance(this);
        spinnerCategory = findViewById(R.id.spinner_category);
        etxtTitle = findViewById(R.id.et_title);
        etxtAmount = findViewById(R.id.et_amount);
        etxtNote = findViewById(R.id.et_notes);

        categories = ExpenseCategory.values();
        String[] categoriesNames = new String[categories.length];
        for (int i = 0; i < categories.length; i++) {
            categoriesNames[i] = categories[i].toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ExpenseCategory selectedCategory = ExpenseCategory.values()[position];
                System.out.println("Selected Category: " + selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch(v.getId()) {
            case R.id.btnAddExpense: {
                Log.d(TAG, "btnAddExpense");
                if(validate()){
                    ExpenseCategory cat = categories[spinnerCategory.getSelectedItemPosition()];
                    Expense expense = new Expense(etxtTitle.getText().toString(), etxtAmount.getText().toString(), cat.name(), etxtNote.getText().toString());
                    dbmanager.insertToDB(expense);
                    Toast.makeText(this, "Expense Inserted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            break;
        }
    }

    public boolean validate(){
        return true;
    }
}