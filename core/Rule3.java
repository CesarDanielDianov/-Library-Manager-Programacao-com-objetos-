package bci.core;

public class Rule3 extends Rule{
    Rule3(){
        super(3);
    }

    @Override
    boolean checkRule(Work work,User user){
       return work.getAvailable()>=1;
    }
}