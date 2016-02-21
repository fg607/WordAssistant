package com.hardwork.fg607.wordassistant.view;

import com.hardwork.fg607.wordassistant.model.DayWords;
import com.hardwork.fg607.wordassistant.model.WordInfo;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;

/**
 * Created by fg607 on 16-1-3.
 */
public interface TranslateView extends MvpView {

    void showInfo(WordInfo info);
    void showInfoFromDB(WordInfoSugar sugar);
    void showLoading();
    void showError();
    void showDayWords(DayWords dayWords);
    void showNotFound();
}
