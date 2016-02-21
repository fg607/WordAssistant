package com.hardwork.fg607.wordassistant.presenter;

import com.hardwork.fg607.wordassistant.model.WordInfoSugar;
import com.hardwork.fg607.wordassistant.view.ReciteBaseView;

import java.util.List;

/**
 * Created by fg607 on 16-1-18.
 */
public class ReciteBasePresenter implements Presenter<ReciteBaseView> {

    protected List<WordInfoSugar> mWordList;
    protected ReciteBaseView mView;
    protected String mPlanName;
    protected int mCurrentPos = 0;
    protected int mSize;


    public ReciteBasePresenter(String planName){

        this.mPlanName = planName;

        obtainPlanWords();
    }

    @Override
    public void attachView(ReciteBaseView view) {

        mView = view;
    }

    public void obtainPlanWords() {

        mWordList = WordInfoSugar.findWithQuery(WordInfoSugar.class,
                "select * from WORD_INFO_SUGAR where PLAN like '%" + mPlanName + "%'");

        mSize = mWordList.size();
    }

    public  void showFirst(){

        showWordInfo();

        mView.hidePrev();

        mView.showPage(mCurrentPos + 1, mSize);

        if (mSize > 1) {

            mView.showNext();
        }
    }

    public void next() {

        prevNext();

        mCurrentPos++;

        if (mCurrentPos < mSize) {

            showWordInfo();

            doNext();

            mView.showPage(mCurrentPos + 1, mSize);

            mView.showPrev();

            if (mCurrentPos + 1 < mSize) {

                mView.showNext();
            }

        }
    }



    public void previous(){

        prevPrevious();

        mCurrentPos--;

        if (mCurrentPos >= 0) {

            showWordInfo();

            doPrevious();

            mView.showPage(mCurrentPos + 1, mSize);

            mView.showNext();

            if (mCurrentPos > 0) {

                mView.showPrev();
            }
        }
    }


    public void showWordInfo() {

        mView.hideNext();
        mView.hidePrev();
        mView.showWordInfo(mWordList.get(mCurrentPos));

    }


    public void prevPrevious() {

    }

    public void prevNext() {


    }

    public void doPrevious() {


    }

    public void doNext() {

    }

}
