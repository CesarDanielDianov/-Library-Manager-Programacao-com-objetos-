package bci.core;

import java.io.Serializable;

public class Date implements Serializable{
    private int _currentDate;

    Date(){
        _currentDate=1;
    }

    int getCurrentDate(){
        return this._currentDate;
    }
    void advanceDay(int nDays){
        _currentDate=_currentDate+nDays;
    }
}