package com.example.finance;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpenseViewAdapter extends RecyclerView.Adapter<ExpenseViewAdapter.ExpenseViewHolder> {
    Context context;
    ArrayList<Expense> expenses;

    public ExpenseViewAdapter(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View expenseItem = layoutInflater.inflate(R.layout.expense_row, parent, false);
        // we can put an onclick listener here if we want...
        ExpenseViewHolder viewHolder = new ExpenseViewHolder(expenseItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewAdapter.ExpenseViewHolder holder, int position) {
        final Expense expense = expenses.get(position);
        double amount = expense.getAmount();

        holder.dateTextView.setText(expense.getDateString());
        //holder.descriptionTextView.setText(expense.getDescription());
        holder.amountTextView.setText("$ " + Double.toString(amount));
        holder.vendorTextView.setText(expense.getVendor());
        holder.categoryTextView.setText(expense.getCategory().toString());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
       // public TextView descriptionTextView;
        public TextView amountTextView;
        public TextView vendorTextView;
        public TextView categoryTextView;
        public ExpenseViewHolder(View expenseView) {
            super(expenseView);
            dateTextView = expenseView.findViewById(R.id.dateTextView);
            amountTextView = expenseView.findViewById(R.id.amountTextView);
            vendorTextView = expenseView.findViewById(R.id.vendorTextView);
            categoryTextView = expenseView.findViewById(R.id.categoryTextView);


            // We're going to put the description in some type of gesture event
            //descriptionTextView = expenseView.findViewById(R.id.descriptionTextView);

        }
    }
}
