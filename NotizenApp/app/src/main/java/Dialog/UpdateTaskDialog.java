package Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import Model.TaskModel;
import NoteDB.NoteController;
import NoteDB.NoteDbHelper;
import ch.bbcag.notizenapp.R;
import ch.bbcag.notizenapp.SingelListView;

public class UpdateTaskDialog extends DialogFragment {

    private NoteDbHelper nh;
    private NoteController nc;

    private SingelListView slv;

    private Bundle Test;

    private int taskid;
    private  int listid;
    private String taskname;
    private boolean isChecked;

    public UpdateTaskDialog(SingelListView context, Bundle savedInstanceState, int Taskid, int Listid, String Taskname, boolean IsChecked){
        slv = context;
        nh = new NoteDbHelper(context);
        nc = new NoteController(nh);
        this.taskid = Taskid;
        this.listid = Listid;
        this.taskname = Taskname;
        this.isChecked = IsChecked;
        this.Test = savedInstanceState;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.dialog_add_category, null);
        final EditText te = (EditText) v.findViewById(R.id.newcategoriename);
        te.setText(taskname);

        builder.setMessage("Aufgabe umbennenen")
                .setView(v)
                .setPositiveButton("ändern", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(te.getText().toString() != ""){
                            nc.updateTask(new TaskModel(taskid, listid, te.getText().toString(), isChecked));
                            slv.loadList();
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
