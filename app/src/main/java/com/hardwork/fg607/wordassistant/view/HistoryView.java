package com.hardwork.fg607.wordassistant.view;

import com.hardwork.fg607.wordassistant.model.HistoryWord;

import java.util.List;

/**
 * Created by fg607 on 16-1-3.
 */
public interface HistoryView extends MvpView {

     public void showHistory(List<HistoryWord> wordList);
}
