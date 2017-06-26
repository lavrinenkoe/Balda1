package com.example.lavr.balda1.ui;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.lavr.balda1.Game;
import com.example.lavr.balda1.R;
import com.example.lavr.balda1.components.BaldaApplication;
import com.example.lavr.balda1.components.PlayerInfo;
import com.example.lavr.balda1.components.LetterInfo;
import com.example.lavr.balda1.controllers.WordsController;
import com.example.lavr.balda1.utils.OnClickListenerString;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {

    final int PLAYER_WIDTH = 200;
    final int PLAYER_HEIGHT = 60;

    PopupWindow popUpWindow;

    private ArrayAdapter<String> _adapter;
    public ArrayAdapter<String> getAdapter()
    {
        return _adapter;
    }
    public void setAdapter(ArrayAdapter<String> arr)
    {
        _adapter = arr;
    }

    public GridView gridview;
    public ListView lvPlayzonePlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        gridview = (GridView) findViewById(R.id.gridview);

        Vector<LetterInfo> letters = ((BaldaApplication) this.getApplication()).getGame().getLetters();
        Vector<PlayerInfo> players = ((BaldaApplication) this.getApplication()).getGame().getPlayers();

        UpdateGrid(letters);
       setPlayersList(players);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                OnGridViewClick(position, id);
            }
        });
    }

    /// на клик по ячейке таблицы
    private void OnGridViewClick(int position, long id)
    {
        // получу выделенный эл-т текствью
        LinearLayout layout = (LinearLayout) gridview.getChildAt(position);
        TextView textView = (TextView) layout.getChildAt(0);
        String letterText =  textView.getText().toString();
        LetterInfo possible = new LetterInfo(letterText, position);
        boolean letterExistsInCurrentWord = ((BaldaApplication) this.getApplication()).getGame().letterExistsInCurrentWord(possible);

        // если это новая буква, обозначу выделение, добавлю букву к слову и выставлю индекс
        if(letterText != " ") {
            if(((BaldaApplication) this.getApplication()).getGame().getLastLetter() == null) return;

            //Vector<LetterInfo> letters = ((BaldaApplication) this.getApplication()).getGame().getLetters();
            LetterInfo prevSelected = Game.getLastButOneLetter();
            LetterInfo letter = new LetterInfo(letterText, position);
            boolean isSelectionValid = (prevSelected == null) ? true : WordsController.isSelectionValid(prevSelected.getPosition(), position);

            if(isSelectionValid  && (!letterExistsInCurrentWord)) {
                layout.setBackgroundResource(R.drawable.rect_selected);
                ((BaldaApplication) this.getApplication()).getGame().addToCurrentWord(letter);
            }
            else
            {
                cleanSelection();
            }
        }
        else
        {
            if(((BaldaApplication) this.getApplication()).getGame().getLastLetter() != null) return;

            Intent intent = new Intent(this, DisplayAlphabet.class);
            LetterInfo l = new LetterInfo("", position);
            intent.putExtra("letter", l);
            startActivityForResult(intent, 0);
        }
    }

    /// на закрытие диалогового окна
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null) return;

        LetterInfo letter = (LetterInfo) data.getSerializableExtra("letter");
        ((BaldaApplication) this.getApplication()).getGame().setLastLetter(letter);
        LinearLayout layout = (LinearLayout) gridview.getChildAt(letter.getPosition());
        TextView textView = (TextView) layout.getChildAt(0);
        textView.setText(letter.getLetter());
    }

    /// на нажатие кнопки "Сохранить"
    public void completeWord(View view)
    {
        // получу слово
        String word = ((BaldaApplication) this.getApplication()).getGame().getCurrentWordString();
        if(word == "") {

            return;
        }
        if(((BaldaApplication) this.getApplication()).getGame().wordExistsInPlayersWords(word))
        {
            String message = "Word " + word + " already exists in players words!";
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
        }

        PlayerInfo currentPlayer = ((BaldaApplication) this.getApplication()).getGame().getCurrentPlayer();
        // проверю на соответствие
        boolean exists = WordsController.wordExists(word, this);
        // если нет в словаре, предложу добавить
        //if(!exists) {
        //    String message = String.format("Word %1$s doesn't exist in dictionary.", word);
        //    Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //    return;
        //}
        // если есть, добавлю слово текущему игроку
        ((BaldaApplication) this.getApplication()).getGame().addWordToPlayer(currentPlayer.getName(), word);
        // обновить количество очков у игрока
        setDisplayPlayerScore(currentPlayer);
        // изменю текущего игрока на следующего по списку
        currentPlayer = ((BaldaApplication) this.getApplication()).getGame().setCurrentPlayerNext(currentPlayer.getName());
        // проставлю новому игроку болд
        setBoldToPlayer(currentPlayer);
        // прокручу список игроков до нового активного игрока
        //HorizontalScrollView hsv = (HorizontalScrollView)findViewById(R.id.hsvPlayers);
        //hsv.scrollTo(PLAYER_WIDTH, hsv.getBottom());

        // зафиксировать текущую букву в массиве
        Vector<LetterInfo> letters = ((BaldaApplication) this.getApplication()).getGame().getLetters();
        LetterInfo last = ((BaldaApplication) this.getApplication()).getGame().getLastLetter();
        letters.set(last.getPosition(), last);
        ((BaldaApplication) this.getApplication()).getGame().setLetters(letters);

        // снять выделение у букв, очистить текущее слово
        cleanCurrentWord(null);
    }





    private void playerInfoClick(View view)
    {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cleanSelection()
    {
        ((BaldaApplication) this.getApplication()).getGame().setCurrentWord(new Vector<LetterInfo>());
        for(int position = 0; position < gridview.getChildCount(); position++) {
            LinearLayout layout = (LinearLayout) gridview.getChildAt(position);
            layout.setBackgroundResource(R.drawable.rect);
        }
    }

    private void cleanCurrentWord(LetterInfo letter)
    {
        ((BaldaApplication) this.getApplication()).getGame().setCurrentWord(new Vector<LetterInfo>());
        ((BaldaApplication) this.getApplication()).getGame().setLastLetter(null);
        for(int position = 0; position < gridview.getChildCount(); position++) {
            LinearLayout layout = (LinearLayout) gridview.getChildAt(position);
            TextView textView = (TextView) layout.getChildAt(0);
            layout.setBackgroundResource(R.drawable.rect);
            if(letter != null)
                if(letter.getPosition() == position)
                    textView.setText(" ");
        }
    }


    private void setDisplayPlayerScore(PlayerInfo player)
    {
        ViewGroup linearLayout = (ViewGroup) findViewById(R.id.llTop);

        for( int i = 0; i < linearLayout.getChildCount(); i++ )
            if( linearLayout.getChildAt( i ) instanceof LinearLayout ) {
                LinearLayout ll = (LinearLayout)linearLayout.getChildAt( i );
                TextView tvn =  (TextView) ll.getChildAt(0);
                TextView tvs =  (TextView) ll.getChildAt(1);
                if(tvn.getText().toString() == player.getName()) {
                    tvs.setText(Integer.toString(player.getScore()));
                    return;
                }
            }

    }

    private void setBoldToPlayer(PlayerInfo player)
    {
        ViewGroup linearLayout = (ViewGroup) findViewById(R.id.llTop);

        for( int i = 0; i < linearLayout.getChildCount(); i++ )
            if( linearLayout.getChildAt( i ) instanceof LinearLayout ) {
                LinearLayout ll = (LinearLayout)linearLayout.getChildAt( i );
                TextView tv =  (TextView) ll.getChildAt(0);
                if(tv.getText().toString() == player.getName())
                    tv.setTypeface(null, Typeface.BOLD);
                else
                    tv.setTypeface(null, Typeface.NORMAL);
            }
    }

    /// на нажатие кнопки "Отмена"
    public void cancelWord(View vew)
    {
        cleanCurrentWord(((BaldaApplication) this.getApplication()).getGame().getLastLetter());
    }

    // на нажатие кнопки пропустить ход
    public void passTurn(View view)
    {
        cleanCurrentWord(((BaldaApplication) this.getApplication()).getGame().getLastLetter());
        ((BaldaApplication) this.getApplication()).getGame().setCurrentPlayerNext(((BaldaApplication) this.getApplication()).getGame().getCurrentPlayer().getName());

        setBoldToPlayer(((BaldaApplication) this.getApplication()).getGame().getCurrentPlayer());
    }

    /// обновляет таблицу в соответствии с массивом _letters
    private void UpdateGrid(Vector<LetterInfo> letters)
    {
        String[] arr = new String[25];
        for(int i = 0; i < arr.length; i++)
        {
            arr[i] = letters.get(i).getLetter();
        }

        setAdapter( new ArrayAdapter<String>(this, R.layout.item, R.id.evText, arr));
        gridview.setAdapter(getAdapter());
    }

    //private void

    private void setPlayersList(Vector<PlayerInfo> players)
    {
        ViewGroup linearLayout = (ViewGroup) findViewById(R.id.llTop);

        for(int i = 0; i < players.size(); i++)
        {
            PlayerInfo player = players.get(i);
            LinearLayout ll = new LinearLayout(this);

            TextView tvn = new TextView(this);
            if(player.getIsCurrent())
                tvn.setTypeface(null, Typeface.BOLD);

            tvn.setText(player.getName());
            tvn.setLayoutParams(new ViewGroup.LayoutParams(PLAYER_WIDTH, PLAYER_HEIGHT));

            ll.addView(tvn);
            TextView tvs = new TextView(this);
            int score = player.getScore();
            tvs.setText(Integer.toString(score));
            tvs.setLayoutParams(new ViewGroup.LayoutParams(PLAYER_WIDTH, PLAYER_HEIGHT));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            params.gravity = Gravity.CENTER_VERTICAL;

            ll.setOnClickListener(new OnClickListenerString(player.getName()) {
                @Override
                public void onClick(View view) {
                    String words = Game.getPlayersWords(getOnClickListenerStringValue());
                    openPopupWindow(words);
                    //Snackbar.make(view, words, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
            ll.addView(tvs);
            ll.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(ll);
        }
    }

    private void openPopupWindow(String message)
    {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.popup, (ViewGroup) findViewById(R.id.popup_element));
            // create a 300px width and 470px height PopupWindow
            popUpWindow = new PopupWindow(layout, 300, 470, true);
            // display the popup in the center
            popUpWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            TextView tvWords = (TextView) layout.findViewById(R.id.tvWords);
            tvWords.setText(message);
            Button cancelButton = (Button) layout.findViewById(R.id.end_data_send_button);
            cancelButton.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            popUpWindow.dismiss();
        }
    };
}
