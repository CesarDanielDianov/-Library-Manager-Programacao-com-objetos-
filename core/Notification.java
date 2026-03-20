package bci.core;

public class Notification{
    private String _message;
    private Work _work;
    Notification(Work work){
        _work=work;
        _message=_work.getDescription();
    }

    @Override
    public String toString(){
        String spc = " - ";

        return("DISPONIBILIDADE: " + _message);
    }
}