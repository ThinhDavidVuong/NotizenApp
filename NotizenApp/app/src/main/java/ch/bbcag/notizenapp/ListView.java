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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Dialog.CreateCategoryDialog;
import Dialog.CreateListDialog;
import Dialog.DeleteCategoryDialog;
import Dialog.UpdateCategoryDialog;
import Model.CategoryModel;
import Model.ListModel;
import NoteDB.NoteController;
import NoteDB.NoteDbHelper;

public class ListView extends AppCompatActivity {

    private NoteDbHelper nh;
    private NoteController nc;

    private ArrayAdapter ListofLists;

    private int category_id;
    private String category_name;
    private ArrayList<ListModel> Listen = new ArrayList<ListModel>();

    private ListView lv = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        category_id = Integer.parseInt(intent.getStringExtra("category_id"));
        category_name = intent.getStringExtra("name");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateListDialog dlg = new CreateListDialog(lv, Bundle.EMPTY, category_id);
                String tag = "";
                FragmentManager fm = getFragmentManager();
                dlg.show(fm, tag);
            }
        });

        nh = new NoteDbHelper(this);
        nc = new NoteController(nh);

        setImage();
        updateCategoryname();
        setCategoryName();
        loadList();
    }

    private  void setImage(){
        ImageView img = (ImageView) findViewById(R.id.addList);
        img.setImageResource(R.drawable.ic_add_black_36dp);
    }

    private void setCategoryName(){
        TextView text = (TextView) findViewById(R.id.categoryname);
        text.setText(category_name);
    }

    private void loadListsInListView() {

        android.widget.ListView ListViewListsodlists = (android.widget.ListView) findViewById(R.id.Listoflists);
        ListofLists = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        for(int i = Listen.size()-1; i >= 0; i--){
            ListofLists.add(Listen.get(i).name);
        }
        
        ListViewListsodlists.setAdapter(ListofLists);
        AdapterView.OnItemClickListener mListClickedHandler = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SingelListView.class);
                String nameofselected = parent.getItemAtPosition(position).toString();

                for(int i = Listen.size()-1; i >= 0; i--){
                    if(Listen.get(i).name == nameofselected){
                        intent.putExtra("list_id", Integer.toString(Listen.get(i).id));
                    }
                }

                Toast.makeText(ListView.this, nameofselected, Toast.LENGTH_SHORT).show();

                intent.putExtra("category_id", Integer.toString(category_id));
                intent.putExtra("name", nameofselected);
                startActivity(intent);
            }
        };
        ListViewListsodlists.setOnItemClickListener(mListClickedHandler);

    }

    public void loadList(){
        Listen = nc.readAllLists(category_id);
        loadListsInListView();
    }

    public void updateCategoryname(){
        ArrayList<CategoryModel> Categories = nc.readAllCategories();
        for(int i = 0; i < Categories.size(); i++){
            if(Categories.get(i).id == category_id){
                category_name = Categories.get(i).name;
            }
        }
        setCategoryName();
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
                UpdateCategoryDialog udlg = new UpdateCategoryDialog(lv, Bundle.EMPTY, category_id, category_name);
                String tag = "";
                FragmentManager fm = getFragmentManager();
                udlg.show(fm, tag);
                return true;

            case R.id.action_delete:
                DeleteCategoryDialog ddlg = new DeleteCategoryDialog(lv, Bundle.EMPTY, category_id);
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
