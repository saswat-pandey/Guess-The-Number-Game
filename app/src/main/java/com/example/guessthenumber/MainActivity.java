package com.example.guessthenumber;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private  String TAG="MainActivity";
    private EditText enteredVal;
    private TextView textView,scoreSheet,highScore;
    private Button checkBtn;
    private int randomInteger, guessedNumber, boundryNumber, gameCount, score, hiScore;
    private Random rand;
    private RadioGroup radioGrp;
    private RadioButton radioBtn;
    private Boolean boundrySet=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.title);
        scoreSheet=findViewById(R.id.score);
        highScore=findViewById(R.id.highScore);
        enteredVal = findViewById(R.id.guessedNum);
        radioGrp = findViewById(R.id.myRadioGroup);
        checkBtn = findViewById(R.id.guessButton);

        rand = new Random();
        setRange(R.id.below10);

        gameCount=0;
        score=0;


        SharedPreferences sharedPref= getApplicationContext().getSharedPreferences("guessTheNum",MODE_PRIVATE);

        scoreSheet.append(String.valueOf(score));
        if(sharedPref.contains("hiScore")){
            highScore.append(sharedPref.getString("hiScore","0"));
        }




        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameCount !=3) {
                    Log.d(TAG, "onClick:::::::::::::::::::gameCount "+gameCount);
                    Log.d(TAG, "onClick:::::::::::::::::::ranDom "+randomInteger);
                    disableRadioGroup();
                    gameLogic();
                }else{
                    gameOverLogic();

                }
            }
        });


        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setRange(checkedId);
            }
        });
    }

    public void ToastMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

    }

    public void disableRadioGroup(){
        for(int i = 0; i < radioGrp.getChildCount(); i++){
            (radioGrp.getChildAt(i)).setEnabled(false);
        }
    }

    public void enableRadioGroup(){

        for(int i = 0; i < radioGrp.getChildCount(); i++){
            (radioGrp.getChildAt(i)).setEnabled(true);
        }
    }

    public void setRange(int radioId){
        radioBtn=findViewById(radioId);
        boundryNumber=Integer.valueOf(radioBtn.getText().toString());
        Log.d(TAG, "selectBoundry:::::Boundry:::::: "+boundryNumber);
        Log.d("Activity", "onCreate:::::::::::::::::::: "+randomInteger);
        randomInteger = rand.nextInt(boundryNumber);
        Log.d(TAG, "selectBoundry::::::RanddomInteger::::: "+randomInteger);

    }



    public void gameLogic(){
        try {
            guessedNumber = Integer.valueOf(enteredVal.getText().toString());
        } catch (Exception e) {
            ToastMsg("Enter a Valid Number");
        }
        if (guessedNumber == randomInteger) {
            correctGuess();
        } else if (guessedNumber > randomInteger) {
            gameCount++;
            ToastMsg("Ohh !!! Need to go a little Below that ");
        } else {
            gameCount++;
            ToastMsg("Ohh !!! Need to go a little Above that ");
        }

    }

    public void correctGuess(){
        ToastMsg("Correct!!!!!!! Well Played");
        randomInteger = rand.nextInt(boundryNumber);
        gameCount=0;
        score++;
        scoreSheet.setText("Score: "+String.valueOf(score));
        if(hiScore<=score){
            highScore.setText("HighScore: "+String.valueOf(score));
        }
        enableRadioGroup();
    }

    public void gameOverLogic(){
        if(gameCount==3){
          GameOverDailogue gameOverDailogue= new GameOverDailogue(MainActivity.this,String.valueOf(randomInteger));
          gameOverDailogue.show();
        }else {
            ToastMsg("Select a Range First");
        }
    }

//    public void enableRadioButton(){
//        for(int i = 0; i < radioGrp.getChildCount(); i++){
//            (radioGrp.getChildAt(i)).setEnabled(true);
//        }
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("score",score);
        outState.putSerializable("hiScore",hiScore);
        outState.putSerializable("boundryNumber",boundryNumber);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        score= (int) savedInstanceState.getSerializable("score");
        hiScore= (int) savedInstanceState.getSerializable("hiScore");
        boundryNumber= (int) savedInstanceState.getSerializable("boundryNumber");
    }
}