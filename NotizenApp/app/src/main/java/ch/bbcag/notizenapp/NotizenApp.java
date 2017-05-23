package ch.bbcag.notizenapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static ch.bbcag.notizenapp.R.attr.title;
import static java.security.AccessController.getContext;

public class NotizenApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notizen_app);
        insert();
    }

    public void insert() {
        // Gets the data repository in write mode
        NoteDbHelper mDbHelper = new NoteDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_NAME_NAME, title);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(NoteContract.NoteEntry.TABLE_NAME, null, values);
    }
}
