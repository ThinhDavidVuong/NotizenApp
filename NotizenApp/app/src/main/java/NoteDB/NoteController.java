package NoteDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import Model.CategoryModel;
import Model.ListModel;
import Model.TaskModel;

/**
 * Created by bvuond on 23.05.2017.
 */

public class NoteController {

    NoteDbHelper mDbHelper;
    SQLiteDatabase db;

    // Starts the DB and enables Foreign Key
    public NoteController(NoteDbHelper mDbHelper) {
        this.mDbHelper = mDbHelper;
        this.db = mDbHelper.getReadableDatabase();
        db.execSQL(NoteContract.ENABLE_FOREIGNKEY);
    }

    //Category functions
    public void insertCategory(String input) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY, input);

        /*
            Insert the new row, returning the primary key value of the new row
            INSERT INTO TABLE_CATEGORY (COLUMN_NAME_CATEGORY)
            VALUES (input);
        */
        db.insert(NoteContract.CategoryEntry.TABLE_CATEGORY, null, values);
    }

    public ArrayList<CategoryModel> readAllCategories() {
        /* Specifies which columns from the database
           you will actually use after this query.
           SELECT _ID, COLUMN_NAME_CATEGORY FROM ... */
        String[] projection = {
                NoteContract.CategoryEntry._ID,
                NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY
        };

        // WHERE ...
        String selection = "";
        String[] selectionArgs = { };

        // How you want the results sorted in the resulting Cursor (1, 2, 3..)
        String sortOrder =
                NoteContract.CategoryEntry._ID + " ASC";

        Cursor cursor = db.query(
                NoteContract.CategoryEntry.TABLE_CATEGORY,// The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        // For each row, you can read a column's value by calling one of the Cursor get methods.
        ArrayList<CategoryModel> allCategories = new ArrayList<CategoryModel>();
        while(cursor.moveToNext()) {
            int category_id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(NoteContract.CategoryEntry._ID));
            String category_name = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY));

            allCategories.add(new CategoryModel(category_id, category_name));
        }
        cursor.close();
        return allCategories;
    }

    public void updateCategory(CategoryModel cm) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(NoteContract.CategoryEntry.COLUMN_NAME_CATEGORY, cm.name);

        // Which row to update, based on the title
        String selection = NoteContract.CategoryEntry._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(cm.id) };

        /*
            UPDATE TABLE_CATEGORY
            SET COLUMN_NAME_CATEGORY = cm.name
            WHERE selection = selectionArgs;
        */
        db.update(
                NoteContract.CategoryEntry.TABLE_CATEGORY,
                values,
                selection,
                selectionArgs);
    }

    public void deleteCategory(int category_id) {
        // Define 'where' part of query.
        String selection = NoteContract.CategoryEntry._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { Integer.toString(category_id) };
        // Issue SQL statement.
        // DELETE FROM TABLE_CATEGORY WHERE selection = selectionArgs
        db.delete(NoteContract.CategoryEntry.TABLE_CATEGORY, selection, selectionArgs);
    }

    //List functions
    public void insertList(int category_id, String input) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteContract.ListEntry.COLUMN_NAME_LIST, input);
        values.put(NoteContract.ListEntry.COLUMN_NAME_CATEGORY_ID, category_id);

        db.insert(NoteContract.ListEntry.TABLE_LIST, null, values);
    }

    public ArrayList<ListModel> readAllLists(int category_id) {
        String[] projection = {
                NoteContract.ListEntry._ID,
                NoteContract.ListEntry.COLUMN_NAME_LIST
        };

        // Filter results WHERE "kategorie_id" = 'id (Kategorie)'
        String selection = NoteContract.ListEntry.COLUMN_NAME_CATEGORY_ID + " = ?";
        String[] selectionArgs = { Integer.toString(category_id) };

        String sortOrder =
                NoteContract.ListEntry._ID + " ASC";

        Cursor cursor = db.query(
                NoteContract.ListEntry.TABLE_LIST,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        ArrayList<ListModel> allLists = new ArrayList<>();
        while(cursor.moveToNext()) {
            int list_id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(NoteContract.ListEntry._ID));
            String list_name = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.ListEntry.COLUMN_NAME_LIST));

            allLists.add(new ListModel(list_id, category_id, list_name));
        }
        cursor.close();
        return allLists;
    }

    public void updateList(ListModel lm) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteContract.ListEntry.COLUMN_NAME_LIST, lm.name);

        String selection = NoteContract.ListEntry._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(lm.id) };

        db.update(
                NoteContract.ListEntry.TABLE_LIST,
                values,
                selection,
                selectionArgs);
    }

    public void deleteList(int list_id) {
        String selection = NoteContract.ListEntry._ID + " LIKE ?";

        String[] selectionArgs = { Integer.toString(list_id) };

        db.delete(NoteContract.ListEntry.TABLE_LIST, selection, selectionArgs);
    }

    //Task functions
    public void insertTask(int list_id, String input) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteContract.TaskEntry.COLUMN_NAME_TASK, input);
        values.put(NoteContract.TaskEntry.COLUMN_NAME_LIST_ID, list_id);
        values.put(NoteContract.TaskEntry.COLUMN_NAME_CHECKED, 0);

        db.insert(NoteContract.TaskEntry.TABLE_TASK, null, values);
    }

    public ArrayList<TaskModel> readAllTasks(int list_id) {
        String[] projection = {
                NoteContract.TaskEntry._ID,
                NoteContract.TaskEntry.COLUMN_NAME_TASK,
                NoteContract.TaskEntry.COLUMN_NAME_CHECKED
        };

        String selection = NoteContract.TaskEntry.COLUMN_NAME_LIST_ID + " = ?";
        String[] selectionArgs = { Integer.toString(list_id) };

        String sortOrder =
                NoteContract.TaskEntry._ID + " ASC";

        Cursor cursor = db.query(
                NoteContract.TaskEntry.TABLE_TASK,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        ArrayList<TaskModel> allTasks = new ArrayList<>();
        while(cursor.moveToNext()) {
            int quest_id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(NoteContract.TaskEntry._ID));
            String task_name = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.TaskEntry.COLUMN_NAME_TASK));
            int task_checked = cursor.getInt(
                    cursor.getColumnIndexOrThrow(NoteContract.TaskEntry.COLUMN_NAME_CHECKED));

            boolean state;
            if (task_checked == 1) {
                state = true;
            } else {
                state = false;
            }

            allTasks.add(new TaskModel(quest_id, list_id, task_name, state));
        }
        cursor.close();
        return allTasks;
    }

    public void updateTask(TaskModel qm) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteContract.TaskEntry.COLUMN_NAME_TASK, qm.name);

        String selection = NoteContract.TaskEntry._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(qm.id) };

        db.update(
                NoteContract.TaskEntry.TABLE_TASK,
                values,
                selection,
                selectionArgs);
    }

    public void updateTaskState(TaskModel qm) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        /*
        We use 0 and 1 as true and false.
        Because MySQLi doesn't know boolean state.
         */
        int zahl = 0;
        if (qm.isChecked == true) {
            zahl = 1;
        } else {
            zahl = 0;
        }

        ContentValues values = new ContentValues();
        values.put(NoteContract.TaskEntry.COLUMN_NAME_CHECKED, zahl);

        String selection = NoteContract.TaskEntry._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(qm.id) };

        db.update(
                NoteContract.TaskEntry.TABLE_TASK,
                values,
                selection,
                selectionArgs);
    }

    public void deleteTask(int task_id) {
        String selection = NoteContract.TaskEntry._ID + " LIKE ?";

        String[] selectionArgs = { Integer.toString(task_id) };

        db.delete(NoteContract.TaskEntry.TABLE_TASK, selection, selectionArgs);
    }
}
