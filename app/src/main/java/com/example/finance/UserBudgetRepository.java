package com.example.finance;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// This class will not be full implemented
// its only purpose is to pull the data for all expenses and update
// the single userbudget in the system for example purposes only
// if this were fully fleshed out then the database calls would be made
// that are associated with the current userID
public class UserBudgetRepository {
    private DataBaseHelper db;
    private MutableLiveData<UserBudget> userBudget;
    private ExecutorService executor;

    public UserBudgetRepository(Context context) {
        db = new DataBaseHelper(context);
        userBudget = new MutableLiveData<>();
        executor = Executors.newSingleThreadExecutor();
        loadUserBudget();
    }

    private void loadUserBudget() {
        // give fictitious data for now because we arent pull any real data
        // for a userBudget as we're assuming there's just one
        // if we were pulling from the db then we would need:
        // budgetId, name, description, Cycle Amount, and Expense list, and BudgetItem list
        int id = 1;
        double cycleAmount = 5000.0;
        String name = "Budget 1";
        String description = "Description for Budget 1";
        ArrayList<BudgetItem> budgetItems = generateBudgetItems();

        executor.execute(() -> {
            ArrayList<Expense> expenseList = db.getAllExpenses();
            UserBudget userBudget = new UserBudget(id, cycleAmount, name, description,  expenseList, budgetItems);
            this.userBudget.postValue(userBudget);
        });
    }

    public void updateUserBudget(UserBudget userBudget) {
        // in our simple case all we need to do is update the list of expenses
        executor.execute(() -> {
            ArrayList<Expense> newExpenses = db.getAllExpenses();
            userBudget.setExpenses(newExpenses);
            this.userBudget.postValue(userBudget);
        });
    }

    public MutableLiveData<UserBudget> getUserBudget() {
        return userBudget;
    }


    // Other private methods

    private ArrayList<BudgetItem> generateBudgetItems() {
        ArrayList<BudgetItem> budgetItems = ExpenseUtils.generateSampleBudget();
        return budgetItems;
    }

}
