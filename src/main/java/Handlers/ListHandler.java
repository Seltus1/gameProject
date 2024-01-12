package Handlers;

import java.util.ArrayList;
import java.util.Random;

public class ListHandler<T> {

    private ArrayList<T> list;

    public ListHandler() {
        list = new ArrayList<>();
    }

    public ArrayList<T> getList(){
        return list;
    }

    public int size(){
        return list.size();
    }

    public boolean add(T e){
        list.add(e);
        return true;
    }

    public T get(int index){
        return list.get(index);
    }

    public boolean removeIndex(int index){
        list.remove(index);
        return true;
    }

    public boolean removeObject(T object){
        list.remove(object);
        return true;
    }

}