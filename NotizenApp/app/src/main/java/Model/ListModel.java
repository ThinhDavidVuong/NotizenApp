package Model;

//is Used to hold the infromation for a list
public class ListModel {

    public int id;

    public int category_id;

    public String name;

    public ListModel(int id, int category_id, String name){
        this.id = id;
        this.category_id = category_id;
        this.name = name;
    }
}
