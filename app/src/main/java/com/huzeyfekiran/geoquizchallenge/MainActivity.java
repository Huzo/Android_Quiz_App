package com.huzeyfekiran.geoquizchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATED = "isCheater";
    private static final String KEY_ANSWERED = "isAnswered";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button buttonYes;
    private Button buttonNo;
    private Button buttonCheat;
    private ImageButton imageButtonNext;
    private ImageButton imageButtonPrev;
    private TextView textQuestion;

    private Question[] questions = new Question[]{
            new Question(R.string.question_root, R.string.question_root_yes, R.string.question_root_no, true),
            new Question(R.string.question_joke, R.string.question_joke_yes, R.string.questoin_joke_no, false)
    };

    private int currentIndex = 0;

    private ArrayList<Integer> didCheat;
    private ArrayList<Integer> isAnswered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate(Bundle) created");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            didCheat = savedInstanceState.getIntegerArrayList(KEY_CHEATED);
            isAnswered = savedInstanceState.getIntegerArrayList(KEY_ANSWERED);
        }
        else{
            didCheat = new ArrayList<Integer>();
            isAnswered = new ArrayList<Integer>();
        }

        textQuestion = (TextView) findViewById(R.id.textQuestion);
        updateQuestion();

        buttonYes = (Button) findViewById(R.id.buttonYes);
        if(isAnswered.contains(currentIndex)){buttonYes.setVisibility(View.INVISIBLE);}
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this,questions[currentIndex].getQuestionYesResponse(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.show();
                isAnswered.add(currentIndex);
                checkFinished();
                if(isAnswered.contains(currentIndex)){
                    hideYesNo();
                    hideCheat();
                }
            }
        });

        buttonNo = (Button) findViewById(R.id.buttonNo);
        if(isAnswered.contains(currentIndex)){buttonNo.setVisibility(View.INVISIBLE);}
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, questions[currentIndex].getQuesitonNoResponse(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.show();
                isAnswered.add(currentIndex);
                checkFinished();
                if(isAnswered.contains(currentIndex)){
                    hideYesNo();
                    hideCheat();
                }
            }
        });

        buttonCheat = (Button) findViewById(R.id.buttonCheat);
        if(isAnswered.contains(currentIndex)){buttonCheat.setVisibility(View.INVISIBLE);}
        buttonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAnswerCool = questions[currentIndex].gettheCoolAnswer();
                Intent intent = CheatActivity.newIntent(MainActivity.this, isAnswerCool);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        imageButtonNext = (ImageButton) findViewById(R.id.imageButtonNext);
        imageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questions.length;
                updateQuestion();
                if(!isAnswered.contains(currentIndex)){
                    showYesNo();
                    showCheat();
                } else{
                    hideYesNo();
                    hideCheat();
                }
            }
        });

        imageButtonPrev = (ImageButton) findViewById(R.id.imageButtonPrev);
        imageButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex == 0){
                    currentIndex = questions.length -1; //go to the last
                } else{
                    currentIndex = currentIndex - 1;
                }
                updateQuestion();
                if(!isAnswered.contains(currentIndex)){
                    showYesNo();
                    showCheat();
                } else {
                    hideYesNo();
                    hideCheat();
                }
            }
        });

        updateQuestion();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            boolean isCheater = CheatActivity.wasAnswerShown(data);
            if(isCheater){
                didCheat.add(currentIndex);
                isAnswered.add(currentIndex);
                hideYesNo();
                hideCheat();
                checkFinished();
                Log.i(TAG, currentIndex + " added as cheated.");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
        savedInstanceState.putIntegerArrayList(KEY_CHEATED, didCheat);
        savedInstanceState.putIntegerArrayList(KEY_ANSWERED, isAnswered);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop) called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    private void updateQuestion(){
        int question = questions[currentIndex].getTextResId();
        textQuestion.setText(question);
    }

    private void showYesNo(){
        buttonYes.setVisibility(View.VISIBLE);
        buttonNo.setVisibility(View.VISIBLE);
    }

    private void hideYes(){
        buttonYes.setVisibility(View.INVISIBLE);
    }

    private void hideNo(){
        buttonNo.setVisibility(View.INVISIBLE);
    }

    private void hideYesNo(){
        buttonYes.setVisibility(View.INVISIBLE);
        buttonNo.setVisibility(View.INVISIBLE);
    }

    private void showCheat(){
        buttonCheat.setVisibility(View.VISIBLE);
    }

    private void hideCheat(){
        buttonCheat.setVisibility(View.INVISIBLE);
    }

    private void checkFinished(){
        boolean allAnswered = true;
        for(int i = 0; i < questions.length; i++){
            if(!isAnswered.contains(i)){
                allAnswered = false;
            }
        }
        if(allAnswered){
            Toast toast = Toast.makeText(MainActivity.this,"Your total coolness level is "  + ((Math.random() * 50) + 50) + "%",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP,0,0);
            toast.show();
            hideCheat();
        }
    }
}
