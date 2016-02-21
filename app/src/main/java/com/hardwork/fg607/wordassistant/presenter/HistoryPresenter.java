package com.hardwork.fg607.wordassistant.presenter;

import com.hardwork.fg607.wordassistant.model.HistoryWord;
import com.hardwork.fg607.wordassistant.view.HistoryView;

import java.util.List;

/**
 * Created by fg607 on 16-1-3.
 */
public class HistoryPresenter implements Presenter<HistoryView> {

    private HistoryView mHistoryView;

    @Override
    public void attachView(HistoryView view) {

        mHistoryView = view;
    }

    public void obtainHistory(){

        long items = HistoryWord.count(HistoryWord.class);

        if(items > 0){

            //"select top 30 * from HISTORY_WORD order by id desc"
            List<HistoryWord> historyWordList =  HistoryWord.findWithQuery(HistoryWord.class,"select * from HISTORY_WORD order by ID desc limit 30");

            if(mHistoryView != null){

                mHistoryView.showHistory(historyWordList);
            }

        }



    }
}
