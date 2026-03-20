package bci.core;

public class Rule5 extends Rule{
    Rule5(){
        super(5);
    }

    @Override
    boolean checkRule(Work work,User user){
       return !(work.getCategory()==Work.Category.REFERENCE);
    }
}