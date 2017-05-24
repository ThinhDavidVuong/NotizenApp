package NoteDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Model.CategoryModel;
import Model.ListModel;
import Model.QuestModel;

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

    public void insertCategory(String input) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY, input);

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
            int category_id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(NoteContract.CategoryEntry._ID));
            String category_name = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY));

            allCategory.add(new CategoryModel(category_id, category_name));

            // Meldung der durchlaufenden Elemente
            Log.e("@@@@", category_name);
        }
        cursor.close();
        return allCategory;
    }

    public void insertList(int category_id, String input) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NoteContract.ListEntry.COLUMN_NAME_LIST, input);
        values.put(NoteContract.ListEntry.COLUMN_NAME_CATEGORY_ID, category_id);

        // Insert the new row, returning the primary key value of the new row
        db.insert(NoteContract.ListEntry.TABLE_LIST, null, values);
    }

    public ArrayList<ListModel> readAllLists(int category_id) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NoteContract.ListEntry._ID,
                NoteContract.ListEntry.COLUMN_NAME_LIST
        };

        // Filter results WHERE "kategorie_id" = '2'
        String selection = NoteContract.ListEntry.COLUMN_NAME_CATEGORY_ID + " = ?";
        String[] selectionArgs = { Integer.toString(category_id) };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                NoteContract.ListEntry.COLUMN_NAME_LIST + " DESC";

        Cursor cursor = db.query(
                NoteContract.ListEntry.TABLE_LIST,        // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        // For each row, you can read a column's value by calling one of the Cursor get methods.
        ArrayList<ListModel> allCategory = new ArrayList<>();
        while(cursor.moveToNext()) {
            int list_id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(NoteContract.ListEntry._ID));
            String list_name = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.ListEntry.COLUMN_NAME_LIST));

            allCategory.add(new ListModel(list_id, category_id, list_name));

            // Meldung der durchlaufenden Elemente
            Log.e("@@@@", list_name);
        }
        cursor.close();
        return allCategory;
    }

    public void insertTask(int list_id, String input) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NoteContract.Task.COLUMN_NAME_TASK, input);
        values.put(NoteContract.Task.COLUMN_NAME_LIST_ID, list_id);
        values.put(NoteContract.Task.COLUMN_NAME_CHECKED, 0);

        // Insert the new row, returning the primary key value of the new row
        db.insert(NoteContract.ListEntry.TABLE_LIST, null, values);
    }

    public ArrayList<QuestModel> readAllTasks(int list_id) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NoteContract.Task._ID,
                NoteContract.Task.COLUMN_NAME_TASK,
                NoteContract.Task.COLUMN_NAME_CHECKED
        };

        // Filter results WHERE "liste_id" = 'id(Liste)'
        String selection = NoteContract.Task.COLUMN_NAME_LIST_ID + " = ?";
        String[] selectionArgs = { Integer.toString(list_id) };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                NoteContract.Task.COLUMN_NAME_TASK + " DESC";

        Cursor cursor = db.query(
                NoteContract.Task.TABLE_TASK,        // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        // For each row, you can read a column's value by calling one of the Cursor get methods.
        ArrayList<QuestModel> allCategory = new ArrayList<>();
        while(cursor.moveToNext()) {
            int quest_id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(NoteContract.Task._ID));
            String task_name = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.Task.COLUMN_NAME_TASK));
            int task_checked = cursor.getInt(
                    cursor.getColumnIndexOrThrow(NoteContract.Task.COLUMN_NAME_CHECKED));

            boolean state;
            if (task_checked == 1) {
                state = true;
            } else {
                state = false;
            }

            allCategory.add(new QuestModel(quest_id, list_id, task_name, state));

            // Meldung der durchlaufenden Elemente
            Log.e("@@@@", task_name);
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
