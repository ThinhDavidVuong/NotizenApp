package ch.bbcag.notizenapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Dialog.*;
import Model.*;
import NoteDB.*;
import ch.bbcag.notizenapp.R;

public class SingelListView extends AppCompatActivity {

    private NoteDbHelper nh;
    private NoteController nc;

    private ArrayAdapter ListofTasks;

    private int list_id;
    private String list_name;
    private ArrayList<QuestModel> Tasks = new ArrayList<QuestModel>();

    private SingelListView slv = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singel_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        list_id = Integer.parseInt(intent.getStringExtra("list_id"));
        list_name = intent.getStringExtra("name");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateQuestDialog dlg = new CreateQuestDialog(slv, Bundle.EMPTY, 1);
                String tag = "";
                FragmentManager fm = getFragmentManager();
                dlg.show(fm, tag);
            }
        });

        nh = new NoteDbHelper(this);
        nc = new NoteController(nh);


        setImage();
        setCategoryName();
    }

    private  void setImage(){
        ImageView img = (ImageView) findViewById(R.id.addTask);
        img.setImageResource(R.drawable.ic_add_black_36dp);
    }

    private void setCategoryName(){
        TextView text = (TextView) findViewById(R.id.listname);
        text.setText(list_name);
    }

    private void loadListsInListView() {
        android.widget.ListView ListViewListsodlists = (android.widget.ListView) findViewById(R.id.Listoflists);
        ListofTasks = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        for (int i = 0; i < Tasks.size(); i++) {
            ListofTasks.add(Tasks.get(i).name);
        }

        ListViewListsodlists.setAdapter(ListofTasks);
    }

    public void loadList() {

    }

    @Override
    protected void onDestroy() {
        nh.close();
        super.onDestroy();
    }
}
