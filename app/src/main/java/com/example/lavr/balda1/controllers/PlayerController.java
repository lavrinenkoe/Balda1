package com.example.lavr.balda1.controllers;

import com.example.lavr.balda1.components.PlayerInfo;
import com.example.lavr.balda1.components.SearchResult;

import java.util.List;
import java.util.Set;
import java.util.Vector;

/* Created by Lavr on 01.06.2017. */

public class PlayerController {
    private Vector<PlayerInfo> _players;
    public PlayerController()
    {
        _players = new Vector<>();
    }

    public Vector<PlayerInfo> GetAllPlayers(){
        return _players;
    }

    private SearchResult PlayerExists(String name)
    {
        int index = -1;
        for(int i = 0; i < _players.size(); i++)
        {
            if(_players.get(i).getName().equals(name))
                index = i;
        }

        boolean isFound = (index > -1);
        return new SearchResult(name, index, isFound);
    }

    public boolean AddPlayer(PlayerInfo player)
    {
        SearchResult r = PlayerExists(player.getName());
        if(r.isFound) return false;

        _players.add(player);
        return true;
    }
    public void DeletePlayer(String name)
    {
        SearchResult r = PlayerExists(name);
        if(r.isFound)
            _players.remove(r.index);
    }

    public PlayerInfo Get(String name){
        SearchResult r = PlayerExists(name);
        if(r.isFound)
            return _players.get(r.index);

        throw new Error(String.format("Player with name %1$s doesn't exist", name));
    }

    public void SetCurrent(String name)
    {
        for(int i = 0; i < _players.size(); i++)
        {
            if(_players.get(i).getName().equals(name))
                _players.get(i).setIsCurrent(true);
            else
                _players.get(i).setIsCurrent(false);
        }
    }
    private void SetCurrent(int index)
    {
        for(int i = 0; i < _players.size(); i++)
        {
            if(i == index)
                _players.get(i).setIsCurrent(true);
            else
                _players.get(i).setIsCurrent(false);
        }
    }

    // проставляет следующего текущего игрока по кругу
    public PlayerInfo SetCurrentNext(String name) {
        int index = GetPlayerIndex(name);
        if(index == _players.size() - 1) {
            SetCurrent(0);
            return _players.get(0);
        }
        else {
            SetCurrent(index + 1);
            return _players.get(index + 1);
        }
    }


    private int GetPlayerIndex(String name)
    {
        for(int i = 0; i < _players.size(); i++)
        {
            if(_players.get(i).getName().equals(name)) return i;
        }
        return -1;
    }


    public PlayerInfo GetCurrent()
    {
        for(int i = 0; i < _players.size(); i++)
        {
            if(_players.get(i).getIsCurrent()) return _players.get(i);
        }
        throw new Error("No player is set as current!");
    }

    public void AddWord(String name, String word)
    {
        int i = GetPlayerIndex(name);
        _players.get(i).addWord(word);
    }

    public String GetPlayersWords(String name)
    {
        int index = GetPlayerIndex(name);
        Vector<String> words = _players.get(index).getWords();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < words.size(); i++) {
            sb.append(i).append(". ").append(words.get(i)).append("\n");
        }
        return sb.toString();
    }

    public boolean WordExistsInPlayersWords(String word)
    {
        for(int i = 0; i < _players.size(); i++)
        {
            Vector<String> words = _players.get(i).getWords();
            for(int j = 0; j < words.size(); j++)
            {
                if(words.get(j).equals(word)) return true;
            }
        }
        return false;
    }
}
