package ch.bbcag.notizenapp;

/**
 * Created by berpet on 23.05.2017.
 */

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
