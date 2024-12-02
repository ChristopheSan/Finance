package com.example.finance;
import android.os.Parcel;
import android.os.Parcelable;
import java.time.format.DateTimeFormatter;
import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Expense implements Parcelable {
    int id;
    LocalDate date;
    String description;
    String vendor;
    double amount;
    SpendingCategory category;

    public Expense(){}

    public Expense(Parcel in) {
        id = in.readInt();
        date = (LocalDate) in.readSerializable();
        description = in.readString();
        vendor = in.readString();
        amount = in.readDouble();
        category = (SpendingCategory) in.readSerializable();
    }

    public Expense(LocalDate date, String description, String vendor, double amount, SpendingCategory category) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.vendor = vendor;
        this.category = category;
    }

    public Expense(int id, LocalDate date, String description, String vendor, double amount, SpendingCategory category) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.vendor = vendor;
        this.category = category;
    }

    public LocalDate getDate() {

        return date;
    }

    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return date.format(formatter);
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpendingCategory getCategory() {
        return category;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(SpendingCategory category) {
        this.category = category;
    }

    public String toString() {
        String str = "Date" + date.toString() +
                "Vendor" + vendor +
                "Description" + description +
                "Amount" + amount +
                "Category" + category.toString();
        return str;
    }

    public String toSQLDateString(){
        // Needs to be in YYYY-MM-DD format
        // LocalDate default is YYYY-MM-DD
        return date.toString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel destination, int flags) {
        destination.writeInt(id);
        destination.writeSerializable(date);
        destination.writeString(description);
        destination.writeString(vendor);
        destination.writeDouble(amount);
        destination.writeSerializable(category);

    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };
}
