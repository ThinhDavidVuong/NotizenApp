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
                    "FOREIGN KEY("+ ListEntry.COLUMN_NAME_CATEGORY_ID+") REFERENCES "+CategoryEntry.TABLE_CATEGORY+"("+CategoryEntry._ID+")";

    /* Inner class that defines the table "Aufgabe" */
    public static class Task implements BaseColumns {
        public static final String TABLE_TASK = "Aufgabe";
        public static final String COLUMN_NAME_TASK = "aufgabe_name";
        public static final String COLUMN_NAME_CHECKED = "isChecked";
        public static final String COLUMN_NAME_LIST_ID = "liste_id";
    }

    public static final String SQL_CREATE_TASK =
            "CREATE TABLE " + Task.TABLE_TASK + " (" +
                    Task._ID + " INTEGER PRIMARY KEY," +
                    Task.COLUMN_NAME_TASK + " TEXT," +
                    Task.COLUMN_NAME_CHECKED + " BLOB," +
                    "FOREIGN KEY("+ Task.COLUMN_NAME_LIST_ID+") REFERENCES "+ListEntry.TABLE_LIST+"("+ListEntry._ID+")";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CategoryEntry.TABLE_CATEGORY;
}


