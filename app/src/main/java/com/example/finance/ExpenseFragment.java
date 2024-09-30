package com.example.finance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ExpenseFragment extends Fragment {
    private RecyclerView expenseRecyclerView;
    private ExpenseViewAdapter adapter;
    private ArrayList<Expense> expenses;

    public ExpenseFragment() {

    }

    public static ExpenseFragment newInstance(ArrayList<Expense> expenses) {
        ExpenseFragment fragment = new ExpenseFragment();

        // Bundle is how we pass data between fragments
        Bundle args = new Bundle();
        args.putParcelableArrayList("expenses", expenses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        //ArrayList<Expense> expenses;
        // Retrieve expenses from arguments
        if (getArguments() != null) {
           expenses = getArguments().getParcelableArrayList("expenses");
        } else {
            expenses = new ArrayList<>();
        }

        // Initialize RecyclerView
        expenseRecyclerView = view.findViewById(R.id.expenseRecyclerView);
        adapter = new ExpenseViewAdapter(expenses);
        expenseRecyclerView.setAdapter(adapter);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void updateExpenses(ArrayList<Expense> newExpenses) {
        this.expenses.clear();
        this.expenses.addAll(newExpenses);
        adapter.notifyDataSetChanged();
    }
}