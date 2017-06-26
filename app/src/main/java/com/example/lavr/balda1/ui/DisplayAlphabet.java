package com.example.lavr.balda1.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lavr.balda1.R;

import com.example.lavr.balda1.components.LetterInfo;
import com.example.lavr.balda1.controllers.WordsController;

public class DisplayAlphabet
        extends AppCompatActivity {

    private GridView gvAlphabet;
    private ArrayAdapter<String> adapter;
    private LetterInfo Letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabet);

        gvAlphabet = (GridView) findViewById(R.id.gvAlphabet);
        UpdateGrid(WordsController.getAlphabet());
        Intent it = getIntent();
        Letter = (LetterInfo) it.getSerializableExtra("letter");


        gvAlphabet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                SetLetter(position, id);
            }
        });
    }

    private void SetLetter(int position, long id) {
        LinearLayout layout = (LinearLayout) gvAlphabet.getChildAt(position);
        TextView textView = (TextView) layout.getChildAt(0);
        Intent intent = new Intent(this, MainActivity.class);
        Letter.setLetter(textView.getText().toString());
        intent.putExtra("letter", Letter);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void UpdateGrid(String[] letters) {
        adapter = new ArrayAdapter<String>(this, R.layout.item_alphabet, R.id.tvLetter, letters);
        gvAlphabet.setAdapter(adapter);
    }
}
