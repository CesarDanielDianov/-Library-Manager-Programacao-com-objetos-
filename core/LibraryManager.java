package bci.core;


import bci.core.exception.*;
import java.io.*;
import java.util.List;

// FIXME import classes

/**
 * The façade class. Represents the manager of this application. It manages the current
 * library and works as the interface between the core and user interaction layers.
 */
public class LibraryManager {

  private Library _library;

  public LibraryManager(){   
     _library = new Library();
  }
  public Library getLibrary(){  
    return this._library;
  }


  public boolean getChangesLM(){
    return  _library.getChangesL();
  }

  public String getFileName(){
    if(_library.getFileName()==null){
      return null;
    }
    return _library.getFileName();
  }


  public int getDateLM(){     
    return _library.getDateL();
  }

  public void advanceDateLM(int advance){ 
    _library.advanceDateL(advance);
  }


  public int rcreateUser(String name,String email){  
    User user=_library.createUser(name, email);
    if(user.getValidade()==true){
      return user.getId();
    }return 0;
  }

  public User getUserLM(int id){  
    return _library.getUserL(id);
  }
  
  public List<User> getUsersLM(){  
    return _library.getUsersL();
  }


  public Work getWorkLM(int id){  
    return _library.getWork(id);
  }

  public List<Work> getWorksLM(){ 
    return _library.getWorksL();
  }


  public List<Work> getCreatorWorksLM(String name){
    if(_library.getCreatorWorksL(name) ==null){
      return null;
    }
    return _library.getCreatorWorksL(name);
  }

  public  String getDescriptionLM(Work work){
    return work.getDescription();
  }

  public boolean changeWorkInventoryLM(Work work, int inventoryChange){
    if (work.getAvailable() + inventoryChange < 0) {
      return false;
    }else{
      work.changeInventory(inventoryChange);
      
      if (work.getNumberOfCopies() == 0) {
        _library.deleteWork(work);
      }
      
      return true;
    }
  }

  public int doRequestLM(Work work,User user){
    return _library.doRequestL(work,user);
  }



  public Request getRequestLM(int userid,int workid){
    return _library.getRequestL(userid, workid);
  }

  public void removeRequestLM(int userid,int workid){
    _library.removeRequestL(userid,workid);
  }


  public int checkRulesLM(int work,int user){
    return _library.checkRulesL(work,user);
  }


  public void sendNotificationLM(int workID){
    _library.sendNotificationL(workID);
  }
  public void addInterestedLM(int userid,int workid){
    _library.addInterestedL(userid,workid);
  }

  public boolean allowedToRequestLM(int userid){
    return _library.allowedToRequestL(userid);
  }

  public void payFineLM(int userid,int fine){
    _library.payfineL(userid,fine);
  }

  public void payAllLM(int userid){
    _library.payAllL(userid);
  }


  public void setStateLM(int userid){
    _library.setStateL(userid);
  }

  public int calculateFineLM(int userid,int workid){
    return _library.calculateFineL(userid, workid);
  }

  public void suspend(int userid){
    _library.suspendL(userid);
  }


  public void clearNotificationsLM(int userid){
    _library.clearNotificationsL(userid);
  }





  public int fineToPayLM(int userid){
    return _library.fineToPayL(userid);
  }
  /**
   * Saves the serialized application's state into the file associated to the current library
   *
   * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
   * @throws MissingFileAssociationException if the current library does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   **/
  public void save() throws MissingFileAssociationException, FileNotFoundException, IOException {
    if (_library.getFileName() == null){
      throw new MissingFileAssociationException();
      }

    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(_library.getFileName()));
    out.writeObject(_library);             
    _library.setChangesL(false);
  }

  /**
   * Saves the serialized application's state into the specified file. The current library is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current library does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   **/



  public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
    if (filename == null || filename.isEmpty()) {
      throw new MissingFileAssociationException();
    }

    _library.setFileName(filename);

      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(_library.getFileName()));
      out.writeObject(_library);
      _library.setChangesL(false);
    
  }








  /**
   * Loads the previously serialized application's state as set it as the current library.
   *
   * @param filename name of the file containing the serialized application's state
   *        to load.
   * @throws UnavailableFileException if the specified file does not exist or there is
   *         an error while processing this file.
   **/
  public void load(String filename) throws UnavailableFileException {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
      _library = (Library) in.readObject();
      _library.setFileName(filename);
      _library.setChangesL(false);
    } catch (IOException | ClassNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
    _library.setChangesL(false);
  }







  /**
   * Read text input file and initializes the current library (which should be empty)
   * with the domain entities representeed in the import file.
   *
   * @param datafile name of the text input file
   * @throws ImportFileException if some error happens during the processing of the
   * import file.
   **/
  public void importFile(String datafile) throws ImportFileException {
    try {
      if (datafile != null && !datafile.isEmpty())
        _library.importFile(datafile);
    } catch (IOException | UnrecognizedEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(datafile, e);
    }

  } 
}