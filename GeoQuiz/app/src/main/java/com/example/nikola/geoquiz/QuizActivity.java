package com.example.nikola.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;

public class QuizActivity extends AppCompatActivity {

    private static final String KEY_ANSWERED_QUESTIONS = "AnsweredQuestions";
    private static final String KEY_IS_CHEATER = "IsCheater";
    private static final String KEY_REMAINING_CHEATS = "RemainingCheats";
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private Button mCheatButton;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private static final Question[] sQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private HashSet<Integer> mAnsweredQuestions = new HashSet<Integer>();

    private int mCurrentIndex = 0;

    private int mCorrectlyAnswered = 0;
    private boolean mIsCheater = false;

    private int mRemainingCheatCount = 3;
    private TextView mCheatCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mAnsweredQuestions = (HashSet<Integer>)savedInstanceState.getSerializable(KEY_ANSWERED_QUESTIONS);
            mAnsweredQuestions  = mAnsweredQuestions == null ? new HashSet<Integer>() : mAnsweredQuestions;
            mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER);
            mRemainingCheatCount = savedInstanceState.getInt(KEY_REMAINING_CHEATS);
        }

        mCheatCountTextView = findViewById(R.id.cheat_count_text_view);
        mCheatCountTextView.setText(Integer.toString(mRemainingCheatCount));
        
        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAnswerTrue = sQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, isAnswerTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                setupAnswerButtons();
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                setupAnswerButtons();
            }
        });

        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % sQuestionBank.length;
                updateQuestion();
                setupAnswerButtons();
            }
        });

        updateQuestion();
        setupAnswerButtons();

        ImageButton prevButton = findViewById(R.id.prev_button);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = mCurrentIndex == 0 ? mCurrentIndex = sQuestionBank.length - 1 : (mCurrentIndex - 1) % sQuestionBank.length;
                updateQuestion();
                setupAnswerButtons();
            }
        });

        ImageButton nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % sQuestionBank.length;
                updateQuestion();
                setupAnswerButtons();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy.() called");
    }

    private void updateQuestion() {
        int question = sQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = sQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId;

        if (mIsCheater)
        {
            messageResId = R.string.judgement_toast;
        } else {

            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mCorrectlyAnswered++;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        mAnsweredQuestions.add(mCurrentIndex);

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        if (mAnsweredQuestions.size() == sQuestionBank.length)
        {
            Toast.makeText(this, "Correctly answered: " + mCorrectlyAnswered + " out of: " + sQuestionBank.length, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putSerializable(KEY_ANSWERED_QUESTIONS, mAnsweredQuestions);
        outState.putBoolean(KEY_IS_CHEATER, mIsCheater);
        outState.putInt(KEY_REMAINING_CHEATS, mRemainingCheatCount);
    }

    private void setupAnswerButtons()
    {
        boolean shouldDisable = mAnsweredQuestions.contains(mCurrentIndex);
        mTrueButton.setEnabled(!shouldDisable);
        mFalseButton.setEnabled(!shouldDisable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            boolean wasAnswerShown = CheatActivity.wasAnswerShown(data);
            mIsCheater = mIsCheater || wasAnswerShown;
            if (wasAnswerShown)
            {
                mRemainingCheatCount--;
                mCheatCountTextView.setText(Integer.toString(mRemainingCheatCount));
            }
            if (mRemainingCheatCount <= 0)
            {
                mCheatButton.setEnabled(false);
            }
        }
    }
}
