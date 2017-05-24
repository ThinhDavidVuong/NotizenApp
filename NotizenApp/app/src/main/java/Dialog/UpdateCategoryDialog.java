package Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import NoteDB.NoteController;
import NoteDB.NoteDbHelper;
import ch.bbcag.notizenapp.CategoryView;
import ch.bbcag.notizenapp.R;

public class UpdateCategoryDialog extends DialogFragment {
    private NoteDbHelper nh;
    private NoteController nc;

    private CategoryView cv;

    private Bundle Test;

    public UpdateCategoryDialog(CategoryView context, Bundle savedInstanceState){
        cv = context;
        nh = new NoteDbHelper(context);
        nc = new NoteController(nh);
        this.Test = savedInstanceState;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setMessage("Kategotie umbenennen")
                .setView(inflater.inflate(R.layout.dialog_add_category, null))
                .setPositiveButton("ändern", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("abbrechen", new DialogInterface.OnClickListener() {
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