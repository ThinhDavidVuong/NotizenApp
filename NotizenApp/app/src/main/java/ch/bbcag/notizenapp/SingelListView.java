package ch.bbcag.notizenapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private int catagory_id;
    private String list_name;
    private ArrayList<TaskModel> Tasks = new ArrayList<TaskModel>();

    private SingelListView slv = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singel_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.singellist_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        list_id = Integer.parseInt(intent.getStringExtra("list_id"));
        catagory_id = Integer.parseInt(intent.getStringExtra("category_id"));
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
        setListName();
        loadList();
    }

    private  void setImage(){
        ImageView img = (ImageView) findViewById(R.id.addTask);
        img.setImageResource(R.drawable.ic_add_black_36dp);
    }

    private void setListName(){
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
        loadListsInListView();
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        TaskModel tm = (TaskModel)view.getTag();

        nc.updateTaskState(new TaskModel(tm.id, tm.list_id, tm.name, checked));
    }

    public void updateListname(){
        ArrayList<ListModel> Lists = nc.readAllLists(catagory_id);
        for(int i = 0; i < Lists.size(); i++){
            if(Lists.get(i).id == list_id){
                list_name = Lists.get(i).name;
            }
        }
        setListName();
    }

    @Override
    protected void onDestroy() {
        nh.close();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_edit:
                UpdateListDialog udlg = new UpdateListDialog(slv, Bundle.EMPTY, list_id, catagory_id, list_name);
                String tag = "";
                FragmentManager fm = getFragmentManager();
                udlg.show(fm, tag);
                return true;

            case R.id.action_delete:
                DeleteListDialog ddlg = new DeleteListDialog(slv, Bundle.EMPTY, list_id);
                String dtag = "";
                FragmentManager dfm = getFragmentManager();
                ddlg.show(dfm, dtag);
                return true;

            case R.id.action_overview:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...

                Intent intent = new Intent(this, CategoryView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
