package Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import Model.CategoryModel;
import NoteDB.NoteController;
import NoteDB.NoteDbHelper;
import ch.bbcag.notizenapp.CategoryView;
import ch.bbcag.notizenapp.ListView;
import ch.bbcag.notizenapp.R;

public class DeleteCategoryDialog extends DialogFragment {

    private NoteDbHelper nh;
    private NoteController nc;

    private ListView lv;

    private Bundle Test;

    private int categoryid;


    public DeleteCategoryDialog(ListView context, Bundle savedInstanceState, int categoryid){
        lv = context;
        nh = new NoteDbHelper(context);
        nc = new NoteController(nh);
        this.categoryid = categoryid;
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
                    nc.deleteCategory(categoryid);
                        lv.backToTop();
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