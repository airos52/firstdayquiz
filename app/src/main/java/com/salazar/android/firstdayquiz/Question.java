package com.salazar.android.firstdayquiz;

/** *
 * Created by Home on 3/13/18.
 */

public class Question {

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }

    private int mTextResId;
    private boolean mAnswer;
    private int mHintResId;

    public Question(int textResId, boolean answer, int hint){
        mTextResId = textResId;
        mAnswer = answer;
        mHintResId = hint;

    }
    public int getHintResId() {
        return mHintResId;
    }
    public void setHintResId(int hintResId) {
        mHintResId = hintResId;
    }

}
