package Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import Model.ListModel;
import NoteDB.NoteController;
import NoteDB.NoteDbHelper;
import ch.bbcag.notizenapp.ListView;

public class DeleteListDialog extends DialogFragment {

    private NoteDbHelper nh;
    private NoteController nc;

    private ListView lv;

    private Bundle Test;

    private int listid;
    private  int categoryid;
    private String listname;

    public DeleteListDialog(ListView context, Bundle savedInstanceState, int Listid, int categoryid, String listname){
        lv = context;
        nh = new NoteDbHelper(context);
        nc = new NoteController(nh);
        this.listid = Listid;
        this.categoryid = categoryid;
        this.listname = listname;
        this.Test = savedInstanceState;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setTitle("Liste löschen")
                .setMessage("Sind sie sicher, dass sie die Liste löschen wollen?")
                .setPositiveButton("löschen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        nc.deleteList(new ListModel(listid, categoryid, listname));
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