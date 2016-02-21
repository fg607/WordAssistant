package com.hardwork.fg607.wordassistant.presenter;

import com.hardwork.fg607.wordassistant.model.Config;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;
import com.hardwork.fg607.wordassistant.view.TestView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fg607 on 16-1-14.
 */
public class TestPresenter extends ReciteBasePresenter {

    private ArrayList<String> mSpellList = new ArrayList<>();
    private ArrayList<String> mChooseList = new ArrayList<>();
    private List<WordInfoSugar> mOtherOptionList;
    private TestView mTestView;
    private int mReciteStyle;
    private String mSpell;
    private String mChoose;

    public TestPresenter(String planName,int reciteStyle) {
        super(planName);
        mReciteStyle = reciteStyle;
    }

    public void attachView(TestView view){

        super.attachView(view);

        mTestView = view;
    }


    @Override
    public void prevNext() {

       save();

    }

    private void saveChoose() {

        if(mChooseList.size() > mCurrentPos){

            mChooseList.set(mCurrentPos, mTestView.getChoose());

        }else {

            mChooseList.add(mTestView.getChoose());
        }
    }

    private void setChoose() {

        mChoose = mChooseList.size() > mCurrentPos ? mChooseList.get(mCurrentPos) : "";

        mTestView.setChoose(mChoose);
    }

    @Override
    public void prevPrevious() {

      save();
    }

    @Override
    public void doPrevious() {

      set();
    }


    @Override
    public void doNext() {

      set();
    }

    public void saveSpell() {
        if(mSpellList.size() > mCurrentPos){

            mSpellList.set(mCurrentPos, mTestView.getSpell());

        }else {

            mSpellList.add(mTestView.getSpell());
        }
    }

    public void setSpell() {

        mSpell = mSpellList.size() > mCurrentPos ? mSpellList.get(mCurrentPos) : "";

        mTestView.setSpell(mSpell);
    }


    @Override
    public void showWordInfo() {

        if (mReciteStyle == Config.STYLE_CHOOSE) {

            mOtherOptionList = WordInfoSugar.findWithQuery(WordInfoSugar.class, "select * from WORD_INFO_SUGAR where NAME != '" + mWordList.get(mCurrentPos).getName() + "' and SPEAK_UK !='' order by random() limit 3");

            mTestView.showChoose(mWordList.get(mCurrentPos), mOtherOptionList);

        } else if (mReciteStyle == Config.STYLE_SPELL) {

            super.showWordInfo();
        }
    }

    public List<WordInfoSugar> getWordList(){

        return mWordList;
    }

    public ArrayList<String> getSpellList(){

        return mSpellList;
    }

    public ArrayList<String> getChooseList(){

        return mChooseList;
    }

    public void save(){

        if(mReciteStyle == Config.STYLE_SPELL){

            saveSpell();

        }else {

            saveChoose();
        }
    }

    public void set(){

        if(mReciteStyle == Config.STYLE_SPELL){

            setSpell();

        }else {

            setChoose();
        }

    }
}
