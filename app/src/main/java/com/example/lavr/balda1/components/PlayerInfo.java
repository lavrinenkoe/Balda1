package com.example.lavr.balda1.components;

import java.util.Vector;

/**
 * Created by Lavr on 30.05.2017.
 */

public class PlayerInfo {

    private boolean _IsCurrent;
    public boolean getIsCurrent(){ return _IsCurrent; }
    public void setIsCurrent(boolean b) { _IsCurrent = b; }

    private Vector<String> _words;
    public Vector<String> getWords(){ return _words; }
    public void addWord(String word) { _words.add(word); }

    private String _Name;
    public String getName()
    {
        return _Name;
    }
    public void setName(String s)
    {
        _Name = s;
    }

    public int getScore()
    {
        int score = 0;
        for(int i = 0; i < getWords().size(); i++)
        {
            String word = getWords().get(i);
            score += word.length();
        }
        return score;
    }

    public PlayerInfo(){}
    public PlayerInfo(String name)
    {
        _Name = name;
        _IsCurrent = false;
        _words = new Vector<String>();
    }
}

