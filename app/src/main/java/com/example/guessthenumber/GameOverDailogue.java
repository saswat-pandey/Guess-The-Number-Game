package com.example.guessthenumber;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


/**
 * To create a new custom Dailogue we need to extend the Dailog
 * and implement the android.view.View.OnClickListener
 *  Two methods are to be implemented compulsory:
 *  *)Consturctor -->parameter as the activity/context
 *  *)onClickListener
 */
public class GameOverDailogue extends Dialog implements
        android.view.View.OnClickListener  {

    Activity currActivity;
    Button retry,exit;
    String generatedNumber;
    TextView gmOver;


    public GameOverDailogue( Activity activity, String number) {
        super(activity);
        this.currActivity=activity;
        this.generatedNumber=number;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_over);
        gmOver=findViewById(R.id.txt_dia);
        gmOver.append("\n");
        gmOver.append("The Correct Number: "+generatedNumber);
        retry=findViewById(R.id.btn_retry);
        exit=findViewById(R.id.btn_exit);
        retry.setOnClickListener(this);
        exit.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_retry:
                currActivity.recreate();
                break;
            case R.id.btn_exit:
                currActivity.finish();
                break;
            default:
                 break;

        }
    }
}
