package bci.core;

public class Rule2 extends Rule{
    Rule2(){
        super(2);
    }

    @Override
    boolean checkRule(Work work,User user){
       return user.isActive();
    }
}