package NoteDB;

import android.provider.BaseColumns;

/**
 * Created by bvuond on 23.05.2017.
 */

public class NoteContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private NoteContract() {}

    /* Inner class that defines the table "Kategorie" */
    public static class CategoryEntry implements BaseColumns {
        public static final String TABLE_CATEGORY = "Kategorie";
        public static final String COLUMN_NAME_CATEGORY = "kategorie_name";
    }

    public static final String SQL_CREATE_CATEGORY =
            "CREATE TABLE " + CategoryEntry.TABLE_CATEGORY + " (" +
                    CategoryEntry._ID + " INTEGER PRIMARY KEY," +
                    CategoryEntry.COLUMN_NAME_CATEGORY + " TEXT)";

    /* Inner class that defines the table "Liste" */
    public static class ListEntry implements BaseColumns {
        public static final String TABLE_LIST = "Liste";
        public static final String COLUMN_NAME_LIST = "liste_name";
        public static final String COLUMN_NAME_CATEGORY_ID = "kategorie_id";
    }

    public static final String SQL_CREATE_LIST =
            "CREATE TABLE " + ListEntry.TABLE_LIST + " (" +
                    ListEntry._ID + " INTEGER PRIMARY KEY," +
                    ListEntry.COLUMN_NAME_LIST + " TEXT," +
                    ListEntry.COLUMN_NAME_CATEGORY_ID + " INTEGER," +
                    "FOREIGN KEY("+ ListEntry.COLUMN_NAME_CATEGORY_ID+") REFERENCES "+CategoryEntry.TABLE_CATEGORY+"("+CategoryEntry._ID+") ON DELETE CASCADE)";

    /* Inner class that defines the table "Aufgabe" */
    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_TASK = "Aufgabe";
        public static final String COLUMN_NAME_TASK = "aufgabe_name";
        public static final String COLUMN_NAME_CHECKED = "isChecked";
        public static final String COLUMN_NAME_LIST_ID = "liste_id";
    }

    public static final String SQL_CREATE_TASK =
            "CREATE TABLE " + TaskEntry.TABLE_TASK + " (" +
                    TaskEntry._ID + " INTEGER PRIMARY KEY," +
                    TaskEntry.COLUMN_NAME_TASK + " TEXT," +
                    TaskEntry.COLUMN_NAME_CHECKED + " INTEGER," +
                    TaskEntry.COLUMN_NAME_LIST_ID + " INTEGER," +
                    "FOREIGN KEY("+ TaskEntry.COLUMN_NAME_LIST_ID+") REFERENCES "+ListEntry.TABLE_LIST+"("+ListEntry._ID+") ON DELETE CASCADE)";


    // Delete Table "Kategorie"
    public static final String SQL_DELETE_CATEGORY =
            "DROP TABLE IF EXISTS " + CategoryEntry.TABLE_CATEGORY;

    // Delete Table "Liste"
    public static final String SQL_DELETE_LIST =
            "DROP TABLE IF EXISTS " + ListEntry.TABLE_LIST;

    // Delete Table "Aufgabe"
    public static final String SQL_DELETE_TASK =
            "DROP TABLE IF EXISTS " + TaskEntry.TABLE_TASK;

    // Enable Foreign Key
    public static final String ENABLE_FOREIGNKEY = "PRAGMA foreign_keys = ON";
}


