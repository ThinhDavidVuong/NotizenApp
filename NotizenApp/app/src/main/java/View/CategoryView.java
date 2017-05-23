package View;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.app.FragmentManager;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.CategoryModel;
import ch.bbcag.notizenapp.R;

public class CategoryView extends AppCompatActivity {

    ArrayAdapter ListofCategories;
    private ArrayList<CategoryModel> Categories = new ArrayList<CategoryModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addCategory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateCategoryDialog dlg = new CreateCategoryDialog();
                String tag = "";
                FragmentManager fm = getFragmentManager();
                dlg.show(fm, tag);


            }
        });

        //Categories = GetCategoriesFromDB();
        setImage();

        loadListsInListView();
    }

    private  void setImage(){
        ImageView img = (ImageView) findViewById(R.id.addCategory);
        img.setImageResource(R.drawable.ic_add_black_36dp);
    }

    private void loadListsInListView() {

        android.widget.ListView ListViewListsodlists = (android.widget.ListView) findViewById(R.id.Listofcatrogries);
        ListofCategories = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ListViewListsodlists.setAdapter(ListofCategories);
        AdapterView.OnItemClickListener mListClickedHandler = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SingelListView.class);
                String nameofselected = parent.getItemAtPosition(position).toString();

                for(int i = 0; i < Categories.size(); i++){
                    ListofCategories.add(Categories.get(i).name);
                }

                for(int i = 0; i < Categories.size(); i++){
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


}
