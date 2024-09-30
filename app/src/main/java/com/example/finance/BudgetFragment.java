package com.example.finance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BudgetFragment extends Fragment {
    private UserBudget userBudget; // This holds the budget information for a single budget
    private RecyclerView budgetRecyclerView;
    private BudgetViewAdapter adapter;

    public BudgetFragment() {
        // Required empty public constructor
    }

    public static BudgetFragment newInstance(UserBudget userBudget) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putParcelable("userBudget", userBudget);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        if (getArguments() != null) {
            userBudget = getArguments().getParcelable("userBudget");
        }
        else {
            userBudget = new UserBudget(0, "Default Budget"); // this shouldn't happen
            // if there's an error at some point it's probably because no budgetItems
            // TODO: handle this error better
        }

        budgetRecyclerView = view.findViewById(R.id.budgetRecyclerView);
        int numberOfGridColumns = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), numberOfGridColumns);
        budgetRecyclerView.setLayoutManager(gridLayoutManager);

        adapter = new BudgetViewAdapter(this.getContext(), userBudget.getBudgetItems());
        budgetRecyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
}