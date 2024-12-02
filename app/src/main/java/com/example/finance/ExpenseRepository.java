package com.example.finance;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpenseRepository {
    private DataBaseHelper db;
    private MutableLiveData<List<Expense>> expenses;
    private ExecutorService executor;

    public ExpenseRepository(Context context) {
        db = new DataBaseHelper(context);
        expenses = new MutableLiveData<>();
        executor = Executors.newSingleThreadExecutor();
        loadExpenses();
    }

    private void loadExpenses() {
        executor.execute(() -> {
            List<Expense> expenseList = db.getAllExpenses();
            expenses.postValue(expenseList);
        });
    }

    public void addExpense(Expense expense) {
        executor.execute(() -> {
            boolean success = db.addExpense(expense);
            if (success) {
                loadExpenses();
            }
        });
    }

    public MutableLiveData<List<Expense>> getExpenses() {
        return expenses;
    }
}
