package com.example.finance;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class BudgetItem implements Parcelable{
    private int id;
    private String label;
    private double allocatedAmount;
    private double usedAmount;
    private SpendingCategory category;

    public BudgetItem(int id, String label, double allocatedAmount, SpendingCategory category) {
        this.id = id;
        this.label = label;
        this.allocatedAmount = allocatedAmount;
        this.category = category;
    }

    protected BudgetItem(Parcel in) {
        id = in.readInt();
        label = in.readString();
        allocatedAmount = in.readDouble();
        usedAmount = in.readDouble();
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public double getAllocatedAmount() {
        return allocatedAmount;
    }

    public double getUsedAmount() {
        return usedAmount;
    }

    public SpendingCategory getCategory() { return category;}

    public void setUsedAmount(double usedAmount) {
        this.usedAmount = usedAmount;
    }

    public double getRemainingAmount() {
        return allocatedAmount - usedAmount;
    }

    public boolean isOverAllocated() {
        return usedAmount > allocatedAmount;
    }

    public boolean isUnderAllocated() {
        return usedAmount < allocatedAmount;
    }

    public void addToUsedAmount(double amount) {
        usedAmount += amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(label);
        parcel.writeDouble(allocatedAmount);
        parcel.writeDouble(usedAmount);
    }

    public static final Creator<BudgetItem> CREATOR = new Creator<BudgetItem>() {
        @Override
        public BudgetItem createFromParcel(Parcel in) {
            return new BudgetItem(in);
        }

        @Override
        public BudgetItem[] newArray(int size) {
            return new BudgetItem[size];
        }
    };
}
