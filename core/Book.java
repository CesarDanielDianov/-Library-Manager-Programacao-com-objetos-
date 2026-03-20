package bci.core;
import java.util.ArrayList;
import java.util.List;

public class Book extends Work{
    private int _isbn;
    private List<Creator> _authors;


    Book(String title,List<Creator> authors,int price,Category category,int isbn,int numberOfCopies){
        super(title,price,category,numberOfCopies);
        _isbn=isbn;
        _authors = new ArrayList<>(authors);
    }

    int getISBN(){
        return this._isbn;
    }     

    List<Creator> getAuthors(){
        return this._authors;
    }

    String toStringCreators(List<Creator> authors) {
        StringBuilder autores = new StringBuilder(); 
        for (int i = 0; i < authors.size(); i++) {
            autores.append(authors.get(i).getName());
            if (i < authors.size() - 1) {
                autores.append("; "); 
            }
        }
        return autores.toString();
    }


    @Override
    public String getDescription(){
        String category = categoryToString();
        String spc = " - ";
        return(getId() + spc + getAvailable() + " de " + getNumberOfCopies() + spc + "Livro" + spc + getTitle() + spc + getPrice() + spc + category + spc + toStringCreators(_authors) + spc + getISBN());
    }   
    

}