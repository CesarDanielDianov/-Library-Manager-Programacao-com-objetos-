package bci.core;

import java.io.Serializable;

public class  Request implements Serializable{
    private int _deadLine;
    private User _user;
    private Work _work;


    
    Request(Work work,User user){
        _user=user;
        _work=work;
    }


    int getDeadLine(){
        return this._deadLine;
    }
    User getUser(){
        return this._user;
    }   

    Work getWork(){
        return this._work;
    }



    int computeDeadLine(int date){
       if(_user.getComportamento()==User.Comportamento.FALTOSO){
        return this._deadLine=2+date;
       }
       if(_user.getComportamento()==User.Comportamento.CUMPRIDOR){
            if(_work.getNumberOfCopies()==1){
                return this._deadLine=8+date;
            }else if(_work.getNumberOfCopies()<=5){
                return this._deadLine=15+date;
            }else return this._deadLine=30+date;
       }else 
            if(_work.getNumberOfCopies()==1){
                return this._deadLine=3+date;
            }else if(_work.getNumberOfCopies()<=5){
                return this._deadLine=8+date;
            }else return this._deadLine=15+date;

    }  


}