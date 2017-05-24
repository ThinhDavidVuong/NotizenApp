package ch.bbcag.notizenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import Model.CategoryModel;
import Model.ListModel;
import NoteDB.NoteController;
import NoteDB.NoteDbHelper;

public class NotizenApp extends AppCompatActivity {

    NoteDbHelper mDbHelper;
    NoteController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDbHelper = new NoteDbHelper(this);
        this.controller = new NoteController(mDbHelper);
        setContentView(R.layout.activity_notizen_app);
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
