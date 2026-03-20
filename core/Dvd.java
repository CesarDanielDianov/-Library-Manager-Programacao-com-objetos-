package bci.core;

public class Dvd extends Work{
    private int _igac;
    private Creator _diretor;
    
    Dvd(String title,Creator diretor,int price,Category category,int igac,int numberOfCopies){
        super(title,price,category,numberOfCopies);
        _igac=igac;
        _diretor=diretor;
    }

    int getIGAC(){
        return this._igac;
    }

    Creator getCreator(){
        return this._diretor;
    } 
    @Override
    public String getDescription(){
        String category = categoryToString();
        String spc = " - ";
        return(getId() + spc + getAvailable() + " de " + getNumberOfCopies() + spc + "DVD" + spc + getTitle() + spc + getPrice() + spc + category + spc + getCreator().getName() + spc + getIGAC());
    }


}

