package ch.bbcag.notizenapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static ch.bbcag.notizenapp.R.attr.title;

public class NotizenApp extends AppCompatActivity {

    NoteDbHelper mDbHelper;
    NoteController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDbHelper = new NoteDbHelper(this);
        this.controller = new NoteController(mDbHelper);
        setContentView(R.layout.activity_notizen_app);
        controller.insert();
        List itemIds = controller.readDB();
        for(int i = 0; i < itemIds.size(); i++)
        {
            Toast.makeText(this, itemIds.get(i).toString(), Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
