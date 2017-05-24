package Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import NoteDB.NoteController;
import NoteDB.NoteDbHelper;
import ch.bbcag.notizenapp.ListView;
import ch.bbcag.notizenapp.R;

public class UpdateListDialog extends DialogFragment {
    private NoteDbHelper nh;
    private NoteController nc;

    private ListView lv;

    private Bundle Test;

    public UpdateListDialog(ListView context, Bundle savedInstanceState){
        lv = context;
        nh = new NoteDbHelper(context);
        nc = new NoteController(nh);
        this.Test = savedInstanceState;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setMessage("Liste umbenennen")
                .setView(inflater.inflate(R.layout.dialog_add_list, null))
                .setPositiveButton("ändern", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
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
