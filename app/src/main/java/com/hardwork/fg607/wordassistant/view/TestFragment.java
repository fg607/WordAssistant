package com.hardwork.fg607.wordassistant.view;


import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.Config;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;
import com.hardwork.fg607.wordassistant.presenter.TestPresenter;
import com.hardwork.fg607.wordassistant.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;

public class TestFragment extends BaseFragment implements TestView, OnCheckedChangeListener {

    @Bind(R.id.paraphrase)
    LinearLayout mLayoutParaphrase;
    @Bind(R.id.layout_choose)
    LinearLayout mLayoutChoose;
    @Bind(R.id.layout_spell)
    LinearLayout mLayoutSpell;
    @Bind(R.id.button_next)
    Button mButtonNext;
    @Bind(R.id.button_previous)
    Button mButtonPrev;
    @Bind(R.id.textview_speakUK)
    TextView mTextViewUK;
    @Bind(R.id.textview_speakUS)
    TextView mTextViewUS;
    @Bind(R.id.layout_speak)
    RelativeLayout mLayoutSpeak;
    @Bind(R.id.checkbox_remind)
    CheckBox mCheckBoxRemind;
    @Bind(R.id.textview_page)
    TextView mTextViewPage;
    @Bind(R.id.edit_spell)
    EditText mEditSpell;

    @Bind(R.id.textview_A)
    TextView mTextA;
    @Bind(R.id.textview_B)
    TextView mTextB;
    @Bind(R.id.textview_C)
    TextView mTextC;
    @Bind(R.id.textview_D)
    TextView mTextD;

    @Bind(R.id.checkbox_A)
    CheckBox mCheckBoxA;
    @Bind(R.id.checkbox_B)
    CheckBox mCheckBoxB;
    @Bind(R.id.checkbox_C)
    CheckBox mCheckBoxC;
    @Bind(R.id.checkbox_D)
    CheckBox mCheckBoxD;

    @Bind(R.id.layout_optionA)
    RelativeLayout mLayoutA;
    @Bind(R.id.layout_optionB)
    RelativeLayout mLayoutB;
    @Bind(R.id.layout_optionC)
    RelativeLayout mLayoutC;
    @Bind(R.id.layout_optionD)
    RelativeLayout mLayoutD;


    private String mPlanName;
    private int mReciteStyle = 0;
    private TestPresenter mPresenter;
    private TextView mTextView;
    private int mTextPaddingV;
    private int mTextPaddingH;

    private boolean mIsFirst = true;
    private String mChoosedOption = "";
    private String mChoosedWord = "";

    public TestFragment() {
    }

    @Override
    protected View createFragmentView() {
        return mInflater.inflate(R.layout.fragment_test, mContainer, false);
    }

    @Override
    protected void init() {

        mTextPaddingV = UIUtils.dip2px(8);
        mTextPaddingH = UIUtils.dip2px(7);

        mButtonNext.setOnClickListener(this);
        mButtonPrev.setOnClickListener(this);
        mCheckBoxRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    mLayoutSpeak.setVisibility(View.VISIBLE);

                } else {

                    mLayoutSpeak.setVisibility(View.GONE);
                }
            }
        });

        mCheckBoxA.setOnCheckedChangeListener(this);
        mCheckBoxB.setOnCheckedChangeListener(this);
        mCheckBoxC.setOnCheckedChangeListener(this);
        mCheckBoxD.setOnCheckedChangeListener(this);

        mLayoutA.setOnClickListener(this);
        mLayoutB.setOnClickListener(this);
        mLayoutC.setOnClickListener(this);
        mLayoutD.setOnClickListener(this);

    }

    public void bindPlan(String planName, int reciteStyle) {

        mReciteStyle = reciteStyle;
        mPresenter = new TestPresenter(planName, reciteStyle);
        mPresenter.attachView(this);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (mIsFirst) {

            mPresenter.showFirst();

            mIsFirst = false;
        }
    }

    @Override
    public void showWordInfo(WordInfoSugar wordInfo) {

        if (mLayoutSpell.getVisibility() == View.GONE) {

            mLayoutSpell.setVisibility(View.VISIBLE);

        }

        mCheckBoxRemind.setChecked(false);

        hideNext();
        hidePrev();

        showParaphrase(wordInfo.getParaphrase());

        showSpeak(wordInfo.getSpeakUK(), wordInfo.getSpeakUS());

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
    public void showPage(int position, int size) {

        mTextViewPage.setText(position + "/" + size);
    }

    @Override
    public String getSpell() {

        String spell = mEditSpell.getText().toString().trim();

        return spell;
    }

    @Override
    public String getChoose() {

        mChoosedWord = "";

        switch (mChoosedOption){
            case "A":
                mChoosedWord =  mTextA.getText().toString();
                break;
            case "B":
                mChoosedWord =  mTextB.getText().toString();
                break;
            case "C":
                mChoosedWord =  mTextC.getText().toString();
                break;
            case "D":
                mChoosedWord =  mTextD.getText().toString();
                break;
            default:
                break;
        }
        return mChoosedWord;
    }

    @Override
    public void setSpell(String spell) {

        mEditSpell.setText(spell);
    }

    @Override
    public void setChoose(String choose) {

        if(choose.equals(mTextA.getText().toString())){

            mCheckBoxA.setChecked(true);

        }else if(choose.equals(mTextB.getText().toString())){

            mCheckBoxB.setChecked(true);

        }else if(choose.equals(mTextC.getText().toString())){

            mCheckBoxC.setChecked(true);

        }else if(choose.equals(mTextD.getText().toString())){

            mCheckBoxD.setChecked(true);
        }
    }

    @Override
    public String getSpeak() {
        return null;
    }


    @Override
    public void showParaphrase(String paraphrase) {

        mLayoutParaphrase.removeAllViews();

        mTextView = new TextView(mContext);
        mTextView.setTextSize(16);
        mTextView.setLineSpacing(mTextPaddingV * 2, 1);
        mTextView.setPadding(mTextPaddingH, mTextPaddingV, mTextPaddingH, mTextPaddingV);
        mTextView.setGravity(Gravity.LEFT);

        mTextView.setText(paraphrase);

        mLayoutParaphrase.addView(mTextView);

    }

    @Override
    public void showSpell(WordInfoSugar wordInfo) {

        if (mLayoutSpell.getVisibility() == View.GONE) {

            mLayoutSpell.setVisibility(View.VISIBLE);

        }

        mCheckBoxRemind.setChecked(false);

        hideNext();
        hidePrev();

        showParaphrase(wordInfo.getParaphrase());

        showSpeak(wordInfo.getSpeakUK(), wordInfo.getSpeakUS());

    }

    @Override
    public void showChoose(WordInfoSugar wordInfo, List<WordInfoSugar> otherOption) {

        HashSet<String> hashSet = new HashSet<>();

        hashSet.add(wordInfo.getName());

        for (WordInfoSugar sugar : otherOption) {

            hashSet.add(sugar.getName());
        }

        String[] choice = new String[4];

        hashSet.toArray(choice);

        if (mLayoutChoose.getVisibility() == View.GONE) {

            mLayoutChoose.setVisibility(View.VISIBLE);
        }

        clearState();

        showParaphrase(wordInfo.getParaphrase());

        mTextA.setText(choice[0]);
        mTextB.setText(choice[1]);
        mTextC.setText(choice[2]);
        mTextD.setText(choice[3]);


    }

    public void clearState() {

        mChoosedOption = "";
        hideNext();
        hidePrev();
        mCheckBoxA.setChecked(false);
        mCheckBoxB.setChecked(false);
        mCheckBoxC.setChecked(false);
        mCheckBoxD.setChecked(false);
    }

    @Override
    public void showSpeak(String speakUK, String speakUS) {

        mTextViewUK.setText(speakUK);
        mTextViewUS.setText(speakUS);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.button_next:
                mPresenter.next();
                break;
            case R.id.button_previous:
                mPresenter.previous();
                break;
            case R.id.layout_optionA:
                mCheckBoxA.setChecked(true);
                break;
            case R.id.layout_optionB:
                mCheckBoxB.setChecked(true);
                break;
            case R.id.layout_optionC:
                mCheckBoxC.setChecked(true);
                break;
            case R.id.layout_optionD:
                mCheckBoxD.setChecked(true);
                break;
            default:
                break;

        }
    }

    public void commit() {

        mPresenter.save();
        Intent intent = new Intent(mContext, TestScoreActivity.class);

        intent.putExtra("answerList", new ArrayList<WordInfoSugar>(mPresenter.getWordList()));

        if (mReciteStyle == Config.STYLE_SPELL) {

            intent.putExtra("commitList", mPresenter.getSpellList());

        } else if (mReciteStyle == Config.STYLE_CHOOSE) {

            intent.putExtra("commitList", mPresenter.getChooseList());
        }

        getActivity().startActivity(intent);
        getActivity().finish();


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


        switch (buttonView.getId()) {

            case R.id.checkbox_A:
                if (isChecked) {
                    mChoosedOption = "A";
                    mCheckBoxB.setChecked(false);
                    mCheckBoxC.setChecked(false);
                    mCheckBoxD.setChecked(false);
                }

                break;
            case R.id.checkbox_B:
                if (isChecked) {
                    mChoosedOption = "B";
                    mCheckBoxA.setChecked(false);
                    mCheckBoxC.setChecked(false);
                    mCheckBoxD.setChecked(false);
                }
                break;
            case R.id.checkbox_C:
                if (isChecked) {
                    mChoosedOption = "C";
                    mCheckBoxB.setChecked(false);
                    mCheckBoxA.setChecked(false);
                    mCheckBoxD.setChecked(false);
                }
                break;
            case R.id.checkbox_D:
                if (isChecked) {
                    mChoosedOption = "D";
                    mCheckBoxB.setChecked(false);
                    mCheckBoxC.setChecked(false);
                    mCheckBoxA.setChecked(false);
                }
                break;
            default:
                break;
        }
    }
}
