package bci.core;

public class Rule1 extends Rule{
    Rule1(){
        super(1);
    }

    @Override
    boolean checkRule(Work work,User user){
       return !user.existingRequest(work);
    }
}