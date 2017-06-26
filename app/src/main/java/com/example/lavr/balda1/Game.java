package com.example.lavr.balda1;

import android.content.Context;

import com.example.lavr.balda1.components.LetterInfo;
import com.example.lavr.balda1.components.PlayerInfo;
import com.example.lavr.balda1.controllers.PlayerController;
import com.example.lavr.balda1.controllers.WordsController;

import java.util.Vector;

/**
 * Created by Lavr on 01.06.2017.
 */

public class Game {

    private static PlayerController _PlayerC;
    private static Vector<LetterInfo> _letters;
    public static Vector<LetterInfo> getLetters()
    {
        return  _letters;
    }
    public static void setLetters(Vector<LetterInfo> arr)
    {
        _letters = arr;
    }



    public  Game(Context context){

        _PlayerC = new PlayerController();
        Vector<LetterInfo> letters = WordsController.getInitialArray(context);
        setLetters(letters);
    }

    public static Vector<PlayerInfo> getPlayers(){
        return getPlayerC().GetAllPlayers();
    }
    public static String[] getPlayerNames()
    {
        Vector<PlayerInfo> players = getPlayers();
        String[] arr = new String[players.size()];
        for(int i = 0; i < players.size(); i++)
        {
            arr[i] = players.get(i).getName();
        }
        return arr;
    }


    static Vector<LetterInfo> _currentWord;
    public static String getCurrentWordString()
    {
        if(getLastLetter() == null)  return "";

        boolean exists = lastLetterExistsInCurrentWord();
        if(!exists) return "";

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < _currentWord.size(); i++)
        {
            if(_currentWord.get(i) != null) {
                String s = _currentWord.get(i).getLetter();
                sb.append(s);
            }
        }
        return sb.toString();
    }
    private static boolean lastLetterExistsInCurrentWord()
    {
        LetterInfo letter = getLastLetter();
        Vector<LetterInfo> letters = getCurrentWord();
        for(int i = 0; i < letters.size(); i ++)
        {
            if(letters.get(i).getLetter().equals(letter.getLetter()) &&
                    letters.get(i).getPosition() == letter.getPosition()) return true;
        }
        return false;
    }

    public static Vector<LetterInfo>  getCurrentWord()
    {
        return _currentWord;
    }
    public static void setCurrentWord(Vector<LetterInfo> letters)
    {
        _currentWord = letters;
    }
    public static void addToCurrentWord(LetterInfo letter)
    {
        if(_currentWord == null)
            _currentWord = new Vector<LetterInfo>();
            _currentWord.add(letter);
    }

    public static void removeFromCurrentWord(LetterInfo letter)
    {
        int index = getLetterIndexInWord(letter);
        if(index == -1) throw new Error("Invalid index for letter");
        _currentWord.remove(index);
    }

    public static int getLetterIndexInWord(LetterInfo letter)
    {
        if(getCurrentWord() == null) return -1;
        for(int i = 0; i < getCurrentWord().size(); i++)
        {
            if(getCurrentWord().get(i).getPosition() == letter.getPosition()) return i;
        }
        return -1;
    }

    public static boolean letterExistsInCurrentWord(LetterInfo letter)
    {
        int index = getLetterIndexInWord(letter);
        return (index >= 0);
    }


    private static LetterInfo _lastLetter;
    public static LetterInfo getLastLetter(){ return _lastLetter ; }
    public static void setLastLetter(LetterInfo letter){ _lastLetter = letter; }

    public static LetterInfo getLastButOneLetter()
    {
        if(_currentWord == null) return null;
        if(_currentWord.size() == 0) return null;
        return _currentWord.get(_currentWord.size() - 1);
    }





    public static PlayerController getPlayerC()    {        return _PlayerC;    }
    public static boolean addPlayer(PlayerInfo player){
        return getPlayerC().AddPlayer(player);
    }
    public static  void deletePlayer(String name)    { getPlayerC().DeletePlayer(name);   }
    public static void setCurrentPlayer(String name) { getPlayerC().SetCurrent(name); }
    public static PlayerInfo setCurrentPlayerNext(String name) { return getPlayerC().SetCurrentNext(name); }
    public static PlayerInfo getCurrentPlayer() { return getPlayerC().GetCurrent(); }
    public static void addWordToPlayer(String name, String word){ getPlayerC().AddWord(name, word); }
    public static String getPlayersWords(String name){ return getPlayerC().GetPlayersWords(name); }
    public static boolean wordExistsInPlayersWords(String word){ return  getPlayerC().WordExistsInPlayersWords(word); }
}
