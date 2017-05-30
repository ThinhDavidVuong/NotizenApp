package NoteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bvuond on 23.05.2017.
 */

public class NoteDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Note.db";

    // Create a database with a database version.
    // Set activity as context to use the database.
    public NoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create tables
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteContract.SQL_CREATE_CATEGORY);
        db.execSQL(NoteContract.SQL_CREATE_LIST);
        db.execSQL(NoteContract.SQL_CREATE_TASK);
    }

    // Use this when something on the table is changed.
    // Create, delete or alter table
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Gives the old version of the database.
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
