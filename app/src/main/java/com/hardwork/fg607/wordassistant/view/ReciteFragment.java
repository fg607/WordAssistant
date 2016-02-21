package com.hardwork.fg607.wordassistant.view;


import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;
import com.hardwork.fg607.wordassistant.presenter.RecitePresenter;
import com.hardwork.fg607.wordassistant.utils.DrawableUtils;
import com.hardwork.fg607.wordassistant.utils.ImageUtils;
import com.hardwork.fg607.wordassistant.utils.UIUtils;

import butterknife.Bind;

public class ReciteFragment extends BaseFragment implements ReciteView{

    @Bind(R.id.button_next) Button mButtonNext;
    @Bind(R.id.button_previous) Button mButtonPrev;
    @Bind(R.id.textview_page) TextView mTextViewPage;
    @Bind(R.id.textview_word) TextView mTextViewWord;
    @Bind(R.id.textview_level) TextView mTextViewLevel;
    @Bind(R.id.textview_speakUK) TextView mTextViewSpeakUK;
    @Bind(R.id.textview_speakUS) TextView mTextViewSpeakUS;
    @Bind(R.id.imageview_voiceUK) ImageView mImageViewUK;
    @Bind(R.id.imageview_voiceUS) ImageView mImageViewUS;

    @Bind(R.id.paraphrase) LinearLayout mLinearLayout;
    @Bind(R.id.container) FrameLayout mContainer;
    @Bind(R.id.layout_wordinfo) LinearLayout mLayoutWordInfo;

    private RecitePresenter mPresenter;
    private boolean mIsFirst = true;
    private FlowLayout mFlowLayout;
    private TextView mTextView;
    private int mTextPaddingV;
    private int mTextPaddingH;
    private int mTextRadius;
    private int mColor;

    public ReciteFragment() {
    }


    @Override
    protected View createFragmentView() {
        return mInflater.inflate(R.layout.fragment_recite, mContainer, false);
    }

    @Override
    protected void init() {

        mButtonPrev.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);

        initFlowLayout();

    }

    private void initFlowLayout() {

        mFlowLayout = new FlowLayout(mContext);

        mContainer.addView(mFlowLayout);

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

        if(mIsFirst){

            mPresenter.showFirst();

            mIsFirst = false;
        }
    }

    public void bindPlan(String planName){

        mPresenter = new RecitePresenter(planName);

        mPresenter.attachView(this);

    }
    @Override
    public void showWordInfo(WordInfoSugar wordInfo) {

        if(mLayoutWordInfo.getVisibility() == View.GONE){

            mLayoutWordInfo.setVisibility(View.VISIBLE);
            mImageViewUK.setVisibility(View.GONE);
            mImageViewUS.setVisibility(View.GONE);
        }

        showWord(wordInfo.getName());
        showLevel(wordInfo.getLevel());
        showSpeak(wordInfo);
        showParaphrase(wordInfo.getParaphrase());
        showChange(wordInfo.getChange());


    }


    public void showWord(String name) {

        mTextViewWord.setText(name);
    }

    public void showLevel(String level) {

        mTextViewLevel.setText(level);
    }

    public void showSpeak(WordInfoSugar sugar) {

        if(sugar.getSpeakUK() != null && !TextUtils.isEmpty(sugar.getSpeakUK())){

            mTextViewSpeakUK.setText(sugar.getSpeakUK());
            mTextViewSpeakUS.setText(sugar.getSpeakUS());

        }else {
            mTextViewSpeakUK.setText("");
            mTextViewSpeakUS.setText("");
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

    private TextView getTextView() {

        mColor = ImageUtils.getRandomColor();

        GradientDrawable colorDrawable = DrawableUtils.createDrawable(mColor, mColor, mTextRadius);

        mTextView = new TextView(mContext);
        mTextView.setTextSize(16);
        mTextView.setTextColor(getResources().getColor(R.color.white));
        mTextView.setPadding(mTextPaddingH, mTextPaddingV, mTextPaddingH, mTextPaddingV);
        mTextView.setBackgroundDrawable(colorDrawable);

        return mTextView;
    }

    @Override
    public void showNext() {

        mButtonNext.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideNext() {

        mButtonNext.setVisibility(View.GONE);
    }

    @Override
    public void showPrev() {

        mButtonPrev.setVisibility(View.VISIBLE);

    }

    @Override
    public void hidePrev() {

        mButtonPrev.setVisibility(View.GONE);

    }

    @Override
    public void showPage(int position,int size) {

        mTextViewPage.setText(position + "/" + size);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){

            case R.id.button_next:
                mPresenter.next();
                break;
            case R.id.button_previous:
                mPresenter.previous();
                break;
            default:
                break;

        }
    }
}
