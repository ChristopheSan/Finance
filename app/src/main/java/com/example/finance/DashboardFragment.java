package com.example.finance;

import static java.lang.System.exit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment {
    private UserBudget userBudget;
    private PieChart pieChart;
    private View view = null;

    private UserBudgetViewModel userBudgetViewModel;
    private ExpenseViewModel expenseViewModel;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(UserBudget userBudget) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putParcelable("userBudget", userBudget);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_dashboard, container, false);

            // Initialize the ViewModels
            userBudgetViewModel = new ViewModelProvider(requireActivity()).get(UserBudgetViewModel.class);
            expenseViewModel = new ViewModelProvider(requireActivity()).get(ExpenseViewModel.class);

            // Initialize UI components
            pieChart = view.findViewById(R.id.spendingPieChart);

            // Observe the UserBudget LiveData
            userBudgetViewModel.getBudget().observe(getViewLifecycleOwner(), new Observer<UserBudget>() {
                @Override
                public void onChanged(UserBudget userBudget) {
                    if (userBudget != null) {
                        DashboardFragment.this.userBudget = userBudget;
                        updateQuickGlance();
                        buildPieChart();
                        pieChart.invalidate();
                    }
                }
            });

            // Observe the Expense LiveData
            expenseViewModel.getExpenses().observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {
                        @Override
                        public void onChanged(List<Expense> expenses) {
                            if (userBudget != null) {
                                userBudgetViewModel.updateBudget();
                            }
                        }
        });


            UserBudget currentBudget = userBudgetViewModel.getBudget().getValue();
            if (currentBudget != null) {
                DashboardFragment.this.userBudget = currentBudget;
                updateQuickGlance();
                buildPieChart();
                pieChart.invalidate();
            }

            return view;
        } catch (Exception e) {
            Log.wtf("DashboardFragment", "Something went very wrong: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateQuickGlance();
        refresh();
    }

    public void update(ArrayList<Expense> expenses) {
        //userBudget.updateBudgetUsage(expenses);
        refresh();
    }

    public void refresh() {
        updateQuickGlance();
        buildPieChart();
        pieChart.invalidate();
    }


    private void updateQuickGlance() {
        // Update the quick glance text views

        if (userBudget == null) {
            return;
        }

        // Utilization
        TextView utilizationVal = view.findViewById(R.id.currentUtilizationValueTextView);
        utilizationVal.setText(String.format("$ %.2f / $ %.2f", userBudget.getTotalUsedAmount(), userBudget.getCycleAmount()));

        // Unallocated Spending
        TextView unallocatedVal = view.findViewById(R.id.unallocatedSpendingValueTextView);
        BudgetItem unallocated = null;

        if (userBudget.getBudgetItem(SpendingCategory.Other) != null){
            unallocated = userBudget.getBudgetItem(SpendingCategory.Other);
        }
        else {
            unallocated = new BudgetItem(0, "Unallocated Spending", 0, SpendingCategory.Other);
            userBudget.addBudgetItem(unallocated);
//            Log.wtf("DashboardFragment", "No unallocated BudgetItem we have an issue");
//            exit(0);
        }
        unallocatedVal.setText(String.format("$ %.2f", unallocated.getUsedAmount()));

        // Largest Category
        TextView largestCategoryVal = view.findViewById(R.id.largestCategoryValueTextView);
        largestCategoryVal.setText(userBudget.getMostUsedItem().getLabel());

        // # of categories fully used
        TextView numFullyUsedVal = view.findViewById(R.id.numFullyUsedValueTextView);
        numFullyUsedVal.setText(String.valueOf(userBudget.getNumberOfFullyUtilizedItems()));

        // # of categories underutilized
        TextView numUnderutilizedVal = view.findViewById(R.id.numUnderutilizedValueTextView);
        numUnderutilizedVal.setText(String.valueOf(userBudget.getNumberOfUnderutilizedItems()));
    }
    private void buildPieChart() {

        PieDataSet pieDataSet = new PieDataSet(getPieEntries(), "Current Spending");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueFormatter(new PercentFormatter());

        int[] pieColors = getPieColors();
        pieDataSet.setColors(pieColors);
        pieChart.setData(new PieData(pieDataSet));
        pieChart.setExtraOffsets(5, 0, 5, 5);

        pieChart.setDrawEntryLabels(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterTextSize(16f);
        pieChart.setEntryLabelTextSize(10f);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(10f);
        //legend.setPosition(Legend.LegendPosition.CENTER_BOTTOM);
        legend.setTypeface(Typeface.SERIF);
        legend.setYEntrySpace(5f);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        pieChart.invalidate();
    }

    private ArrayList<PieEntry> getPieEntries() {
        userBudget.updateBudgetUsage(userBudget.getExpenses());
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (BudgetItem item : userBudget.getBudgetItems()) {
            pieEntries.add(new PieEntry((float) item.getUsedAmount(), item.getLabel()));
            //pieEntries.add(new PieEntry(30f, item.getLabel())); test values
        }
        return pieEntries;
    }

    private int[] getPieColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        int[] c = new int[colors.size()];
        for (int i = 0; i < colors.size(); i++){
            c[i] = colors.get(i);
        }
        return c;
    }

}