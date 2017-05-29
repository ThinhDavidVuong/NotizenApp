package Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import NoteDB.NoteController;
import NoteDB.NoteDbHelper;
import ch.bbcag.notizenapp.SingelListView;

public class DeleteListDialog extends DialogFragment {

    private NoteDbHelper nh;
    private NoteController nc;

    private SingelListView slv;

    private Bundle Test;

    private int listid;
    private  int categoryid;
    private String listname;

    public DeleteListDialog(SingelListView context, Bundle savedInstanceState, int Listid){
        slv = context;
        nh = new NoteDbHelper(context);
        nc = new NoteController(nh);
        this.listid = Listid;
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
                        nc.deleteList(listid);
                            slv.backToUperActivity();
                    }
                })
                .setNegativeButton("abbruch", new DialogInterface.OnClickListener() {
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