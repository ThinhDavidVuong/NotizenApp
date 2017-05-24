package Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import NoteDB.NoteController;
import NoteDB.NoteDbHelper;
import ch.bbcag.notizenapp.R;
import ch.bbcag.notizenapp.SingelListView;

public class CreateTaskDialog extends DialogFragment {

    private NoteDbHelper nh;
    private NoteController nc;

    private SingelListView slv;

    private Bundle Test;

    private int listid;

    public CreateTaskDialog(SingelListView context, Bundle savedInstanceState, int listid){
        slv = context;
        nh = new NoteDbHelper(context);
        nc = new NoteController(nh);
        this.listid = listid;
        this.Test = savedInstanceState;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.dialog_add_tansk, null);

        builder.setMessage("Neue Aufgabe")
                .setView(v)
                .setPositiveButton("erstellen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TextView tv = (TextView) v.findViewById(R.id.newitemname);
                        if(tv.getText().toString() != ""){
                            nc.insertTask(listid, tv.getText().toString());
                            slv.loadList();
                        }
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