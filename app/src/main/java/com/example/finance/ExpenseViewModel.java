package com.example.finance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository repository;
    private MutableLiveData<List<Expense>> expenses;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository(application);
        expenses = repository.getExpenses();

    }

    public void addExpense(Expense expense) {
        repository.addExpense(expense);
    }

    public MutableLiveData<List<Expense>> getExpenses() {
        return expenses;
    }
}
