package com.example.lavr.balda1;

import com.example.lavr.balda1.components.LetterInfo;
import com.example.lavr.balda1.components.PlayerInfo;
import com.example.lavr.balda1.controllers.WordsController;
import com.example.lavr.balda1.ui.MainActivity;


import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }




    @Test
    public void isSelectionValid() throws Exception{
        //Context ctx = new MockContext();

//        Vector<LetterInfo> arr = new Vector<LetterInfo>(); // Utils.GetInitialArray(a);
//        for(int i = 0; i < arr.size(); i++)
//        {
//            String s = " ";
//            if(i > 9 && i <15)
//                s = "Z";
//            LetterInfo letter = new LetterInfo(s, i);
//            arr.add(letter);
//        }
        boolean zero = WordsController.isSelectionValid( 0, 1);
        assertTrue(zero);
        zero = WordsController.isSelectionValid( 0, 5);
        assertTrue(zero);
        zero = WordsController.isSelectionValid( 0, 6);
        assertTrue(!zero);

        boolean four = WordsController.isSelectionValid( 4, 3);
        assertTrue(four);
        four = WordsController.isSelectionValid( 4, 9);
        assertTrue(four);
        four = WordsController.isSelectionValid( 4, 5);
        assertTrue(!four);
        four = WordsController.isSelectionValid( 4, 8);
        assertTrue(!four);


        boolean five = WordsController.isSelectionValid( 5, 0);
        assertTrue(five);
        five = WordsController.isSelectionValid(5, 10);
        assertTrue(five);
        five = WordsController.isSelectionValid( 5, 6);
        assertTrue(five);
        five = WordsController.isSelectionValid( 5, 1);
        assertTrue(!five);
        five = WordsController.isSelectionValid( 5, 4);
        assertTrue(!five);

        boolean nine = WordsController.isSelectionValid( 9, 4);
        assertTrue(nine);
        nine = WordsController.isSelectionValid( 9, 8);
        assertTrue(nine);
        nine = WordsController.isSelectionValid( 9, 14);
        assertTrue(nine);
        nine = WordsController.isSelectionValid( 9, 10);
        assertTrue(!nine);
        nine = WordsController.isSelectionValid( 9, 13);
        assertTrue(!nine);

        boolean six = WordsController.isSelectionValid( 6, 1);
        assertTrue(six);
        six = WordsController.isSelectionValid( 6, 5);
        assertTrue(six);
        six = WordsController.isSelectionValid( 6, 7);
        assertTrue(six);
        six = WordsController.isSelectionValid( 6, 11);
        assertTrue(six);
        six = WordsController.isSelectionValid( 6, 0);
        assertTrue(!six);
        six = WordsController.isSelectionValid( 6, 8);
        assertTrue(!six);

        boolean tfour = WordsController.isSelectionValid( 24, 19);
        assertTrue(tfour);
        tfour = WordsController.isSelectionValid( 24, 23);
        assertTrue(tfour);
        tfour = WordsController.isSelectionValid( 24, 18);
        assertTrue(!tfour);
    }
}