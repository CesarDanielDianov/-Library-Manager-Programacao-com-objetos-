package bci.core;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Creator implements Serializable{
    private String _name;
    private List<Work> _works;


    Creator(String nome){
        _name=nome;
        _works = new ArrayList<>();
    }

    String getName(){
        return this._name;
    }

    void add(Work work){
        _works.add(work);
    }

    void remove(Work work){
        _works.remove(work);
    }
    
    List<Work> works(){
        return this._works;
    }

    
}
