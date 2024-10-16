package com.example.finance;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBudget implements Parcelable {
    private int id;
    private double cycleAmount;
    private String name;
    private String description;
    private ArrayList<BudgetItem> budgetItems;

    public UserBudget(int id, String name){
        this.id = id;
        this.name = name;
        budgetItems = new ArrayList<BudgetItem>();
    }

    public UserBudget(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
        budgetItems = new ArrayList<BudgetItem>();
    }

    public UserBudget(int id, String name, String description, ArrayList<BudgetItem> budgetItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.budgetItems = budgetItems;
    }

    public UserBudget(int id, double cycleAmount, String name,
                      String description, ArrayList<BudgetItem> budgetItems) {
        this.id = id;
        this.name = name;
        this.budgetItems = budgetItems;
        this.description = description;
        this.cycleAmount = cycleAmount;
    }

    protected UserBudget(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        budgetItems = in.createTypedArrayList(BudgetItem.CREATOR);
    }


    public void setCycleAmount(double totalAllocatedAmount) {
        this.cycleAmount = totalAllocatedAmount;
    }

    public double getCycleAmount() {
        return cycleAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudgetItems(ArrayList<BudgetItem> budgetItems) {
        this.budgetItems = budgetItems;
    }

    public void addBudgetItem(BudgetItem item) {
        budgetItems.add(item);
    }

    public void removeBudgetItem(BudgetItem item) {
        budgetItems.remove(item);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalAllocatedAmount() {
        double total = 0;
        for (BudgetItem item : budgetItems) {
            total += item.getAllocatedAmount();
        }
        return total;
    }

    public double getTotalUsedAmount() {
        double total = 0;
        for (BudgetItem item : budgetItems) {
            total += item.getUsedAmount();
        }
        return total;
    }

    public BudgetItem getMostUsedItem(){
        BudgetItem mostUsed = budgetItems.get(0);
        for (BudgetItem item : budgetItems)
            if (item.getUsedAmount() > mostUsed.getUsedAmount())
                mostUsed = item;

        return mostUsed;
    }

    public int getNumberOfFullyUtilizedItems(){
        int num = 0;

        for (BudgetItem item : budgetItems) {
            if (item.getUsedAmount() == item.getAllocatedAmount() || item.getUsedAmount() > item.getAllocatedAmount()) {
                num++;
            }
        }
        return num;
    }

    public BudgetItem getBudgetItem(SpendingCategory category) {
        for (BudgetItem item : budgetItems) {
            if (item.getCategory() == category) {
                return item;
            }
        }
        return null;
    }

    public int getNumberOfUnderutilizedItems() {
        int num = 0;
        for (BudgetItem item : budgetItems) {
            if (item.getUsedAmount() < item.getAllocatedAmount()) {
                num++;
            }
        }
        return num;
    }




    public void updateBudgetUsage(ArrayList<Expense> expenses) {
        // Calculate the total amount spent in each category
        // Also calculate unallocated spending into a new budget item
        Set<SpendingCategory> categories = getCategories();
        BudgetItem unallocated = new BudgetItem(0, "Unallocated Spending", 0, SpendingCategory.Other);

        for (Expense expense : expenses) {
            if ( categories.contains(expense.getCategory()) ){ // If the expense is in the budget
                // Find the budget item that corresponds to the expense
                for (BudgetItem item : budgetItems) {
                    if (item.getCategory() == expense.getCategory()) {
                        item.addToUsedAmount(expense.getAmount());
                    }
                }
            }

            else { // If the expense is not in the budget
                unallocated.addToUsedAmount(expense.getAmount());
            }
        }
        // Add the unallocated item to the budget
        budgetItems.add(unallocated);
    }

    private Set<SpendingCategory> getCategories() {
        Set<SpendingCategory> categories = new HashSet<>();
        for (BudgetItem item : budgetItems) {
            categories.add(item.getCategory());
        }
        return categories;
    }

    public boolean isOverUsed(){
        return getTotalUsedAmount() > getTotalAllocatedAmount();
    }

    public ArrayList<BudgetItem> getBudgetItems() {
        return budgetItems;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(cycleAmount);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeTypedList(budgetItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserBudget> CREATOR = new Creator<UserBudget>() {
        @Override
        public UserBudget createFromParcel(Parcel in) {
            return new UserBudget(in);
        }

        @Override
        public UserBudget[] newArray(int size) {
            return new UserBudget[size];
        }
    };

}