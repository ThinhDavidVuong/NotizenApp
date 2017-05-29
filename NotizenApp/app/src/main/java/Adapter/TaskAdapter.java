package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import Model.TaskModel;
import ch.bbcag.notizenapp.R;

/**
 * Created by berpet on 24.05.2017.
 */

public class TaskAdapter extends ArrayAdapter<TaskModel> {


    public TaskAdapter(Context context, ArrayList<TaskModel> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TaskModel tm = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }

        TextView text_task = (TextView) convertView.findViewById(R.id.text_task);
        CheckBox checkbox_task = (CheckBox) convertView.findViewById(R.id.checkbox_task);
        ImageButton image_button = (ImageButton) convertView.findViewById(R.id.editbutton);

        text_task.setText(tm.name);
        checkbox_task.setChecked(tm.isChecked);
        checkbox_task.setTag(tm);
        image_button.setTag(tm);

        return convertView;
    }
}