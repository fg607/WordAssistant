package com.hardwork.fg607.wordassistant.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

/**
 * Created by fg607 on 16-1-19.
 */
@Table
public class TestStatistics extends SugarRecord {

    @Unique
    String level;
    int total;
    int correct;

    public TestStatistics(){


    }

    public TestStatistics(String level, int total, int correct) {
        this.level = level;
        this.total = total;
        this.correct = correct;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }
}
