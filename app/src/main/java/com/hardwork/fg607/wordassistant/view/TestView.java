package com.hardwork.fg607.wordassistant.view;

import com.hardwork.fg607.wordassistant.model.WordInfoSugar;

import java.util.List;

/**
 * Created by fg607 on 16-1-14.
 */
public interface TestView extends ReciteBaseView {

    public String getSpell();
    public String getChoose();
    public void setSpell(String spell);
    public void setChoose(String choose);
    public String getSpeak();
    public void showParaphrase(String paraphrase);
    public void showSpell(WordInfoSugar wordInfo);
    public void showChoose(WordInfoSugar wordInfo,List<WordInfoSugar> otherOption);
    public void showSpeak(String speakUK,String speakUS);
}
