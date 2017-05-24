package Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import Model.QuestModel;
import NoteDB.NoteController;
import NoteDB.NoteDbHelper;
import ch.bbcag.notizenapp.R;
import ch.bbcag.notizenapp.SingelListView;

public class UpdateQuestDialog extends DialogFragment {

    private NoteDbHelper nh;
    private NoteController nc;

    private SingelListView slv;

    private Bundle Test;

    private int taskid;
    private  int listid;
    private String taskname;
    private boolean isChecked;

    public UpdateQuestDialog(SingelListView context, Bundle savedInstanceState, int Taskid, int Listid, String Taskname, boolean IsChecked){
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


        builder.setMessage("Aufgabe umbennenen")
                .setView(inflater.inflate(R.layout.dialog_add_list, null))
                .setPositiveButton("ändern", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        nc.updateTask(new QuestModel(taskid, listid, taskname, isChecked));
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
