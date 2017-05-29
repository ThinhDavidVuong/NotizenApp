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
import Model.ListModel;
import NoteDB.NoteController;
import NoteDB.NoteDbHelper;
import ch.bbcag.notizenapp.ListView;
import ch.bbcag.notizenapp.R;
import ch.bbcag.notizenapp.SingelListView;

public class UpdateListDialog extends DialogFragment {
    private NoteDbHelper nh;
    private NoteController nc;

    private SingelListView slv;

    private Bundle Test;

    private int listid;
    private  int categoryid;
    private String listname;

    public UpdateListDialog(SingelListView context, Bundle savedInstanceState, int Listid, int categoryid, String listname){
        slv = context;
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

        final View v = inflater.inflate(R.layout.dialog_add_category, null);
        final EditText te = (EditText) v.findViewById(R.id.newcategoriename);
        te.setText(listname);

        builder.setMessage("Liste umbenennen")
                .setView(v)
                .setPositiveButton("ändern", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(te.getText().toString() != ""){
                            nc.updateList(new ListModel(listid, categoryid, te.getText().toString()));
                            slv.updateListname();
                        }
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
