package com.hardwork.fg607.wordassistant.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.BaseSpeak;
import com.hardwork.fg607.wordassistant.model.DayWords;
import com.hardwork.fg607.wordassistant.model.WordInfo;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;
import com.hardwork.fg607.wordassistant.presenter.TranslatePresenter;
import com.hardwork.fg607.wordassistant.utils.DrawableUtils;
import com.hardwork.fg607.wordassistant.utils.HttpUtils;
import com.hardwork.fg607.wordassistant.utils.ImageUtils;
import com.hardwork.fg607.wordassistant.utils.StringUtils;
import com.hardwork.fg607.wordassistant.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TranslateFragment extends BaseFragment implements TranslateView ,
        OnPreparedListener,OnCompletionListener{

    private static final String TAG = "TranslateFragment";

    @Bind(R.id.progressbar) ProgressBar mProgressBar;
    @Bind(R.id.textview_error) TextView mTextViewError;
    @Bind(R.id.button_retry) Button mButtonRetry;
    @Bind(R.id.daywords) RelativeLayout mDaywordsLayout;
    @Bind(R.id.day_pic) ImageView mDayPic;
    @Bind(R.id.day_english) TextView mDayEnglish;
    @Bind(R.id.day_chinese) TextView mDayChinese;
    @Bind(R.id.layout_wordinfo) LinearLayout mLayoutWordInfo;
    @Bind(R.id.textview_word) TextView mTextViewWord;
    @Bind(R.id.textview_level) TextView mTextViewLevel;
    @Bind(R.id.textview_speakUK) TextView mTextViewSpeakUK;
    @Bind(R.id.textview_speakUS) TextView mTextViewSpeakUS;
    @Bind(R.id.paraphrase) LinearLayout mLinearLayout;
    @Bind(R.id.container) FrameLayout mContainer;
    @Bind(R.id.imageview_voiceUK) ImageView mImageViewUK;
    @Bind(R.id.imageview_voiceUS) ImageView mImageViewUS;

    @Bind(R.id.cardview)
    CardView mCardView;

    @Bind(R.id.layout_remind) LinearLayout mLayoutRemind;
    @Bind(R.id.layout_remind_word) FrameLayout mLayoutRemindWord;

    private TranslatePresenter mPresenter;
    private String mWord;
    private boolean mIsFirstStart = true;
    private FlowLayout mFlowLayout;

    private TextView mTextView;
    private int mTextPaddingV;
    private int mTextPaddingH;
    private Random mRandom;
    private int mTextRadius;

    private MediaPlayer mMediaPlayer;
    private String mVoiceUK;
    private String mVoiceUS;
    private boolean mIsVoiceUKPlay;
    private boolean mIsVoiceUSPlay;
    private AnimationDrawable mUKAnimation;
    private AnimationDrawable mUSAnimation;
    private boolean mIsPrepare = false;
    private Runnable mTimeoutRunnable;


    private int mRed,mGreen,mBlue,mColor;

    public TranslateFragment() {

    }

    @Override
    protected View createFragmentView() {

        return mInflater.inflate(R.layout.fragment_translate, mContainer, false);
    }


    @Override
    protected void init() {

        ButterKnife.bind(this, mFragmentView);

        mImageViewUK.setOnClickListener(this);
        mImageViewUS.setOnClickListener(this);
        mButtonRetry.setOnClickListener(this);

        mPresenter = new TranslatePresenter();
        mPresenter.attachView(this);

        initRunnable();

        getMediaPlayer();

        initFlowLayout();

        initRemind();
    }

    private void initRemind() {


        FlowLayout layout = new FlowLayout(mContext);

        mLayoutRemindWord.addView(layout);

        List<WordInfoSugar> remindList = mPresenter.getRemindWords();

       // TextView textView;

        for(final WordInfoSugar wordInfo:remindList){

            TextView textView = getTextView();



            textView.setText(wordInfo.getName());
            textView.setClickable(true);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    query(wordInfo.getName());
                }
            });

            layout.addView(textView);
        }
    }

    private void initRunnable() {

        mTimeoutRunnable = new Runnable() {
            @Override
            public void run() {

                UIUtils.showToastSafe("网络超时");

                if(mMediaPlayer != null){
                    mMediaPlayer = null;
                }
                mIsPrepare = false;
                mIsVoiceUKPlay = false;
                mIsVoiceUSPlay = false;
            }
        };
    }

    private void initFlowLayout() {

        mFlowLayout = new FlowLayout(mContext);

        mContainer.addView(mFlowLayout);

        mRandom = new Random();

        mTextPaddingV = UIUtils.dip2px(8);
        mTextPaddingH = UIUtils.dip2px(7);
        mTextRadius = UIUtils.dip2px(5);

        int layoutPadding = UIUtils.dip2px(13);

        mFlowLayout.setHorizontalSpacing(layoutPadding);
        mFlowLayout.setVerticalSpacing(layoutPadding);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(mIsFirstStart){
            mPresenter.getDayWords();
            mIsFirstStart = false;
        }

    }

    public void query(String queryWord){

        mWord = queryWord;

        mPresenter.getWordInfo(queryWord);
    }



    @Override
    public void showInfo(WordInfo wordInfo) {

        mProgressBar.setVisibility(View.GONE);
        mTextViewError.setText("");
        mButtonRetry.setVisibility(View.GONE);
        mDaywordsLayout.setVisibility(View.GONE);
        mLayoutRemind.setVisibility(View.GONE);
        mLayoutWordInfo.setVisibility(View.VISIBLE);

        showWord(wordInfo.getName());
        showLevel(wordInfo.getLevel());
        showSpeak(wordInfo.getBaseSpeak());
        showParaphrase(wordInfo.getParaphrase());
        showChange(wordInfo.getChange());

    }

    @Override
    public void showInfoFromDB(WordInfoSugar sugar) {

        mProgressBar.setVisibility(View.GONE);
        mTextViewError.setText("");
        mButtonRetry.setVisibility(View.GONE);
        mDaywordsLayout.setVisibility(View.GONE);
        mLayoutRemind.setVisibility(View.GONE);
        mLayoutWordInfo.setVisibility(View.VISIBLE);

        showWord(sugar.getName());
        showLevel(sugar.getLevel());
        showSpeak(sugar);
        showParaphrase(sugar.getParaphrase());
        showChange(sugar.getChange());
    }



    public void showWord(String name) {

        mTextViewWord.setText(name);
    }

    public void showLevel(String level) {

        mTextViewLevel.setText(level);
    }

    @Override
    public void showLoading() {

        mTextViewError.setText("");
        mButtonRetry.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {

        if(mProgressBar.getVisibility() != View.VISIBLE){

            return;
        }
        mProgressBar.setVisibility(View.GONE);
        mDaywordsLayout.setVisibility(View.GONE);
        mLayoutWordInfo.setVisibility(View.INVISIBLE);
        mTextViewError.setText("大人,网络错误,请稍后重试!");
        mButtonRetry.setVisibility(View.VISIBLE);
    }

    public void showDaywordsPic() {

        mDayPic.setVisibility(View.VISIBLE);
    }

    public void hideDaywordsPic() {

        mDayPic.setVisibility(View.GONE);
    }

    @Override
    public void showDayWords(DayWords dayWords) {

        if(mProgressBar.getVisibility() != View.VISIBLE){
            return;
        }

        mProgressBar.setVisibility(View.GONE);
        mTextViewError.setText("");
        mDaywordsLayout.setVisibility(View.VISIBLE);

        if(dayWords != null){

            if(mPresenter.getDayPicBitmap() != null){

                mDayPic.setImageBitmap(mPresenter.getDayPicBitmap());

            }else {

                String path = StringUtils.getSdcardDir() + mPresenter.PIC_PATH;
                Bitmap bitmap = ImageUtils.getBitmap(path);

                if(bitmap != null){

                    mDayPic.setImageBitmap(bitmap);

                }else {

                    Uri uri = Uri.parse(dayWords.getPicUrl());
                    Picasso.with(mContext).load(uri)
                    .into(mDayPic);
                }

            }

            mDayEnglish.setText("    " + dayWords.getEnglish());
            mDayChinese.setText("    " + dayWords.getChinese());
        }


    }

    @Override
    public void showNotFound() {

        mProgressBar.setVisibility(View.GONE);
        mDaywordsLayout.setVisibility(View.GONE);
        mLayoutWordInfo.setVisibility(View.GONE);
        mTextViewError.setText("对不起，没有要查找的内容！");
    }

    public void showSpeak(BaseSpeak speak) {

        if(speak != null){

            mTextViewSpeakUK.setText(speak.getSpeakUK());
            mTextViewSpeakUS.setText(speak.getSpeakUS());

            mVoiceUK = speak.getMp3UKUrl();
            mVoiceUS = speak.getMp3USUrl();

            mImageViewUK.setVisibility(View.VISIBLE);
            mImageViewUS.setVisibility(View.VISIBLE);

        }else {
            mTextViewSpeakUK.setText("");
            mTextViewSpeakUS.setText("");
            mImageViewUK.setVisibility(View.GONE);
            mImageViewUS.setVisibility(View.GONE);
        }


    }

    public void showSpeak(WordInfoSugar sugar) {

        if(sugar.getSpeakUK() != null && !TextUtils.isEmpty(sugar.getSpeakUK())){

            mTextViewSpeakUK.setText(sugar.getSpeakUK());
            mTextViewSpeakUS.setText(sugar.getSpeakUS());

            mVoiceUK = sugar.getMp3UKUrl();
            mVoiceUS = sugar.getMp3USUrl();

            mImageViewUK.setVisibility(View.VISIBLE);
            mImageViewUS.setVisibility(View.VISIBLE);


        }else {
            mTextViewSpeakUK.setText("");
            mTextViewSpeakUS.setText("");
            mImageViewUK.setVisibility(View.GONE);
            mImageViewUS.setVisibility(View.GONE);
        }

    }

    public void getMediaPlayer(){

        if(mMediaPlayer == null){

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
        }

    }


    public void playVoiceUS() {

        if(mIsPrepare || mIsVoiceUKPlay){
            return;
        }

        if(!HttpUtils.isNetworkConnected()){

            showMsg("没有网络哦，亲！");
            return;
        }


        mIsVoiceUSPlay = true;

        if(mMediaPlayer == null){

            getMediaPlayer();
        }

        try {

            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mVoiceUS);
            mHandler.postDelayed(mTimeoutRunnable,2000);
            mIsPrepare = true;
            mMediaPlayer.prepareAsync();

        } catch (Exception e) {

            showMsg("出错鸟！");
        }

    }

    public void playVoiceUK() {

        if(mIsPrepare || mIsVoiceUSPlay){
            return;
        }

        if(!HttpUtils.isNetworkConnected()){

            showMsg("没有网络哦，亲！");
            return;
        }

        mIsVoiceUKPlay = true;

        if(mMediaPlayer == null){

            getMediaPlayer();
        }

        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mVoiceUK);
            mHandler.postDelayed(mTimeoutRunnable,2000);
            mIsPrepare = true;
            mMediaPlayer.prepareAsync();

        } catch (Exception e) {
            showMsg("出错鸟！");
        }


    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        if(mIsVoiceUKPlay && mUKAnimation != null){
            mUKAnimation.stop();
            mImageViewUK.setBackgroundDrawable(getResources().getDrawable(R.drawable.supreme_volume));
            mIsVoiceUKPlay = false;

        }

        if(mIsVoiceUSPlay && mUSAnimation != null) {
            mUSAnimation.stop();
            mImageViewUS.setBackgroundDrawable(getResources().getDrawable(R.drawable.supreme_volume));
            mIsVoiceUSPlay = false;
        }
    }


    @Override
    public void onPrepared(MediaPlayer mp) {

        if(mp != null) {
            mHandler.removeCallbacks(mTimeoutRunnable);
            mIsPrepare = false;

            if (mIsVoiceUKPlay) {

                mImageViewUK.setBackgroundResource(R.drawable.voice_animation);
                mUKAnimation = (AnimationDrawable) mImageViewUK.getBackground();
                mUKAnimation.start();
            }

            if (mIsVoiceUSPlay) {
                mImageViewUS.setBackgroundResource(R.drawable.voice_animation);
                mUSAnimation = (AnimationDrawable) mImageViewUS.getBackground();
                mUSAnimation.start();
            }

            mp.start();
        }
    }


    public void showParaphrase(HashMap<String, String> paraphrase) {

        mLinearLayout.removeAllViews();

        for(String key:paraphrase.keySet()){

            mTextView = new TextView(mContext);
            mTextView.setTextSize(16);
            mTextView.setPadding(mTextPaddingH, mTextPaddingV, mTextPaddingH, mTextPaddingV);
            mTextView.setGravity(Gravity.LEFT);

            mTextView.setText(key + "   " + paraphrase.get(key).replaceAll(" ", "").replace("\r\n", ""));

            mLinearLayout.addView(mTextView);
        }

    }


    public void showParaphrase(String paraphrase) {

        mLinearLayout.removeAllViews();

        mTextView = new TextView(mContext);
        mTextView.setTextSize(16);
        mTextView.setLineSpacing(mTextPaddingV*2,1);
        mTextView.setPadding(mTextPaddingH, mTextPaddingV, mTextPaddingH, mTextPaddingV);
        mTextView.setGravity(Gravity.LEFT);

        mTextView.setText(paraphrase);

        mLinearLayout.addView(mTextView);

    }


    public void showChange(HashMap<String, String> change) {

        mFlowLayout.removeAllViews();

        for(String key:change.keySet()){

            TextView textView = getTextView();

            textView.setText(key + "   " +
                    change.get(key).replaceAll(" ", "").replace("\r\n", ""));

            mFlowLayout.addView(textView);

        }

    }

    public void showChange(String change) {

        mFlowLayout.removeAllViews();

        String[] changes = change.split("\r\n");

        for(String cha:changes){


            if(!TextUtils.isEmpty(cha)) {

                TextView textView = getTextView();

                textView.setText(cha);

                mFlowLayout.addView(textView);
            }

        }
    }

    @NonNull
    private TextView getTextView() {

        mColor = getRandomColor();

        GradientDrawable colorDrawable = DrawableUtils.createDrawable(mColor, mColor, mTextRadius);

        mTextView = new TextView(mContext);
        mTextView.setTextSize(16);
        mTextView.setTextColor(getResources().getColor(R.color.white));
        mTextView.setPadding(mTextPaddingH, mTextPaddingV, mTextPaddingH, mTextPaddingV);
        mTextView.setBackgroundDrawable(colorDrawable);

        return mTextView;
    }

    private int getRandomColor() {

        // 随机颜色的范围0x202020~0xefefef
        mRed = 32 + mRandom.nextInt(208);
        mGreen = 32 + mRandom.nextInt(208);
        mBlue = 32 + mRandom.nextInt(208);
        return Color.rgb(mRed, mGreen, mBlue);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imageview_voiceUK:
                playVoiceUK();
                break;
            case R.id.imageview_voiceUS:
                playVoiceUS();
                break;
            case R.id.button_retry:
                mButtonRetry.setVisibility(View.GONE);
                refresh();
                break;
            default:
                break;
        }
    }

    private void refresh() {

        mPresenter.refresh();

    }
}
