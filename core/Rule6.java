package bci.core;

public class Rule6 extends Rule{
    Rule6(){
        super(6);
    }

    @Override
    boolean checkRule(Work work,User user){
       if(work.getPrice()>25 && user.getComportamento()!=User.Comportamento.CUMPRIDOR){
        return false;
       }else return true;
    }
}