package com.example.lavr.balda1.controllers;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.example.lavr.balda1.components.LetterInfo;

import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by Lavr on 30.05.2017.
 */

public class WordsController {

    private static Vector<String> _Words;

    public static Vector<String> getWords(Context context) {
        if (_Words == null) {
            _Words = getAllWords(context);
        }
        return _Words;
    }

    public static void setWords(Vector<String> words) {
        _Words = words;
    }


    private static char[] ALPHABET = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};

    public static String[] getAlphabet() {
        String[] arr = new String[33];
        for (int i = 0; i < ALPHABET.length; i++) {
            arr[i] = String.valueOf(ALPHABET[i]);
        }
        return arr;
    }

    public static Vector<LetterInfo> getInitialArray(Context context) {
        Vector<LetterInfo> vector = new Vector<LetterInfo>();
        String word = getRandomWord(context);

        for (int i = 0; i < 25; i++) {
            String s = (i < 10 || i >= 15) ? " " : getInitialWord(i, context, word);
            LetterInfo letter = new LetterInfo(s, i);
            vector.add(letter);
        }
        return vector;
    }

    private static String getRandomWord(Context context) {
        Vector<String> list = getAllWords(context);
        Vector<String> fiveLetter = new Vector<String>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).length() == 5) {
                fiveLetter.add(list.get(i));
            }
        }

        int a = 0;
        int b = fiveLetter.size();
        int random_number = a + (int) (Math.random() * b);

        return fiveLetter.get(random_number);
    }

    private static Vector<String> getAllWords(Context context) {
        Vector<String> list = new Vector<String>();
        ContextWrapper c = new ContextWrapper(context);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(c.getAssets().open("dictionary")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                list.add(mLine.toUpperCase());
            }
        } catch (IOException e) {
            //log the exception
            Log.e("Balda1", "exception", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return list;
    }

    public static boolean wordExists(String word, Context context) {
        Vector<String> words = getWords(context);
        for (int i = 0; i < words.size(); i++) {
            if (word.toLowerCase().trim() == words.get(i).toLowerCase().trim()) return true;
        }
        return false;
    }

    private static String getInitialWord(int index, Context context, String word) {
        char[] arr = word.toCharArray();
        return String.valueOf(arr[index - 10]);
    }

    public static boolean checkValidLetter(String letter) {
        if (letter.length() > 1)
            throw new Error("string chould be one letter!");

        for (int i = 0; i < ALPHABET.length; i++) {
            if (letter.toUpperCase().trim().charAt(0) == ALPHABET[i]) return true;
        }
        return false;
    }

    public static boolean isSelectionValid(int prevSelection, int currentSelection) {
        if (prevSelection < 0) return true;

        // предполагается, что нет значений, выходящих за рамки массива
        //if(prevSelection < 0 || prevSelection > 24 || currentSelection < 0 || currentSelection > 24) throw new Error("Selection is out of range!");
        //граничные условия
        //if(prevSelection == 0) return (currentSelection == 1 || currentSelection == 5);
        //if(prevSelection == 4) return (currentSelection == 3 || currentSelection == 9);
        //if(prevSelection == 20) return (currentSelection == 15 || currentSelection == 21);
        //if(prevSelection == 24) return (currentSelection == 19 || currentSelection == 23);

        if (prevSelection % 5 != 0 && (prevSelection - 4) % 5 != 0) {
            if (prevSelection + 1 == currentSelection ||
                    prevSelection - 1 == currentSelection ||
                    prevSelection + 5 == currentSelection ||
                    prevSelection - 5 == currentSelection) return true;
        } else {
            // 0 5 10 15 20
            if (prevSelection % 5 == 0) {
                return (prevSelection == currentSelection - 1 || prevSelection == currentSelection + 5 || prevSelection == currentSelection - 5);
            } else // 4 9 14 19 24
            {
                return (prevSelection == currentSelection + 1 || prevSelection == currentSelection - 5 || prevSelection == currentSelection + 5);
            }
        }

        return false;
    }

    // проверяет, является ли снятие выбора с буквы нарушением цепочки, из которого выстраивается текущее слово
    public static boolean IsDeselectionRuinsWord(LetterInfo letter, Vector<LetterInfo> currentWord) {
        Vector<LetterInfo> temp = new Vector<LetterInfo>(currentWord.size());
        temp.setSize(currentWord.size());
        Collections.copy(temp, currentWord);
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getPosition() == letter.getPosition())
                temp.remove(i);
        }

        for (int i = 1; i < temp.size(); i++) {
            boolean isSelectionValid = isSelectionValid(temp.get(i).getPosition(), temp.get(i - 1).getPosition());
            if (!isSelectionValid) return true;
        }
        return false;
    }
}