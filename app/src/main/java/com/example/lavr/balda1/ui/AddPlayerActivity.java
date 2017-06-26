package com.example.lavr.balda1.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lavr.balda1.Game;
import com.example.lavr.balda1.R;
import com.example.lavr.balda1.components.BaldaApplication;
import com.example.lavr.balda1.components.LetterInfo;
import com.example.lavr.balda1.components.PlayerInfo;

import java.util.Vector;

public class AddPlayerActivity extends AppCompatActivity {
    ListView lvPlayers;
    ArrayAdapter<String> _adapter;
    public ArrayAdapter<String> getAdapter()
    {
        return _adapter;
    }
    public void setAdapter(ArrayAdapter<String> arr)
    {
        _adapter = arr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
         //   public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
          //      addPlayer(view);
        //    }
        //});
        lvPlayers = (ListView) findViewById(R.id.lvPlayers);
        lvPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                //Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cmdAddTwoPlayers(View view)
    {
        PlayerInfo player = new PlayerInfo("Ираклий");
        Game.addPlayer(player);
        PlayerInfo player2 = new PlayerInfo("Валерьян");
        Game.addPlayer(player2);

        String[] names = Game.getPlayerNames();
        Game.setCurrentPlayer(names[0]);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /// на нажатие кнопки "Сохранить"
    public void addPlayer(View view) {

        TextView textView = (TextView) findViewById(R.id.edPlayerName);
        if(textView.getText().toString().trim().equals("")) return;

        PlayerInfo player = new PlayerInfo(textView.getText().toString());
        boolean playerAdded = Game.addPlayer(player);
        if(!playerAdded)
            Toast.makeText(getApplicationContext(), String.format("Player with name {0} already exists!", player.getName()), Toast.LENGTH_SHORT).show();

        String[] playerNames = Game.getPlayerNames();
        UpdateList(playerNames);
        textView.setText("");
    }

    public void deleteSelectedPlayers(View view)
    {
        SparseBooleanArray checked = lvPlayers.getCheckedItemPositions();
        for (int i = 0; i < lvPlayers.getAdapter().getCount(); i++) {
            if (checked.get(i)) {
                checked.get(i);
                AppCompatCheckedTextView acctv = (AppCompatCheckedTextView)lvPlayers.getChildAt(i);
                String text = acctv.getText().toString();
                Game.deletePlayer(text);
            }
        }
        String[] playerNames = Game.getPlayerNames();
        UpdateList(playerNames);
    }

    /// обновляет таблицу в соответствии с массивом _letters
    private void UpdateList(String[] letters)
    {
        setAdapter( new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, letters));
        lvPlayers.setAdapter(getAdapter());
    }


    public void goToNewGame(View view)
    {
        if(Game.getPlayers().size() < 2)
        {
            Toast.makeText(getApplicationContext(), "Number of players should be more than 2!", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] names = Game.getPlayerNames();
        Game.setCurrentPlayer(names[0]);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
