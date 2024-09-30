package com.example.finance;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ExpenseHolder {
    //private int id;
    ArrayList<Expense> expenses;
    Set<SpendingCategory> usedCategories;

    public ExpenseHolder() {
        expenses = new ArrayList<Expense>();
        usedCategories = new HashSet<SpendingCategory>();
    }
    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense e){
        expenses.add(e);
        usedCategories.add(e.getCategory());
        e.setId(expenses.size());
    }

    public ArrayList<Expense> getExpenses(){
        return expenses;
    }

    public double getAllTimeTotal(){
        double sum = 0;
        for (Expense e : expenses) {
            sum += e.getAmount();
        }
        return sum;
    }

    public double getAllTimeCategory(SpendingCategory searchCategory){
        double sum = 0;
        for (Expense e : expenses){
            if (e.getCategory() == searchCategory){
                sum += e.getAmount();
            }
        }
        return sum;
    }
}
