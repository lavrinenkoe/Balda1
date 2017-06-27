package com.example.lavr.balda1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.lavr.balda1.Game;
import com.example.lavr.balda1.R;

public class AddWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        Intent it = getIntent();
        String word = (String) it.getSerializableExtra("word");

        TextView tvText = (TextView) findViewById(R.id.tvText);
        TextView tvWord = (TextView) findViewById(R.id.tvWord);

        tvText.setText("Word '" + word + "' doesn't exist in dictionary. Add the word?");
        tvWord.setText(word);
    }

    public void cmdAddWord(View view){
        TextView tvWord = (TextView) findViewById(R.id.tvWord);
        String word = tvWord.getText().toString();
        Game.addWordToDictionary(word, this.getApplicationContext());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("word", word);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelAddWord(View view){
        finish();
    }
}
