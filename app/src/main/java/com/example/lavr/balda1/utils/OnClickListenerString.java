package com.example.lavr.balda1.utils;

import android.content.DialogInterface;
import android.view.View;

/**
 * Created by Lavr on 09.06.2017.
 */

public class OnClickListenerString implements View.OnClickListener {
    String _value;
    public String getOnClickListenerStringValue() { return _value; }
    public OnClickListenerString(String value) {
        this._value = value;
    }

    @Override
    public void onClick(View v)
    {
        //read your lovely variable
    }
}
