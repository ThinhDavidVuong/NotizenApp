package ch.bbcag.notizenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;
    enum Direction {LEFT, RIGHT;}

    TextView lvSimple = (TextView) findViewById(R.id.category);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NotizApp");

        lvSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


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

    @Override
    protected void onResume() {
        super.onResume();
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
                Intent intent = new Intent(getApplicationContext(), MultiListView.class);
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
}
