package bci.core;

import java.io.Serializable;

public abstract  class Rule implements Serializable{
    private int _id;
    Rule(int id){
        _id=id;
    }
    abstract boolean  checkRule(Work work,User user);
    
    int getId(){
        return this._id;
    }
}