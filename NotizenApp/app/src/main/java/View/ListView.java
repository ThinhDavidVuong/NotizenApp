package View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.ListModel;
import ch.bbcag.notizenapp.R;

public class ListView extends AppCompatActivity {

    ArrayAdapter ListofLists;
    private int category_id;
    private String category_name;
    private ArrayList<ListModel> Listen = new ArrayList<ListModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewList();
            }
        });

        Intent intent = getIntent();

        category_id = Integer.parseInt(intent.getStringExtra("category_id"));
        category_name = intent.getStringExtra("name");

        //GetListenVonDBbyCategorieID(category_id);

        setImage();
        loadListsInListView();
    }

    private  void setImage(){
        ImageView img = (ImageView) findViewById(R.id.addList);
        img.setImageResource(R.drawable.ic_add_black_36dp);
    }

    private void loadListsInListView() {

        android.widget.ListView ListViewListsodlists = (android.widget.ListView) findViewById(R.id.Listoflists);
        ListofLists = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);


        
        ListViewListsodlists.setAdapter(ListofLists);
        AdapterView.OnItemClickListener mListClickedHandler = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SingelListView.class);
                String nameofselected = parent.getItemAtPosition(position).toString();



                for(int i = 0; i < Listen.size(); i++){
                    ListofLists.add(Listen.get(i).name);
                }

                for(int i = 0; i < Listen.size(); i++){
                    if(Listen.get(i).name == nameofselected){
                        intent.putExtra("listid", Integer.toString(Listen.get(i).id));
                    }
                }

                Toast.makeText(ListView.this, nameofselected, Toast.LENGTH_SHORT).show();

                intent.putExtra("name", nameofselected);
                startActivity(intent);
            }
        };
        ListViewListsodlists.setOnItemClickListener(mListClickedHandler);

    }

    private void addNewList(){

    }

    private void editcategory(){

    }

    private void deleteCategory(){

    }

}
