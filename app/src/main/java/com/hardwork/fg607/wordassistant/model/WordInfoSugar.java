package com.hardwork.fg607.wordassistant.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.io.Serializable;

/**
 * Created by fg607 on 16-1-6.
 */
@Table
public class WordInfoSugar extends SugarRecord implements Serializable{

    @Unique
    String name;
    String speakUK;
    String speakUS;
    String mp3UKUrl;
    String mp3USUrl;
    String level;
    String paraphrase;
    String change;
    String plan;

    public WordInfoSugar(){

    }

    public WordInfoSugar(String name, String speakUK, String speakUS, String mp3UKUrl,
                         String mp3USUrl, String level, String paraphrase,
                         String change, String plan) {
        this.name = name;
        this.speakUK = speakUK;
        this.speakUS = speakUS;
        this.mp3UKUrl = mp3UKUrl;
        this.mp3USUrl = mp3USUrl;
        this.level = level;
        this.paraphrase = paraphrase;
        this.change = change;
        this.plan = plan;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeakUK() {
        return speakUK;
    }

    public void setSpeakUK(String speakUK) {
        this.speakUK = speakUK;
    }

    public String getSpeakUS() {
        return speakUS;
    }

    public void setSpeakUS(String speakUS) {
        this.speakUS = speakUS;
    }

    public String getMp3UKUrl() {
        return mp3UKUrl;
    }

    public void setMp3UKUrl(String mp3UKUrl) {
        this.mp3UKUrl = mp3UKUrl;
    }

    public String getMp3USUrl() {
        return mp3USUrl;
    }

    public void setMp3USUrl(String mp3USUrl) {
        this.mp3USUrl = mp3USUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
