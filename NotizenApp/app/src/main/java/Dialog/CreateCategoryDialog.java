package Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import NoteDB.*;
import ch.bbcag.notizenapp.CategoryView;
import ch.bbcag.notizenapp.R;

public class CreateCategoryDialog extends DialogFragment {

    private NoteDbHelper nh;
    private NoteController nc;

    private CategoryView cv;

    private Bundle Test;

    public CreateCategoryDialog(CategoryView context, Bundle savedInstanceState){
        cv = context;
        nh = new NoteDbHelper(context);
        nc = new NoteController(nh);
        this.Test = savedInstanceState;
    }

    @Override
    public Dialog onCreateDialog(Bundle Test) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.dialog_add_category, null);

        //Build the dialog
        builder.setMessage("Neue Kategorie")
                .setView(v)
                .setPositiveButton("erstellen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TextView tv = (TextView) v.findViewById(R.id.newcategoriename);
                        if(tv.getText().toString() != "") {
                            nc.insertCategory(tv.getText().toString());
                            cv.loadList();
                        }
                    }
                })
                .setNegativeButton("abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog and nothing happens
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