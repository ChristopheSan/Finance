package com.example.finance;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import android.content.res.Resources;
import java.util.List;


public class ExpenseUtils {

    public static ArrayList<BudgetItem> generateSampleBudget() {
        ArrayList<BudgetItem> budgetItems = new ArrayList<BudgetItem>();

        // Build 5 budget items
        BudgetItem item0 = new BudgetItem(0, "Groceries", 300.00, SpendingCategory.Grocery);
        BudgetItem item1 = new BudgetItem(1, "Rent", 1950.00, SpendingCategory.Rent);
        BudgetItem item2 = new BudgetItem(2, "Home Utilities", 200.00, SpendingCategory.Electricity);
        BudgetItem item3 = new BudgetItem(3, "Internet", 60.00, SpendingCategory.Internet);
        BudgetItem item4 = new BudgetItem(4, "Gas", 150.00, SpendingCategory.Gas);

        budgetItems.add(item0);
        budgetItems.add(item1);
        budgetItems.add(item2);
        budgetItems.add(item3);
        budgetItems.add(item4);

        return budgetItems;
    }

    public static Expense generateRandomExpense() {
        // Generate a random date between 2020-01-01 and today
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.now();

        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();

        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        LocalDate randomDate = LocalDate.ofEpochDay(randomEpochDay);

        // Generate a random amount between $10.00 and $5,000.00
        double minAmount = 10.00;
        double maxAmount = 5000.00;
        double randomAmount = minAmount + (maxAmount - minAmount) * ThreadLocalRandom.current().nextDouble();
        randomAmount = Math.round(randomAmount * 100.0) / 100.0; // Round to 2 decimal places

        // Randomly select a spending category
        SpendingCategory[] categories = SpendingCategory.values();
        SpendingCategory randomCategory = categories[ThreadLocalRandom.current().nextInt(categories.length)];

        // Randomly select a description based on the category
        String randomDescription = getRandomDescription(randomCategory);

        // Randomly select a vendor based on the category
        String randomVendor = getRandomVendor(randomCategory);

        // Create the Expense object
        String vendor = randomVendor;
        Expense expense = new Expense(randomDate, randomDescription, vendor, randomAmount, randomCategory);

        return expense;
    }

    private static String getRandomDescription(SpendingCategory category) {
        switch (category) {
            case Automotive:
                return getRandomItem(new String[]{
                        "Routine maintenance at auto service center",
                        "Replacement of car battery",
                        "New set of tires for the vehicle",
                        "Car detailing and cleaning service"
                });
            case CarInsurance:
                return "Monthly car insurance premium payment";
            case CarPayment:
                return "Monthly car loan installment payment";
            case Education:
                return getRandomItem(new String[]{
                        "Purchase of textbooks for the semester",
                        "Enrollment in an online certification course",
                        "Tuition fee for the upcoming term"
                });
            case Electricity:
                return "Monthly electricity bill payment";
            case Fitness:
                return getRandomItem(new String[]{
                        "Annual gym membership renewal",
                        "Purchase of home exercise equipment",
                        "Registration fee for marathon event"
                });
            case Gas:
                return "Fuel refill at gas station";
            case Grocery:
                return "Weekly grocery shopping at supermarket";
            case HomeInsurance:
                return "Annual home insurance premium payment";
            case Internet:
                return "Monthly internet service provider bill";
            case Medical:
                return getRandomItem(new String[]{
                        "Doctor's consultation fee",
                        "Purchase of prescription medications",
                        "Emergency room visit charges"
                });
            case Mortgage:
                return "Monthly mortgage payment for home loan";
            case Other:
                return "Miscellaneous expense not categorized";
            case Personal:
                return getRandomItem(new String[]{
                        "Haircut and styling at salon",
                        "Purchase of personal care products",
                        "Spa treatment and relaxation session"
                });
            case Phone:
                return "Monthly mobile phone bill payment";
            case Rent:
                return "Monthly rental payment for apartment";
            case Savings:
                return "Transfer to personal savings account";
            case Shopping:
                return getRandomItem(new String[]{
                        "Purchase of new clothing items",
                        "Buying gifts for family and friends",
                        "Acquisition of home decor accessories"
                });
            case Subscriptions:
                return getRandomItem(new String[]{
                        "Monthly streaming service subscription fee",
                        "Annual magazine subscription renewal",
                        "Premium membership for online service"
                });
            case ToDebt:
                return "Payment towards outstanding credit card debt";
            default:
                return "General expense";
        }
    }

    private static String getRandomVendor(SpendingCategory category) {
        switch (category) {
            case Automotive:
            case CarPayment:
                return getRandomItem(new String[]{
                        "AutoZone",
                        "Pep Boys",
                        "Firestone Complete Auto Care",
                        "Discount Tire"
                });
            case CarInsurance:
            case HomeInsurance:
                return getRandomItem(new String[]{
                        "State Farm",
                        "GEICO",
                        "Allstate",
                        "Progressive"
                });
            case Education:
                return getRandomItem(new String[]{
                        "Coursera",
                        "Udemy",
                        "Khan Academy",
                        "Local University"
                });
            case Electricity:
                return "Local Electric Utility Company";
            case Fitness:
                return getRandomItem(new String[]{
                        "Planet Fitness",
                        "Gold's Gym",
                        "24 Hour Fitness",
                        "Local Yoga Studio"
                });
            case Gas:
                return getRandomItem(new String[]{
                        "Shell Gas Station",
                        "Chevron",
                        "BP",
                        "ExxonMobil"
                });
            case Grocery:
                return getRandomItem(new String[]{
                        "Whole Foods Market",
                        "Trader Joe's",
                        "Kroger",
                        "Safeway"
                });
            case Internet:
                return getRandomItem(new String[]{
                        "Comcast Xfinity",
                        "AT&T Internet",
                        "Verizon Fios",
                        "Spectrum"
                });
            case Medical:
                return getRandomItem(new String[]{
                        "City Hospital",
                        "Local Pharmacy",
                        "Health Clinic",
                        "Dental Care Center"
                });
            case Mortgage:
                return getRandomItem(new String[]{
                        "Wells Fargo Home Mortgage",
                        "Bank of America Home Loans",
                        "Quicken Loans",
                        "Chase Mortgage"
                });
            case Personal:
                return getRandomItem(new String[]{
                        "Luxury Spa Retreat",
                        "Elite Hair Salon",
                        "Boutique Skincare Shop",
                        "Wellness Center"
                });
            case Phone:
                return getRandomItem(new String[]{
                        "Verizon Wireless",
                        "AT&T Mobility",
                        "T-Mobile",
                        "Sprint"
                });
            case Rent:
                return "Property Management Company";
            case Savings:
                return "Transfer to Savings Account at Bank";
            case Shopping:
                return getRandomItem(new String[]{
                        "Nordstrom",
                        "Amazon",
                        "Best Buy",
                        "IKEA"
                });
            case Subscriptions:
                return getRandomItem(new String[]{
                        "Netflix",
                        "Spotify",
                        "Adobe Creative Cloud",
                        "Amazon Prime"
                });
            case ToDebt:
                return "Credit Card Payment";
            case Other:
                return "Miscellaneous Vendor";
            default:
                return "General Vendor";
        }
    }

    private static String getRandomItem(String[] items) {
        return items[ThreadLocalRandom.current().nextInt(items.length)];
    }
}
