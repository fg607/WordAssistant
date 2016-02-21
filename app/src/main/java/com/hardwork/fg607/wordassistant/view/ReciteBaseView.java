package com.hardwork.fg607.wordassistant.view;

import com.hardwork.fg607.wordassistant.model.WordInfoSugar;

/**
 * Created by fg607 on 16-1-18.
 */
public interface ReciteBaseView extends MvpView {

    public void showWordInfo(WordInfoSugar wordInfo);
    public void showNext();
    public void hideNext();
    public void showPrev();
    public void hidePrev();
    public void showPage(int position,int size);
}
