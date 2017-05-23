package ch.bbcag.notizenapp;

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
import android.widget.Toast;

import java.util.ArrayList;

public class ListView extends AppCompatActivity {

    ArrayAdapter ListofLists;
    private int listid;
    private ArrayList Listen = new ArrayList();

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
                    ListofLists.add(Listen[i]);
                }

                intent.putExtra("listid", listid);
                //Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();



                //Intent mit Zusatzinformationen - hier die Badi Nummer

                if (selected.equals(getString(R.string.badaarberg))) {
                    intent.putExtra("badi", "71");

                } else if (selected.equals(getString(R.string.badadelboden))) {
                    intent.putExtra("badi", "27");
                } else if (selected.equals(getString(R.string.badbern))) {

                } else if (selected.equals(getString(R.string.seebrinz))) {
                    intent.putExtra("badi", "143");
                } else if (selected.equals(getString(R.string.badburgdorf))) {
                    intent.putExtra("badi", "1");
                } else if (selected.equals(getString(R.string.badkirchberg))) {
                    intent.putExtra("badi", "12");
                }
                intent.putExtra("name", selected);
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
