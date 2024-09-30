package com.example.finance;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class BudgetViewAdapter extends RecyclerView.Adapter<BudgetViewAdapter.BudgetViewHolder> {
    Context context;
    ArrayList<BudgetItem> budgetItems;

    public BudgetViewAdapter(Context context, ArrayList<BudgetItem> budgetItems) {
        this.context = context;
        this.budgetItems = budgetItems;
    }

    @NonNull
    @Override
    public BudgetViewAdapter.BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View budgetItem = inflater.inflate(R.layout.budget_item_card, parent, false);
        return new BudgetViewHolder(budgetItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewAdapter.BudgetViewHolder holder, int position) {
        final BudgetItem budgetItem = budgetItems.get(position);
        double remainingAmount = budgetItem.getRemainingAmount();

        holder.labelTextView.setText(budgetItem.getLabel());
        holder.allocatedAmountTextView.setText(String.format("%.2f", budgetItem.getAllocatedAmount()));
        holder.usedAmountTextView.setText(String.format("%.2f", budgetItem.getUsedAmount()));
        holder.remainingAmountTextView.setText(String.format("%.2f", remainingAmount));

    }

    @Override
    public int getItemCount() {
        return budgetItems.size();
    }

    public static class BudgetViewHolder extends RecyclerView.ViewHolder {
        public TextView labelTextView;
        public TextView allocatedAmountTextView;
        public TextView usedAmountTextView;
        public TextView remainingAmountTextView;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            labelTextView = itemView.findViewById(R.id.labelTextView);
            allocatedAmountTextView = itemView.findViewById(R.id.allocatedAmountTextView);
            usedAmountTextView = itemView.findViewById(R.id.usedAmountTextView);
            remainingAmountTextView = itemView.findViewById(R.id.remainingAmountTextView);
        }

    }
}
