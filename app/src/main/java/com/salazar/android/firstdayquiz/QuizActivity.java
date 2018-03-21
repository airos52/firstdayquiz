package com.salazar.android.firstdayquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";
 //   private static final String KEY_ANSWERED = "answer";

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mFirstName;
    private Button mNextButton;
    private Button mBackButton;
    private Button mRandButton;
    private Button mHintButton;
    private TextView mQuestionTextView;
    private TextView mCurrentScoreTextView;
    private String defaultName;
    private Button mCheatButton;


    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_text1, true, R.string.hint_text1),
            new Question(R.string.question_text2, false, R.string.hint_text2),
            new Question(R.string.question_text3, true,  R.string.hint_text3),
            new Question(R.string.question_text4, true,  R.string.hint_text4),
            new Question(R.string.question_text5, false,  R.string.hint_text5),
            new Question(R.string.question_text6, true,  R.string.hint_text6),
    };

    private int mCurrentIndex = 0;
    private int mCurrentScore = 0;


    private void displayScore(){
        TextView mCurrentScoreTextView = (TextView) findViewById(R.id.current_score_view);
        mCurrentScoreTextView.setText(defaultName + " current score is " + mCurrentScore);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Log.d(TAG, "the system just caled onCreate(Bundle) and life is good?");


        mCurrentScoreTextView = (TextView) findViewById(R.id.current_score_view);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mBackButton = (Button) findViewById(R.id.back_button);
        mRandButton = (Button) findViewById(R.id.rand_button);
        mFirstName = (TextView) findViewById(R.id.first_name);
        mHintButton = (Button) findViewById(R.id.hint_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);


        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCurrentScore = savedInstanceState.getInt(KEY_SCORE, 0);
        }

//these methods are used to update the screen for the fields they control
        updatedQuestion();
        updateName();
        displayScore();


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateName();
                checkAnswer(true);
//                               Toast.makeText(QuizActivity.this, mFirstName.getText() + " is " + getString(R.string.correct_toast), Toast.LENGTH_SHORT).show();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateName();
                checkAnswer(false);
//                               Toast.makeText(QuizActivity.this, mFirstName.getText() + " is " + getString(R.string.false_toast), Toast.LENGTH_SHORT).show();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex != mQuestionBank.length -1)
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updatedQuestion();
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex--;
                if (mCurrentIndex== -1)
                    mCurrentIndex = 0;
                updatedQuestion();
            }
        });
        mRandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random randGen = new Random();
                mCurrentIndex= randGen.nextInt(mQuestionBank.length);
                updatedQuestion();
            }
        });
        mHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedHint();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start CheatActivity
                Intent intent = CheatActivity.newIntent(QuizActivity.this,
                        mQuestionBank[mCurrentIndex].isAnswer());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "system called onPaused");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "system called onPaused");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_SCORE, mCurrentScore);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "system called onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "system called onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "system called onDestroy");
    }

    private void updatedHint() {
        int hint = mQuestionBank[mCurrentIndex].getHintResId();
//        mHintResId.setText(hint);
        Toast.makeText(this,hint, Toast.LENGTH_SHORT).show();

    }

    private void updatedQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

    }
    private void upScore(){
        mCurrentScore = mCurrentScore +1;

    }
    private void downScore(){
        mCurrentScore = mCurrentScore -1;

    }

    private void updateName() {

        if (mFirstName.getText().toString().trim().length() == 0) {
            defaultName = "Your";
        } else {
            defaultName = mFirstName.getText().toString();
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswer();

        int setTextResId = 0;

        if (userPressedTrue == answerIsTrue) {
            setTextResId = R.string.correct_toast;
            upScore();
            displayScore();
        } else {
            setTextResId = R.string.false_toast;
            downScore();
            displayScore();
        }

        Toast.makeText(this, defaultName + " guess is " + getString(setTextResId), Toast.LENGTH_SHORT).show();
    }
}
