package com.example.finance;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    BottomNavigationView bottomNav;

    BudgetFragment budgetFragment = null;
    ExpenseFragment expenseFragment = null;
    DashboardFragment dashboardFragment = null;

    ExpenseHolder expenseHolder = new ExpenseHolder();
    UserBudget userBudget = new UserBudget(0, "My Budget");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Generating Test data - eventually this will be pull from a db...
//        expenseHolder.setExpenses(readExpenses());
        //generateBudgetItems();

        // db test
        retrieveExpensesFromDB();

        // Update Budget Items with correct usage
        //userBudget.updateBudgetUsage(expenseHolder.getExpenses());

        expenseFragment = ExpenseFragment.newInstance(expenseHolder.getExpenses());
        budgetFragment = BudgetFragment.newInstance(userBudget);
        dashboardFragment = DashboardFragment.newInstance(userBudget);

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(this);
        bottomNav.setSelectedItemId(R.id.budget);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.budget) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, budgetFragment).commit();
            return true;
        }
        else if ( item.getItemId() == R.id.expenses){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, expenseFragment).commit();
            return true;
        }
        else if ( item.getItemId() == R.id.dashboard){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dashboardFragment).commit();
            return true;
        }
            return false;
    }

    private void buildExpenses() {
//        int createAmt = 20;
//
//        for (int i = 0 ; i < createAmt ; i++){
//            Expense e = ExpenseUtils.generateRandomExpense();
//            expenseHolder.addExpense(e);
//            Log.d("ExpenseHolder", e.toString());
//        }

    }

    private void retrieveExpensesFromDB() {
        DataBaseHelper db = new DataBaseHelper(this);
        ArrayList<Expense> expenses = db.getAllExpenses();
        for (Expense e : expenses) {
            Log.d("ExpenseHolder", e.toString());
        }
        expenseHolder.setExpenses(expenses);
    }

    private ArrayList<Expense> readExpenses() {
        ArrayList<Expense> expenses = new ArrayList<Expense>();
        // Read expenses from b1data.csv file
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.b1data);
        BufferedReader in = new BufferedReader( new InputStreamReader(is));

        String line = "";
        String splitBy = ",";
        int idCount = 1;
        try {
            while ((line = in.readLine()) != null) {
                String[] tokens = line.split(splitBy);
                Log.d("readExpenses", line);
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                LocalDate date = LocalDate.parse(tokens[1].trim(), format);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);
                SpendingCategory category = SpendingCategory.valueOf(tokens[5]);
                Expense expense = new Expense(idCount++, date, description, vendor, amount, category);
                expenses.add(expense);
            }
        } catch (Exception e) {
            Log.wtf("readExpenses", e.getMessage());
            e.printStackTrace();
        }

        return expenses;
    }

    // moving this to UserBudget Repo because we're generating everything there.
//    private void generateBudgetItems() {
//        ArrayList<BudgetItem> budgetItems = ExpenseUtils.generateSampleBudget();
//        userBudget.setBudgetItems(budgetItems);
//        userBudget.setCycleAmount(3000.00);
//    }

}