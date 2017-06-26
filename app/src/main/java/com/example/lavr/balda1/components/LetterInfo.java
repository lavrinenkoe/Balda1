package com.example.lavr.balda1.components;

import java.io.Serializable;

/**
 * Created by Lavr on 30.05.2017.
 */

public class LetterInfo implements Serializable {

//    private boolean _isSelected;
//    public boolean getSelected() { return _isSelected; }
//    public void setSelected(boolean b) { _isSelected = b; }

    private String _Letter;
    public String getLetter()
    {
        return _Letter;
    }
    public void setLetter(String s)
    {
        _Letter = s;
    }

    private int _Position;
    public int getPosition()
    {
        return _Position;
    }
    public void setPosition(int i)
    {
        _Position = i;
    }

    private int _indexInWord;
    public int getIndexInWord()
    {
        return _indexInWord;
    }
    public void setIndexInWord(int i)
    {
        _indexInWord = i;
    }

    public LetterInfo(){}
    public LetterInfo(String letter, int position){
        setLetter(letter);
        setPosition(position);
        //setSelected(isSelected);
    }

    public LetterInfo(String letter, int position, int indexInWord){
        setLetter(letter);
        setPosition(position);
        //setSelected(isSelected);
        setIndexInWord(indexInWord);
    }
}
