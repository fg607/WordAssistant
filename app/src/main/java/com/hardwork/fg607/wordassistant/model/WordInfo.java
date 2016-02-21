package com.hardwork.fg607.wordassistant.model;

import java.util.HashMap;

/**
 * Created by fg607 on 16-1-3.
 */
public class WordInfo {

    String name;
    BaseSpeak baseSpeak;
    String level;
    HashMap<String,String> change = new HashMap<>();
    HashMap<String,String> paraphrase = new HashMap<>();


    public WordInfo(BaseSpeak baseSpeak, String level, HashMap<String, String> change, HashMap<String, String> paraphrase) {
        this.baseSpeak = baseSpeak;
        this.level = level;
        this.change = change;
        this.paraphrase = paraphrase;
    }

    public BaseSpeak getBaseSpeak() {
        return baseSpeak;
    }

    public void setBaseSpeak(BaseSpeak baseSpeak) {
        this.baseSpeak = baseSpeak;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public HashMap<String, String> getChange() {
        return change;
    }

    public void setChange(HashMap<String, String> change) {
        this.change = change;
    }

    public HashMap<String, String> getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(HashMap<String, String> paraphrase) {
        this.paraphrase = paraphrase;
    }

    @Override
    public String toString() {
        return "WordInfo{" +
                "baseSpeak=" + baseSpeak +
                ", level='" + level + '\'' +
                ", change=" + change +
                ", paraphrase=" + paraphrase +
                '}';
    }
}
