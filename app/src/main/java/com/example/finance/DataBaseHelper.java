package com.example.finance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_EXPENSE_DATE = "EXPENSE_DATE";
    public static final String COLUMN_EXPENSE_VENDOR = "EXPENSE_VENDOR";
    public static final String COLUMN_EXPENSE_DESCRIPTION = "EXPENSE_DESCRIPTION";
    public static final String COLUMN_EXPENSE_AMOUNT = "EXPENSE_AMOUNT";
    public static final String COLUMN_EXPENSE_CATEGORY = "EXPENSE_CATEGORY";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "porky.db", null, 1);
    }

    // This called the first time a database is accessed.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Expense table
        String createExpenseTableStatement = "CREATE TABLE " + TABLE_EXPENSE_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_EXPENSE_DATE + " TEXT," +
                COLUMN_EXPENSE_VENDOR + " TEXT," +
                COLUMN_EXPENSE_DESCRIPTION + " TEXT," +
                COLUMN_EXPENSE_AMOUNT + " REAL," +
                COLUMN_EXPENSE_CATEGORY + " TEXT)";

        sqLiteDatabase.execSQL(createExpenseTableStatement);

    }

    // this called if the db version number changes - upgrade schema
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Insert Expense
    public boolean addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXPENSE_DATE, expense.toSQLDateString());
        cv.put(COLUMN_EXPENSE_VENDOR, expense.getVendor());
        cv.put(COLUMN_EXPENSE_DESCRIPTION, expense.getDescription());
        cv.put(COLUMN_EXPENSE_AMOUNT, expense.getAmount());
        cv.put(COLUMN_EXPENSE_CATEGORY, expense.getCategory().toString());

        long insert = db.insert(TABLE_EXPENSE_TABLE, null, cv);

        if (insert == -1) {
            return false;
        }
        return true;
    }

    // Get all Expenses
    public ArrayList<Expense> getAllExpenses() {
        ArrayList<Expense> list = new ArrayList<>();

        String queryString = "SELECT * FROM " + TABLE_EXPENSE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery(queryString, null);
            if (cursor.moveToFirst()) {
                // Create expense for each row in the result set
                do {
                    int id = cursor.getInt(0);
                    String date = cursor.getString(1);
                    String vendor = cursor.getString(2);
                    String description = cursor.getString(3);
                    double amount = cursor.getDouble(4);
                    String category = cursor.getString(5);

                    Expense expense = new Expense(id, LocalDate.parse(date), vendor, description, amount, SpendingCategory.valueOf(category));
                    list.add(expense);
                } while (cursor.moveToNext());
            }
            else {
                Log.wtf("Expense Database", "Error in expense table ");
            }
        }
        catch (Exception e) {
            Log.wtf("Expense Database", "Error in expense table row");
        }
        finally {
            db.close();
        }


        return list;
    }
}
