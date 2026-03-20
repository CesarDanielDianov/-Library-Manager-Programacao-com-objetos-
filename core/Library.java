package bci.core;

import bci.core.Work.Category;
import bci.core.exception.UnrecognizedEntryException;
import java.io.*;
import java.util.*;



/**
 * Represents a library system that manages users, creators, and works (books,DVD's...).
 * 
 * This class supports adding and retrieving users and creators, managing works, 
 * tracking changes, and handling the current date. It is serializable to allow 
 * saving and loading the library state from a file.
 */
public class Library implements Serializable {

  @Serial
  private static final long serialVersionUID = 202501101348L;

  private boolean _changed=false;    

  private  int _userID=1;
  private Map<Integer,User>  _users;
  private List<User> _usersOrganized;

  private Map<String,Creator> _creators;

  private int _workID=1;
  private Map<Integer,Work> _works;
  private List<Work> _workOrganized;

  private Date _date;
  
  private List<Request> _requests;

  private String _fileName;

  private List<Rule> _rules;

  /**
   * Constructs a new Library object.
   * Initializes the internal collections for users, creators, and works,
   * and sets the current date.
   */
  public Library(){      
    _date = new Date();
    _users = new HashMap<>();
    _creators = new HashMap<>();
    _works = new HashMap<>();
    _requests = new ArrayList<>(); 
    _rules= new ArrayList<>();
    Rule1 rule1=new Rule1();
    Rule2 rule2=new Rule2();
    Rule3 rule3=new Rule3();
    Rule4 rule4=new Rule4();
    Rule5 rule5=new Rule5();
    Rule6 rule6=new Rule6();
    _rules.add(rule1);
    _rules.add(rule2);
    _rules.add(rule3);
    _rules.add(rule4);
    _rules.add(rule5);
    _rules.add(rule6);
  }  

  /**
   * Returns the name of the file associated with this Library.
   * @return the current file name
   */  
  String getFileName(){
    return this._fileName;
  }

  /**
   * Sets the name of the file associated with this Library.
   * @param fileName the new file name to set
   */ 
  void setFileName(String fileName){
    _fileName=fileName;
  }

  /**
   * Returns whether any changes have been made since the last save.
   * @return true if changes have been made, false otherwise
   */
  boolean getChangesL(){ 
    return  this._changed;
  }

  /**
   * Sets the changed flag.
   * @param changes the new value for the changed flag
   */
  void setChangesL(boolean changes){ 
    _changed=changes;
  }

  /**
   * Returns the current date from the library Date object.
   * @return the current date as an integer 
   */
  int getDateL(){    
    return _date.getCurrentDate();
  }

  /**
   * Advances the current date by the specified number of days.
   * Also sets the changed flag to true.
   * @param advance the number of days to advance
   */
  void advanceDateL(int advance){  
    _date.advanceDay(advance);
    _changed=true;
    for(User u:_users.values()){
      allowedToRequestL(u.getId());
    }
  }

  /**
   * Creates a new user with the given name and email if it does not already exist.
   * If a user with the same name and email already exists,it returns a not valid user.
   * Otherwise, a new User is created, assigned a unique ID, added to the users HashMap,
   * its validity is set, and  _changed is set to true.
   * @param name the name of the user
   * @param email the email of the user
   * @return the invalid or newly valid created User object
   */
  User createUser(String name,String email){   //recebe um user e verifica se ja existe
    User user = new User(name,email);
    for(User u :_users.values()){
      if((u.getName().equals(name) && u.getEmail().equals(email))){
        return user;
        
      }
    }user.setId(_userID++);
    _users.put(user.getId(),user);
        user.setValidade();
        _changed=true;
        return user;
  }



  /**
   * Registers a new creator with the given name if not already present.
   * If a creator with the specified name already exists in the system, it returns the existing creator.
   * Otherwise, it creates a new Creator, adds it to the creators HashMap, sets _changed to true,
   * and returns the new creator.
   * @param name the name of the creator to register
   * @return the existing or newly created Creator object
   */
  Creator registerCreator(String name){     
    for(Creator c:_creators.values()){ 
      if(c.getName().equals(name)){ 
        return c; 
      } 
    }Creator creator = new Creator(name); 
      _creators.put(name,creator);
      _changed=true;
      return creator; 
  }
  
 


  /**
   * Registers a new DVD in the Library.
   * Creates a new DVD object with the given title,director, price, category,IGAC, and number of copies,
   * assigns it a unique ID, adds it to the works collection, and associates it with the given director.
   * Also sets _changed to true to indicate that the library has been modified.
   * @param title the title of the book
   * @param diretor the list of creators (authors) of the book
   * @param price the price of the book
   * @param category the category of the book
   * @param igac the ISBN number of the book
   * @param nCopies the number of copies
   */ 
  void registerDvd(String title,Creator diretor,int price,Category category,int igac,int nCopies){
    Dvd dvd = new Dvd(title,diretor,price,category,igac,nCopies);
    dvd.setId(_workID++);  
    _works.put(dvd.getId(),dvd);  //adiciona a obra à hashmap da library
    diretor.add(dvd);   //adiciona à list do criador
    _changed=true;
  }  

  /**
   * Registers a new book in the Library.
   * Creates a new Book object with the given title, authors, price, category, ISBN, and number of copies,
   * assigns it a unique ID, adds it to the works collection, and associates it with each author.
   * Also sets _changed to true to indicate that the library has been modified.
   * @param title the title of the book
   * @param authores the list of creators (authors) of the book
   * @param price the price of the book
   * @param category the category of the book
   * @param isbn the ISBN number of the book
   * @param nCopies the number of copies
   */
  void registerBook(String title,List<Creator> authores,int price,Category category,int isbn,int nCopies){
    Book book = new Book(title,authores,price,category,isbn,nCopies); 
    book.setId(_workID++);
    _works.put(book.getId(),book);  
    for(Creator c:authores){        
      c.add(book); 
    _changed=true;             
    }  
  }    


  void deleteWork(Work work){
    for(Creator c:_creators.values()){
      c.remove(work);
    }
    _works.remove(work);
    _changed = true;
  }

  int doRequestL(Work work,User user){
    Request request = new Request(work,user);
    _requests.add(request);
    request.computeDeadLine(getDateL());
    user.addRequest(request);
    work.setAvailable(-1);
    _changed = true;
    return request.getDeadLine();
  }

  Request getRequestL(int userId,int workId){
    for(Request r:_requests){
      if(r.getWork().getId()==workId && r.getUser().getId()==userId){
        return r;
      }
    }return null;
  }

  void removeRequestL(int userId, int workId) {
    User u=this.getUserL(userId);
    Iterator<Request> it = _requests.iterator();
    Work work=this.getWork(workId);
    while (it.hasNext()) {
      Request r = it.next();
      if (r.getWork().getId() == workId && r.getUser().getId() == userId) {
        judgeUserL(r);
        it.remove();
        work.setAvailable(1);
        u.removeRequest(r);
        _changed=true;
        break;       
      }
    }
  }

  int checkRulesL(int workId, int userId){
      Work work = this.getWork(workId);
      User user = this.getUserL(userId);
      for(Rule r : _rules){
          if(r.checkRule(work, user)==false){
              return r.getId();
          }
      }
      return 0;
  }

  void sendNotificationL(int workID){
    Work work=this.getWork(workID);
    Notification notification=new Notification(work);
    for(User u:work.getInterested()){
      u.addNotification(notification);
      _changed=true;
    }
  }





  void addInterestedL(int userid,int workid){
    Work work=this.getWork(workid);
    User user=this.getUserL(userid);
    work.addInterested(user);
    _changed=true;
  }




  void judgeUserL(Request request){
    User user=request.getUser();
    User.Comportamento comportamento=user.getComportamento();

    if(request.getDeadLine()<this.getDateL()){
      user.setDeadLineNotFulfilled(1);
      user.setDeadLineFulfilled(0);
    }else {user.setDeadLineNotFulfilled(0);
          user.setDeadLineFulfilled(1);
    }
    if(user.getDeadLineFulfilled()==3 && comportamento==User.Comportamento.FALTOSO){
      user.setComportamento(User.Comportamento.NORMAL);

    }else if((comportamento==User.Comportamento.NORMAL || 
      comportamento==User.Comportamento.CUMPRIDOR)&&
      user.getDeadLineNotFulfilled()==3 ){
      user.setComportamento(User.Comportamento.FALTOSO);

    }else if((comportamento==User.Comportamento.NORMAL || 
      comportamento==User.Comportamento.FALTOSO)&&
      user.getDeadLineFulfilled()==5){
        user.setComportamento(User.Comportamento.CUMPRIDOR);
      }
    _changed=true;
  }


  boolean allowedToRequestL(int userid){
    User user=getUserL(userid);
    
    for(Request r:user.getRequests()){
      if(r.getDeadLine()<getDateL()){
          user.setActive(false);
          _changed=true;
        return false;
      }
    }if(user.getFine()!=0){
      user.setActive(false);
      _changed=true;
      return false;
    }
    user.setActive(true);
    _changed=true;
    return true;
  }

  void payfineL(int userId,int fine){
    User user=getUserL(userId);
    user.setFine(-fine);
    if(user.getFine()==0){
      user.setActive(true);
    }
    _changed=true;
  }
  void payAllL(int userid){
    User user=getUserL(userid);
    user.payAll(); 
     _changed=true;  
  }



  int fineToPayL(int userid){
    User user=getUserL(userid);
    return user.getFine();
  }


  /**
   * Returns a list of works created by the specified creator, sorted lexicographically by title.
   * @param name the name of the creator whose works are to be return
   * @return a list of Work objects sorted by title, or null if the creator does not exist
   */ 
  List<Work> getCreatorWorksL(String name){  
    Creator creator = _creators.get(name);
    if (creator == null)
      return null;
    List<Work> works = creator.works();
    Collections.sort(works,(u1,u2) -> u1.getTitle().compareToIgnoreCase(u2.getTitle()));
    return works;
  }

  /**
   * Returns the Work object associated with the given ID from the works HashMap.
   * @param id the ID of the work to return
   * @return the Work object corresponding to the given ID
   */
  Work getWork(int id){   
    return _works.get(id);
  }

  /**
   * Returns the User object associated with the given ID from the users HashMap.
   * @param id the ID of the user to return
   * @return the User object corresponding to the given ID
   */
  User getUserL(int id){  
    User user=_users.get(id); 
    return user;
  }


  boolean setStateL(int userid) {
      return allowedToRequestL(userid);
  }


  void suspendL(int userid){
    getUserL(userid).setActive(false);
    if(getUserL(userid).getComportamento()==User.Comportamento.CUMPRIDOR){
    getUserL(userid).setComportamento(User.Comportamento.NORMAL);
    }_changed=true;
  }
  
  int calculateFineL(int userid,int workid){
      User u=getUserL(userid);
      for(Request r:u.getRequests()){
        if(r.getWork().getId()==workid){
          if(getDateL()>r.getDeadLine()){
            int fine=5*(getDateL()-r.getDeadLine());
            if(fine>0){
              u.setFine(fine);
              _changed=true;
              return fine;
          }
          }
        }
      }return 0;
  }




  void clearNotificationsL(int userid){
    User user=getUserL(userid);
    user.clearNotifications();
  }



  /**
   * Returns a list of all Users stored in the users HashMap,sorted in lexicographical order and by ID .
   * @return a list of Users objects sorted in lexicographical order and by ID
   */
  List<User> getUsersL(){      
    _usersOrganized = new ArrayList<>(_users.values());
    Collections.sort(_usersOrganized,(u1, u2) -> u1.getName().compareTo(u2.getName()));
    return _usersOrganized;
  }
  
  /**
   * Returns a list of all works stored in the works HashMap,sorted by ID.
   * @return a list of Work objects sorted by ID
   */
  List<Work> getWorksL(){  
    _workOrganized= new ArrayList<>(_works.values());
    Collections.sort(_workOrganized,(u1,u2) -> Integer.compare(u1.getId(),u2.getId()));
    return _workOrganized;
  }

  /**
   * Read text input file at the beginning of the program and populates the
   * the state of this library with the domain entities represented in the text file.
   * 
   * @param filename name of the text input file to process
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   **/
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
      MyParser parser = new MyParser(this);
      parser.parseFile(filename);

  }
}