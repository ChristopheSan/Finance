package com.example.finance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class NewExpenseFragment extends Fragment {
    Button returnButton;

    Button datePickerButton;
    TextView dateText;

    EditText descriptionEditText;
    EditText amountEditText;
    EditText vendorEditText;

    Spinner categorySpinner;

    Button submitButton;
    DatePickerDialog datePicker;

    ExpenseFragment parentFragment;

    public NewExpenseFragment( ExpenseFragment parentFragment)  {
        this.parentFragment = parentFragment;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_expense_layout, container, false);


        // Return Button to go back to the expense view screen
        returnButton = view.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to expense view screen
                returnToExpenseView();
            }
        });

        // Initialize Edit Texts
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        amountEditText = view.findViewById(R.id.amountEditText);
        vendorEditText = view.findViewById(R.id.VendorEditText);

        // Date Picker Button
        dateText = view.findViewById(R.id.displayDateTextView);
        Calendar calendar = Calendar.getInstance();
        dateText.setText(((calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR)));

        datePickerButton = view.findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });

        // Initialize Category Spinner
        initCategorySpinner(view);

        // initialize submit button
        submitButton = view.findViewById(R.id.submitExpenseButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                buildAndSubmitExpense();
            }
        });

        return view;
    }

    private void buildAndSubmitExpense() {
        Expense expense = new Expense();
        //

        LocalDate date = LocalDate.parse(dateText.getText().toString(), DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        Log.d("LocalDate creation","Date: " + date);

        String description = "";
        if (descriptionEditText.getText().toString().equals("")) {
            // toast message to enter a description
            Toast.makeText(getContext(), "Please enter a description", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            description = descriptionEditText.getText().toString();
            Log.d("Description","Description: " + description);
        }

        double amount = 0;
        if (amountEditText.getText().toString().equals("")) {
            // toast message to enter an amount
            Toast.makeText(getContext(), "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            amount = Double.parseDouble(amountEditText.getText().toString());
            Log.d("Amount","Amount: " + amount);
        }

        String vendor = "";
        if (vendorEditText.getText().toString().equals("")) {
            // toast message to enter a vendor
            Toast.makeText(getContext(), "Please enter a vendor", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            vendor = vendorEditText.getText().toString();
            Log.d("Vendor","Vendor: " + vendor);
        }

        SpendingCategory category = SpendingCategory.valueOf(categorySpinner.getSelectedItem().toString());
        Log.d("Category","Category: " + category);

        Expense newExpense = new Expense(date, description, vendor, amount, category);
        Log.d("New Expense", newExpense.toString());

        sendToDB(newExpense);
        resetTextFields();
        Toast.makeText(getContext(), "New Expense Added", Toast.LENGTH_SHORT).show();

        //update the expense list
        parentFragment.addNewExpense(newExpense);

        //Update Dashboard
        ArrayList<Expense> expenses = parentFragment.getExpenses();

    }

    private void resetTextFields() {
        descriptionEditText.setText("");
        amountEditText.setText("");
        vendorEditText.setText("");

        Calendar calendar = Calendar.getInstance();
        dateText.setText(((calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR)));
    }

    private void sendToDB(Expense newExpense) {
        // TODO: Send to DB
        DataBaseHelper db = new DataBaseHelper(getContext());
        boolean success = db.addExpense(newExpense);
        if (success) {
            // update main expenses
        }
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH); // Starts at 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                dateText.setText((String.valueOf(month)) + "-" + String.valueOf(day) + "-" + String.valueOf(year));
            };
        }, year, month, day); // This is the initial date that is displayed/selected

        dialog.show();
    }

    private void returnToExpenseView() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(this);
        transaction.commit();
        parentFragment.setAlpha(1);
    }

    private void initCategorySpinner(View thisView) {
        SpendingCategoryMap categoryMap = new SpendingCategoryMap();
        categorySpinner = thisView.findViewById(R.id.categorySpinner);

        //Build String list of categories
        ArrayList<String> categoryList = new ArrayList<>();
        for (SpendingCategory category : SpendingCategory.values()) {
            categoryList.add(categoryMap.getCategoryName(category));
        }

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_dropdown_item, categoryList);
        categorySpinner.setAdapter(spinnerAdapter);
    }

    private class SpendingCategoryMap {
        public final Map<SpendingCategory, String> CATEGORY_MAP = new HashMap<>();

        public SpendingCategoryMap() {
            for (SpendingCategory category : SpendingCategory.values()) {
                CATEGORY_MAP.put(category, category.toString());
            }
        }

        public String getCategoryName(SpendingCategory category) {
            return CATEGORY_MAP.get(category);
        }

        public SpendingCategory getSpendingCategory(String categoryName) {
            for (Map.Entry<SpendingCategory, String> entry : CATEGORY_MAP.entrySet()) {
                if (entry.getValue().equals(categoryName)) {
                    return entry.getKey();
                }
            }
            return null;
        }

    }
}
