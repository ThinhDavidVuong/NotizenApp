package NoteDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Model.CategoryModel;

/**
 * Created by bvuond on 23.05.2017.
 */

public class NoteController {

    NoteDbHelper mDbHelper;
    SQLiteDatabase db;

    public NoteController(NoteDbHelper mDbHelper) {
        this.mDbHelper = mDbHelper;
        this.db = mDbHelper.getReadableDatabase();
    }

    public void insertCategory() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String name = "Schule";
        ContentValues values = new ContentValues();
        values.put(NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY, name);

        // Insert the new row, returning the primary key value of the new row
        db.insert(NoteContract.CategoryEntry.TABLE_CATEGORY, null, values);
    }

    public ArrayList<CategoryModel> readAllCategories() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NoteContract.CategoryEntry._ID,
                NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY
        };

        // Filter results WHERE "Kategorie" = "meine_notizen"
        String selection = "";
        String[] selectionArgs = { };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY + " DESC";

        Cursor cursor = db.query(
                NoteContract.CategoryEntry.TABLE_CATEGORY,        // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        // For each row, you can read a column's value by calling one of the Cursor get methods.
        ArrayList<CategoryModel> allCategory = new ArrayList<CategoryModel>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(NoteContract.CategoryEntry._ID));
            String category_name = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY));

            allCategory.add(new CategoryModel(id, category_name));

            Log.e("@@@@", category_name);
        }
        cursor.close();
        return allCategory;
    }

    public void insertList(int id) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String name = "Hausaufgaben";
        ContentValues values = new ContentValues();
        values.put(NoteContract.ListEntry.COLUMN_NAME_LIST, name);
        values.put(NoteContract.ListEntry.COLUMN_NAME_CATEGORY_ID, id);

        // Insert the new row, returning the primary key value of the new row
        db.insert(NoteContract.ListEntry.TABLE_LIST, null, values);
    }

    public List readAllLists(int id) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NoteContract.ListEntry._ID,
                NoteContract.ListEntry.COLUMN_NAME_LIST
        };

        // Filter results WHERE "kategorie_id" = '2'
        String selection = NoteContract.ListEntry.COLUMN_NAME_CATEGORY_ID + " = ?";
        String[] selectionArgs = { Integer.toString(id) };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                NoteContract.ListEntry.COLUMN_NAME_CATEGORY + " DESC";

        Cursor cursor = db.query(
                NoteContract.ListEntry.TABLE_CATEGORY,        // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        // For each row, you can read a column's value by calling one of the Cursor get methods.
        List allCategory = new ArrayList<>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(NoteContract.ListEntry._ID));
            String category_name = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.ListEntry.COLUMN_NAME_CATEGORY));

            allCategory.add(new CategoryModel(id, category_name));
            Log.e("@@@@", category_name);
        }
        cursor.close();
        return allCategory;
    }


    // =============================================================================================
    // Wird noch nicht gebraucht.
    public void deleteDB() {
        // Define 'where' part of query.
        String selection = NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { "Meine Notizen" };
        // Issue SQL statement.
        db.delete(NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY, selection, selectionArgs);
    }

    public void updateDB() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        String name = "Kochen";
        ContentValues values = new ContentValues();
        values.put(NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY, name);

        // Which row to update, based on the title
        String selection = NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY + " LIKE ?";
        String[] selectionArgs = { "Meine Notizen" };

        int count = db.update(
                NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY,
                values,
                selection,
                selectionArgs);
    }
}