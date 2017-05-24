package ch.bbcag.notizenapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.TaskAdapter;
import Dialog.*;
import Model.*;
import NoteDB.*;

public class SingelListView extends AppCompatActivity {

    private NoteDbHelper nh;
    private NoteController nc;

    private TaskAdapter ListofTasks;

    private int list_id;
    private String list_name;
    private ArrayList<TaskModel> Tasks = new ArrayList<TaskModel>();

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

                CreateTaskDialog dlg = new CreateTaskDialog(slv, Bundle.EMPTY, 1);
                String tag = "";
                FragmentManager fm = getFragmentManager();
                dlg.show(fm, tag);
            }
        });

        nh = new NoteDbHelper(this);
        nc = new NoteController(nh);


        setImage();
        setCategoryName();
        loadList();
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
        android.widget.ListView ListViewListsodlists = (android.widget.ListView) findViewById(R.id.Listoftasks);
        ArrayList<TaskModel> TaskModels = new ArrayList<TaskModel>();

        for (int i = Tasks.size()-1; i >= 0; i--) {
            TaskModels.add(Tasks.get(i));
        }

        ListofTasks = new TaskAdapter(this, TaskModels);

        ListViewListsodlists.setAdapter(ListofTasks);
    }

    public void loadList() {
        Tasks = nc.readAllTasks(list_id);
        Tasks.add(new TaskModel(1, 1, "TESTESS", false));
        loadListsInListView();
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        nc.updateTask(new TaskModel(1,1,"TEST", checked));

    }

    @Override
    protected void onDestroy() {
        nh.close();
        super.onDestroy();
    }
}
