package com.hardwork.fg607.wordassistant.presenter;

import com.hardwork.fg607.wordassistant.view.ReciteView;

/**
 * Created by fg607 on 16-1-18.
 */
public class RecitePresenter extends ReciteBasePresenter {

    private ReciteView mReciteView;

    public RecitePresenter(String planName) {
        super(planName);
    }


    public void attachView(ReciteView view) {
        super.attachView(view);

        mReciteView = view;
    }


    @Override
    public void next() {
        super.next();
    }

    @Override
    public void previous() {
        super.previous();
    }

    @Override
    public void showWordInfo() {
        super.showWordInfo();
    }

    @Override
    public void doPrevious() {
        super.doPrevious();
    }

    @Override
    public void doNext() {
        super.doNext();
    }
}
