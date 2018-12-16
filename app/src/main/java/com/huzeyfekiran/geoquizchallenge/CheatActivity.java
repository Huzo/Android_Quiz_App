package com.huzeyfekiran.geoquizchallenge;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_COOL = "com.huzeyfekiran.geoquizchallenge.answer_is_cool";
    private static final String EXTRA_ANSWER_SHOWN = "com.huzeyfekiran.geoquizchallenge.answer_shown";

    private boolean answerIsCool;

    private TextView textSure;
    private TextView textAnswer;
    private Button buttonAnswer;

    public static Intent newIntent(Context packageContext, boolean answerIsCool){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_COOL, answerIsCool);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        answerIsCool = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_COOL, false);

        textSure = (TextView) findViewById(R.id.textSure);

        textAnswer = (TextView) findViewById(R.id.textAnswer);

        buttonAnswer = (Button) findViewById(R.id.buttonAnswer);
        buttonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textSure.setText(R.string.show_answer_cheater);

                if(answerIsCool){
                    textAnswer.setText(R.string.button_yes);
                } else{
                    textAnswer.setText(R.string.button_no);
                }
                setAnswerShownResult(true);
            }
        });
    }
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
