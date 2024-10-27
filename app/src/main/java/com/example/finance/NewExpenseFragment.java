package com.example.finance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewExpenseFragment extends Fragment {
    Button datePickerButton;
    TextView text;

    Button returnButton;
    Spinner categorySpinner;

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


        // Date Picker Button
        text = view.findViewById(R.id.date_picker_label);
        datePickerButton = view.findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });

        // Initialize Category Spinner
        initCategorySpinner(view);

        return view;
    }

    private void openDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                text.setText(String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
            };
        }, 2023, 0, 1);

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
