package bci.core;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Work implements Serializable{   

    private int _id;
    private int _price;
    private int _numberOfCopies;
    private int _available;
    private String _title;
    private Category _category;
    private int _code;
    private List<User> _interested;

    public enum Category { 
        REFERENCE,FICTION,SCITECH
    }

    Work(String title,int price,Category category,int numberOfCopies){  
        _interested=new ArrayList<>();
        _numberOfCopies=numberOfCopies;
        _title=title;
        _price=price;
        _category=category;
        _available=numberOfCopies;
    }

    int getId(){   
        return this._id; 
    }
    int setId(int id){
        return this._id=id;
    }

    void setAvailable(int integer){
        _available=_available+integer;
    }


    int getPrice(){  
        return this._price;
    }    
    int getNumberOfCopies(){  
        return this._numberOfCopies;
    }    
    String getTitle(){     
        return this._title;
    }
    Category getCategory(){ 
        return this._category;
    } 
    public int getAvailable(){ 
        return this._available;
    }

    void addInterested(User user){
        _interested.add(user);
    }
    
    List<User> getInterested(){
        return this._interested;
    }

    String categoryToString() {
        if (_category == Category.REFERENCE) {
            return "Referência";
        } else if (_category == Category.FICTION) {
            return "Ficção";
        } else  return "Técnica e Científica";   
    }

    public abstract String  getDescription();

    
    void changeInventory(int inventoryChange){
        _numberOfCopies = getNumberOfCopies() + inventoryChange;
        _available = getAvailable() + inventoryChange;
    } 
}