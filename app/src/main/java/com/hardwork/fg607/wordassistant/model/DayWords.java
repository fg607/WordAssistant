package com.hardwork.fg607.wordassistant.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by fg607 on 16-1-6.
 */
@Table
public class DayWords extends SugarRecord{

    String picUrl;
    String english;
    String chinese;

    public DayWords(){}

    public DayWords(String picUrl, String english, String chinese) {
        this.picUrl = picUrl;
        this.english = english;
        this.chinese = chinese;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return "DayWords{" +
                "picUrl='" + picUrl + '\'' +
                ", english='" + english + '\'' +
                ", chinese='" + chinese + '\'' +
                '}';
    }
}
