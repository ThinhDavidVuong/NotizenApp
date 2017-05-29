package Model;

//is used to hold the information of a task
public class TaskModel {

    public int id;

    public int list_id;

    public String name;

    public boolean isChecked;

    public TaskModel(int id, int list_id, String name, boolean isChecked){
        this.id = id;
        this.list_id = list_id;
        this.name = name;
        this.isChecked = isChecked;
    }
}
