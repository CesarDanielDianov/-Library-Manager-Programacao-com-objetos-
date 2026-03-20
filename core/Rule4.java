package bci.core;

public class Rule4 extends Rule{
    Rule4(){
        super(4);
    }

    @Override
    boolean checkRule(Work work,User user){
       if(user.getComportamento()==User.Comportamento.CUMPRIDOR && user.getNumberOfRequest()>5
          || user.getComportamento()==User.Comportamento.FALTOSO && user.getNumberOfRequest()>1
          || user.getComportamento()==User.Comportamento.NORMAL && user.getNumberOfRequest()>3){
            return false;
       }return true;
    }
}