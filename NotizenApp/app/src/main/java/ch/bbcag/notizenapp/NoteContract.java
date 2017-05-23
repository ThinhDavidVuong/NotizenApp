package ch.bbcag.notizenapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import static ch.bbcag.notizenapp.R.attr.subtitle;
import static ch.bbcag.notizenapp.R.attr.title;
import static java.security.AccessController.getContext;

/**
 * Created by bvuond on 23.05.2017.
 */

public class NoteContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private NoteContract() {}

    /* Inner class that defines the table contents */
    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry._ID + " INTEGER PRIMARY KEY," +
                    NoteEntry.COLUMN_NAME_NAME + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;

}


