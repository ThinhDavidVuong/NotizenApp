package Model;

/**
 * Created by berpet on 23.05.2017.
 */

public class QuestModel {

    public int id;

    public int list_id;

    public String name;

    public boolean isChecked;

    public QuestModel(int id, int list_id, String name, boolean isChecked){
        this.id = id;
        this.list_id = list_id;
        this.name = name;
        this.isChecked = isChecked;
    }
}
