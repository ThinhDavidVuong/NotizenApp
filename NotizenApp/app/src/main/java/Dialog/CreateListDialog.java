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
import ch.bbcag.notizenapp.MultiListView;
import ch.bbcag.notizenapp.R;

public class CreateListDialog extends DialogFragment {

    private NoteDbHelper nh;
    private NoteController nc;

    private MultiListView lv;

    private Bundle Test;

    private int categoryid;

    public CreateListDialog(MultiListView context, Bundle savedInstanceState, int Categoryid){
        lv = context;
        nh = new NoteDbHelper(context);
        nc = new NoteController(nh);
        this.categoryid = Categoryid;
        this.Test = savedInstanceState;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.dialog_add_list, null);

        builder.setMessage("Neue Liste")
                .setView(v)
                .setPositiveButton("erstellen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TextView tv = (TextView) v.findViewById(R.id.newlistname);
                        if(tv.getText().toString() != ""){
                            nc.insertList(categoryid, tv.getText().toString());
                            lv.loadList();
                        }
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