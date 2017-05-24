package Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import Model.CategoryModel;
import NoteDB.NoteController;
import NoteDB.NoteDbHelper;
import ch.bbcag.notizenapp.CategoryView;

public class DeleteCategoryDialog extends DialogFragment {

    private NoteDbHelper nh;
    private NoteController nc;

    private CategoryView cv;

    private Bundle Test;

    private int categoryid;
    private String categoryname;

    public DeleteCategoryDialog(CategoryView context, Bundle savedInstanceState, int categoryid, String categoryname){
        cv = context;
        nh = new NoteDbHelper(context);
        nc = new NoteController(nh);
        this.categoryid = categoryid;
        this.categoryname = categoryname;
        this.Test = savedInstanceState;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setTitle("Kategorie löschen")
                .setMessage("Sind sie sicher, dass sie die Kategorie löschen wollen?")
                .setPositiveButton("löschen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    nc.deleteCategory(new CategoryModel(categoryid, categoryname));
                    }
                })
                .setNegativeButton("abbruch", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        nh.close();
        super.onDismiss(dialog);
    }
}