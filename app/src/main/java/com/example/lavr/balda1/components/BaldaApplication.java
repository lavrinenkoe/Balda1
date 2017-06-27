package com.example.lavr.balda1.components;

import android.app.Application;
import android.content.Context;

import com.example.lavr.balda1.Game;
import com.example.lavr.balda1.providers.SqlLiteDataProvider;

/* Created by Lavr on 06.06.2017. */


public class BaldaApplication extends Application {

    private static BaldaApplication _application;
    private static Game _game;
    public static Game getGame()
    {
        return _game;
    }
    public static void setGame(Game game) {
        _game = game;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _application = this;
        _application.initialize();
    }

    public void initialize()
    {
        Context context = this.getApplicationContext();


        setGame(new Game(context));
    }

    public BaldaApplication getApplication()
    {
        return _application;
    }
}