package ch.bbcag.notizenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.CategoryModel;
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

        controller.insertCategory();
        ArrayList<CategoryModel> allCategory = controller.readAllCategories();

        controller.insertList(allCategory.get(1).id);
        controller.readAllLists(allCategory.get(1).id);
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
