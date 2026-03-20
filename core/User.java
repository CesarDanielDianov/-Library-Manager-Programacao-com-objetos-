package bci.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable{

    private int _id;
    private boolean _isActive;
    private String _name;
    private String _email;
    private int _fine;
    private Comportamento _userBehavior;
    private boolean _validoNoSistema=false;
    private List<Notification> _notifications;
    private List<Request> _requests; 


    private int _deadLineFulfilled=0; 
    private int _deadLineNotFulfilled=0;
     
    public enum Comportamento {
        NORMAL,CUMPRIDOR,FALTOSO
    }

    User(String name,String email){
        _notifications =new ArrayList<>();
        _requests =new ArrayList<>();
        _name=name;
        _email=email;
        _userBehavior=Comportamento.NORMAL;
        _fine=0;
        _isActive=true;
    }

    void setDeadLineFulfilled(int i){
        if(i==0){
            _deadLineFulfilled=0;
        }else _deadLineFulfilled++;
    }

    void setDeadLineNotFulfilled(int i){
        if(i==0){
            _deadLineNotFulfilled=0;
        }else _deadLineNotFulfilled++; 
    }

    int getDeadLineFulfilled(){
        return this._deadLineFulfilled;
    }

    int getDeadLineNotFulfilled(){
        return this._deadLineNotFulfilled;
    }







    int setId(int id){
        return this._id=id;
    }
    int getId(){
        return this._id;
    }
    boolean isActive(){
        return this._isActive;
    }
    void setActive(boolean x){
        _isActive=x;
    }

    String getName(){
        return this._name;
    }
    String getEmail(){
        return this._email;
    }
    int getFine(){
        return this._fine;
    }
    void setFine(int i){
        _fine=_fine+i;  
    }
    
    void payAll(){
        _fine=0;
    }



    Comportamento getComportamento(){
        return this._userBehavior;
    }    
    void setComportamento(User.Comportamento comportamento){
        _userBehavior=comportamento;
    }

    void setValidade(){
        _validoNoSistema=!_validoNoSistema;
    }
    boolean getValidade(){
        return this._validoNoSistema;
    }

    void addRequest(Request request){
        _requests.add(request);
    }

    void addNotification(Notification notification){
        _notifications.add(notification);
    }
    public List<Notification> getNotifications(){
        return this._notifications;
    }

    boolean existingRequest(Work work){
        for(Request r : _requests){
            if(r.getWork().getId() == work.getId()){
                
                return true;
            }
        }
        return false;
    }

    int getNumberOfRequest(){
        return this._requests.size();
    }
    
    List<Request> getRequests(){
        return _requests;
    }



    void removeRequest(Request r){
        _requests.remove(r); 
    }

    void clearNotifications(){
        _notifications.clear();
    }
    @Override
    public String toString(){  
      String spc = " - ";
      if(this._isActive == true){
        return  (_id + spc + _name + spc + _email + spc + _userBehavior + spc + "ACTIVO");
    }else return (_id + spc + _name + spc + _email + spc + _userBehavior + spc + "SUSPENSO" + spc + "EUR "+_fine);
    }
}