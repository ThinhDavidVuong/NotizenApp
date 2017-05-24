package View;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import Dialog.*;
import Model.*;
import NoteDB.*;
import ch.bbcag.notizenapp.R;

public class SingelListView extends AppCompatActivity {

    private NoteDbHelper nh;
    private NoteController nc;

    private ArrayAdapter ListofTasks;

    private int category_id;
    private String category_name;
    private ArrayList<ListModel> Listen = new ArrayList<ListModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singel_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addQuest);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateQuestDialog dlg = new CreateQuestDialog();
                String tag = "";
                FragmentManager fm = getFragmentManager();
                dlg.show(fm, tag);
            }
        });

        nh = new NoteDbHelper(this);
        nc = new NoteController(nh);


        setImage();
    }

    private  void setImage(){
        ImageView img = (ImageView) findViewById(R.id.addTask);
        img.setImageResource(R.drawable.ic_add_black_36dp);
    }

    @Override
    protected void onDestroy() {
        nh.close();
        super.onDestroy();
    }

}
