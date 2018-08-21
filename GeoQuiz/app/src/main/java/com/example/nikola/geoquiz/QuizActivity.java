package com.example.nikola.geoquiz;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

    private static final String KEY_ANSWERED_QUESTIONS = "AnsweredQuestions";
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private static Question[] sQuestionBank = new Question[] {
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
        }

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

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = mCurrentIndex == 0 ? mCurrentIndex = sQuestionBank.length - 1 : (mCurrentIndex - 1) % sQuestionBank.length;
                updateQuestion();
                setupAnswerButtons();
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
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

        if (userPressedTrue == answerIsTrue)
        {
            messageResId = R.string.correct_toast;
            mCorrectlyAnswered++;
        } else {
          messageResId = R.string.incorrect_toast;
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
    }

    private void setupAnswerButtons()
    {
        boolean shouldDisable = mAnsweredQuestions.contains(mCurrentIndex);
        mTrueButton.setEnabled(!shouldDisable);
        mFalseButton.setEnabled(!shouldDisable);
    }

}
