package com.sandeep.smartexpensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.titleTextView.setText(expense.getTitle());
        holder.amountTextView.setText(expense.getAmount());
        holder.categoryTextView.setText(expense.getCategory());
        holder.noteTextView.setText(expense.getNote());
        holder.dateTextView.setText(String.valueOf(expense.getDate())); // You can format the date as needed
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, amountTextView, categoryTextView, noteTextView, dateTextView;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            amountTextView = itemView.findViewById(R.id.amount);
            categoryTextView = itemView.findViewById(R.id.category);
            noteTextView = itemView.findViewById(R.id.note);
            dateTextView = itemView.findViewById(R.id.date);
        }
    }
}