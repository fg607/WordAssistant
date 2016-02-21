package com.hardwork.fg607.wordassistant.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by fg607 on 16-1-9.
 */
@Table
public class HistoryWord extends SugarRecord {

    String word;

    public HistoryWord(){};

    public HistoryWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
