package com.example.finance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UserBudgetViewModel extends AndroidViewModel {
    private UserBudgetRepository repository;
    private MutableLiveData<UserBudget> budget; // ideally this is a list as a user can have more than one budget

    public UserBudgetViewModel(@NonNull Application application) {
        super(application);
        repository = new UserBudgetRepository(application);
        budget = repository.getUserBudget();
    }

    // This should exisit if there is more than one budget
    // in our case we only have one budget
//    public void addBudget(UserBudget budget) {
//        repository.addBudget(budget);
//    }
    public void updateBudget() {
        repository.updateUserBudget(budget.getValue());
    }

    public MutableLiveData<UserBudget> getBudget() {
        return budget;
    }

}
