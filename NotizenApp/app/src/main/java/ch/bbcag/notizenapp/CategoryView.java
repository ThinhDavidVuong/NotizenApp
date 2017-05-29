package ch.bbcag.notizenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import NoteDB.*;
import Model.*;
import Dialog.*;

public class CategoryView extends AppCompatActivity {

    private NoteDbHelper nh;
    private NoteController nc;

    ArrayAdapter ListofCategories;

    private ArrayList<CategoryModel> Categories = new ArrayList<CategoryModel>();

    private CategoryView cv = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NotizApp");


        nh = new NoteDbHelper(this);
        nc = new NoteController(nh);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addCategory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateCategoryDialog dlg = new CreateCategoryDialog(cv, Bundle.EMPTY);
                String tag = "";
                FragmentManager fm = getFragmentManager();
                dlg.show(fm, tag);

            }
        });

        setImage();
        loadList();
    }

    private  void setImage(){
        ImageView img = (ImageView) findViewById(R.id.addCategory);
        img.setImageResource(R.drawable.ic_add_black_36dp);
    }

    private void loadListsInListView() {

        android.widget.ListView ListViewListsodlists = (android.widget.ListView) findViewById(R.id.Listofcatrogries);
        ListofCategories = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        for(int i = Categories.size()-1; i >= 0; i--){
            ListofCategories.add(Categories.get(i).name);
        }

        ListViewListsodlists.setAdapter(ListofCategories);
        AdapterView.OnItemClickListener mListClickedHandler = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ListView.class);
                String nameofselected = parent.getItemAtPosition(position).toString();

                for(int i = Categories.size()-1; i >= 0; i--){
                    if(Categories.get(i).name == nameofselected){
                        intent.putExtra("category_id", Integer.toString(Categories.get(i).id));
                    }
                }

                Toast.makeText(CategoryView.this, nameofselected, Toast.LENGTH_SHORT).show();

                intent.putExtra("name", nameofselected);
                startActivity(intent);
            }
        };
        ListViewListsodlists.setOnItemClickListener(mListClickedHandler);

    }

    public void loadList(){
        Categories = nc.readAllCategories();
        loadListsInListView();
    }

    @Override
    protected void onDestroy() {
        nh.close();
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_edit:
                CreateCategoryDialog dlg = new CreateCategoryDialog(cv, Bundle.EMPTY);
                String tag = "";
                FragmentManager fm = getFragmentManager();
                dlg.show(fm, tag);
                return true;

            case R.id.action_delete:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
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
