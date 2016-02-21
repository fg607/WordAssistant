package com.hardwork.fg607.wordassistant.presenter;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.hardwork.fg607.wordassistant.model.Config;
import com.hardwork.fg607.wordassistant.model.DayWords;
import com.hardwork.fg607.wordassistant.model.HistoryWord;
import com.hardwork.fg607.wordassistant.model.WordInfo;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;
import com.hardwork.fg607.wordassistant.model.WordProvider;
import com.hardwork.fg607.wordassistant.utils.HttpUtils;
import com.hardwork.fg607.wordassistant.utils.ImageUtils;
import com.hardwork.fg607.wordassistant.utils.StringUtils;
import com.hardwork.fg607.wordassistant.utils.UIUtils;
import com.hardwork.fg607.wordassistant.view.TranslateView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by fg607 on 16-1-3.
 */

public class TranslatePresenter implements Presenter<TranslateView> {

    public static final String PIC_PATH = "/wordAssistant/daywords.png";
    private TranslateView mTranslateView;
    private WordProvider mWordProvider;
    private WordInfo mWordInfo;
    private DayWords mDaywords;
    private String mWord;
    private boolean mNetworkError = false;
    private boolean mIsGetDaywords;
    private WordInfoSugar mSugar;
    private Bitmap mDayPicBitmap;

    public TranslatePresenter(){

        mIsGetDaywords = checkUpdate();
    }

    @Override
    public void attachView(TranslateView view) {

        mTranslateView = view;
    }


    class LoadDaywordsThread extends Thread{

        private DayWords daywords;

        public LoadDaywordsThread(DayWords daywords){

            this.daywords = daywords;
        }

        @Override
        public void run() {

            DayWords oldDaywords = DayWords.findById(DayWords.class, 1);

            if(oldDaywords != null){

                oldDaywords.setPicUrl(daywords.getPicUrl());
                oldDaywords.setEnglish(daywords.getEnglish());
                oldDaywords.setChinese(daywords.getChinese());
                oldDaywords.save();

            }else {

                daywords.save();
            }


            URL url = null;

            try {

                url = new URL(daywords.getPicUrl());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3 * 1000);
                conn.setRequestMethod("GET");
                InputStream inStream = conn.getInputStream();

                if(inStream != null){

                    mDayPicBitmap = ImageUtils.InputStream2Bitmap(inStream);
                }

                if(mDayPicBitmap != null){

                    UIUtils.runInMainThread(new Runnable() {
                        @Override
                        public void run() {

                            mTranslateView.showDayWords(daywords);
                        }
                    });


                    ImageUtils.saveBitmap(mDayPicBitmap,"daywords.png");
                }

            } catch (Exception e) {

                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {

                        mTranslateView.showError();
                    }
                });
            }

        }
    }

    class TranslateAsyncTask extends AsyncTask<Void, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(mTranslateView != null){

                mTranslateView.showLoading();
            }

        }


        @Override
        protected Object doInBackground(Void... params) {

            if(!HttpUtils.isNetworkConnected()){

                mNetworkError = true;

                return null;

            }else {

                mNetworkError = false;
            }

            if(mWordProvider == null){

                mWordProvider = getProvider();

            }

            if(mIsGetDaywords){

               if(mWordProvider != null){

                   try {
                       mDaywords = mWordProvider.getDayWords();
                   }catch (Exception e){

                       mNetworkError = true;
                   }
               }

                return mDaywords;

            }else {

                if(mWordProvider != null && !TextUtils.isEmpty(mWord)){

                    try {
                        mWordInfo = mWordProvider.getWord(mWord);
                    }catch (Exception e){

                        mNetworkError = true;
                    }


                }

                return mWordInfo;
            }

        }

        @Override
        protected void onPostExecute(Object object) {
            super.onPostExecute(object);

            if(mNetworkError){

                mTranslateView.showError();
                return;

            }

            if(mIsGetDaywords){

                mDaywords = (DayWords) object;

                if(mDaywords != null){

                    loadDaywords(mDaywords);
                }


                mIsGetDaywords = false;


            }else {

                if(object != null) {



                  mWordInfo = (WordInfo) object;



                    if(mWordInfo.getParaphrase().size() > 0){

                        //save word to sqlite

                        saveWord(mWordInfo);

                        mTranslateView.showInfo(mWordInfo);

                    }else {
                        mTranslateView.showNotFound();
                    }

                }

            }


        }
    }

    private void saveHistoryWord(String name) {

        HistoryWord historyWord = new HistoryWord(name);

        historyWord.save();

        long items = HistoryWord.count(HistoryWord.class);

        if(items >= 50){

            HistoryWord.executeQuery("delete from HISTORY_WORD where ID in(select ID from HISTORY_WORD order by ID limit 20)");
        }
    }

    public boolean checkUpdate(){

        DayWords dayWords =   DayWords.findById(DayWords.class, 1);

        if(dayWords == null || !StringUtils.isFileExist(StringUtils.getSdcardDir()
                +PIC_PATH)){

            return true;
        }



        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
        Date date = new Date(System.currentTimeMillis());

        String today = formatter.format(date);

        boolean sameDate = StringUtils.hasSameDate(dayWords.getPicUrl(),today);

        return !sameDate;
    }

    public void loadDaywords(final DayWords daywords) {


        new LoadDaywordsThread(daywords).start();


    }

    public Bitmap getDayPicBitmap(){

        return mDayPicBitmap;
    }

    public WordInfoSugar queryDB(String name){

        List<WordInfoSugar> list = WordInfoSugar.findWithQuery(WordInfoSugar.class, "select * from WORD_INFO_SUGAR where NAME = ?", name);

        if(list.size() > 0){
            return list.get(0);
        }

        return null;
    }

    public static List<WordInfoSugar> querySuggestDB(String text){

        List<WordInfoSugar> list = WordInfoSugar.findWithQuery(WordInfoSugar.class, "select * from WORD_INFO_SUGAR where NAME like '" + text + "%'");

        return list;
    }

    public void saveWord(final WordInfo wordInfo) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                if(wordInfo.getBaseSpeak() != null){
                    mSugar = new WordInfoSugar(wordInfo.getName(),
                            wordInfo.getBaseSpeak().getSpeakUK(),
                            wordInfo.getBaseSpeak().getSpeakUS(),
                            wordInfo.getBaseSpeak().getMp3UKUrl(),
                            wordInfo.getBaseSpeak().getMp3USUrl(),
                            wordInfo.getLevel(),
                            StringUtils.getStrFromMap(wordInfo.getParaphrase()),
                            StringUtils.getStrFromMap(wordInfo.getChange()),
                            "");

                }else {
                    mSugar = new WordInfoSugar(wordInfo.getName(),
                           "",
                          "",
                            "",
                          "",
                            wordInfo.getLevel(),
                            StringUtils.getStrFromMap(wordInfo.getParaphrase()),
                            StringUtils.getStrFromMap(wordInfo.getChange()),
                            "");

                }

                mSugar.save();

                saveHistoryWord(wordInfo.getName());
            }
        }).start();
    }

    public void getWordInfo(String queryWord){

        mIsGetDaywords = false;

        queryWord = queryWord.trim();

        mSugar = queryDB(queryWord);

        if(mSugar != null){

            if(mTranslateView != null){

                mTranslateView.showInfoFromDB(mSugar);
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    saveHistoryWord(mSugar.getName());
                }
            }).start();

        }else {

            mWord = queryWord;

            new TranslateAsyncTask().execute();
        }

    }

    public void refresh(){

        new TranslateAsyncTask().execute();
    }

    public void getDayWords(){


        if(mIsGetDaywords){

            new TranslateAsyncTask().execute();

        }else {

            DayWords dayWords = DayWords.findById(DayWords.class,1);

            if(mTranslateView != null){

                mTranslateView.showLoading();

                mTranslateView.showDayWords(dayWords);
            }
        }

    }


    private WordProvider getProvider() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Config.baseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter.create(WordProvider.class);
    }

    public List<WordInfoSugar> getRemindWords(){

        return WordInfoSugar.findWithQuery(WordInfoSugar.class, "select * from WORD_INFO_SUGAR where SPEAK_UK !='' order by random() limit 3");
    }

}
