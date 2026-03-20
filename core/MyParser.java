package bci.core;

import bci.core.Work.Category;
import bci.core.exception.UnrecognizedEntryException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// MAYBE more import

class MyParser {
  private Library _library;

  MyParser(Library lib) {
    _library = lib;
  }

  void parseFile(String filename) throws IOException,UnrecognizedEntryException {
    String line;

    try (BufferedReader in = new BufferedReader(new FileReader(filename));) {
      while ((line = in.readLine()) != null)
        parseLine(line);
    }
  }

  private void parseLine(String line) throws UnrecognizedEntryException {
    String[] components = line.split(":");

    switch (components[0]) {
      case "USER":
        parseUser(components, line);
        break;

      case "DVD":
        parseDvd(components, line);
        break;

      case "BOOK":
        parseBook(components, line);
        break;

      default:
        throw new UnrecognizedEntryException("Tipo inválido " + components[0] + " na linha " + line);
    }
  }

  // Assumo que há um método em Library para registar um utente (por exemplo, com o nome registerUser
  // Caso o método lançe alguma excepção do core, então será necessário apanhá-la. Se não lançar,
  // tirar o try-catch
  private void parseUser(String[] components, String line) throws UnrecognizedEntryException {
    
      if (components.length != 3)
        throw new UnrecognizedEntryException ("Número inválido de campos (3) na descrição de um utente: " + line);

      _library.createUser(components[1], components[2]);
     

  }

  // Assumo que há um método em Library que devolve o criador dado um nome (e cria-o caso não exista)
  // com o nome registerCriator(String)
  // Há um método que regista um DVD em Library dado os vários componentes ou um método que adiciona uma obra
  private void parseDvd(String[] components, String line) throws UnrecognizedEntryException {
    if (components.length != 7)
      throw new UnrecognizedEntryException ("Número inválido de campos (7) na descrição de um DVD: " + line);

    int price = Integer.parseInt(components[3]);
    int igac = Integer.parseInt(components[5]);
    int nCopies = Integer.parseInt(components[6]);
    Category category = Category.valueOf(components[4]);
    Creator creator = _library.registerCreator(components[2].trim());

    _library.registerDvd(components[1],creator,price,category,igac,nCopies);
    // ou cria o DVD (new DVD(...)) e adiciona o dvd às obras da Library
    
  }
  
  private void parseBook(String[] components, String line) throws UnrecognizedEntryException {
    if (components.length != 7)
      throw new UnrecognizedEntryException ("Número inválido de campos (7) na descrição de um Book: " + line);
    
    int price = Integer.parseInt(components[3]);
    int isbn = Integer.parseInt(components[5]);
    int nCopies = Integer.parseInt(components[6]);
    Category category = Category.valueOf(components[4]);
    List<Creator> creators = new ArrayList<>();
    for (String name : components[2].split(","))
      creators.add(_library.registerCreator(name.trim()));

    _library.registerBook(components[1],creators,price,category,isbn,nCopies);
    // ou cria o livro (new Book(...)) e adiciona o livro às obras da Library
  }
  
}
