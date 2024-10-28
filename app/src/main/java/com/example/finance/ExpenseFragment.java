package com.example.finance;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ExpenseFragment extends Fragment {
    private RecyclerView expenseRecyclerView;
    private ExpenseViewAdapter adapter;
    private ArrayList<Expense> expenses;

    private ImageButton newExpenseButton;

    private ConstraintLayout parentLayout;

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

        // Initialize parent layout
        parentLayout = view.findViewById(R.id.expenseParentLayout);


        // Initialize RecyclerView
        expenseRecyclerView = view.findViewById(R.id.expenseRecyclerView);
        adapter = new ExpenseViewAdapter(expenses);
        expenseRecyclerView.setAdapter(adapter);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Intialize button to add new expenses
        newExpenseButton = view.findViewById(R.id.newExpenseButton);
        newExpenseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                initNewExpenseFragment();
            }
        });

        return view;
    }

    public void updateExpenses(ArrayList<Expense> newExpenses) {
        this.expenses.clear();
        this.expenses.addAll(newExpenses);
        adapter.notifyDataSetChanged();
    }

    public void addNewExpense(Expense newExpense) {
        this.expenses.add(newExpense);
        adapter.notifyDataSetChanged();
    }

    public void setAlpha(float alpha) {
        parentLayout.setAlpha(alpha);
    }

    private void initNewExpenseFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment newExpenseFragment = new NewExpenseFragment(this);
        transaction.add(R.id.fragment_container, newExpenseFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        parentLayout.setAlpha( (float) .1);

    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }
}