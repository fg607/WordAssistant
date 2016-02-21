package com.hardwork.fg607.wordassistant.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.Config;
import com.hardwork.fg607.wordassistant.model.RecitePlan;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;

import java.util.LinkedHashSet;
import java.util.List;

import butterknife.Bind;

public class NewPlanFragment extends BaseFragment{

    @Bind(R.id.plan_name) EditText mNameEditText;
    @Bind(R.id.checkbox_random) CheckBox mRandomCheckBox;
    @Bind(R.id.checkbox_manual) CheckBox mManualCheckBox;
    @Bind(R.id.cet4_checkbox) CheckBox mCET4CheckBox;
    @Bind(R.id.cet6_checkbox) CheckBox mCET6CheckBox;
    @Bind(R.id.ky_checkbox) CheckBox mKYCheckBox;
    @Bind(R.id.gre_checkbox) CheckBox mGRECheckBox;
    @Bind(R.id.toefl_checkbox) CheckBox mTOEFLCheckBox;
    @Bind(R.id.word_random_number) EditText mRandomNumber;
    @Bind(R.id.choose_number) TextView mChooseNumber;
    @Bind(R.id.word_number_layout) RelativeLayout mNumberLayout;
    @Bind(R.id.chooseword_layout) RelativeLayout mChooseLayout;

    private  LinkedHashSet<String> mWordNmberSet = new LinkedHashSet<>();
    private LinkedHashSet<WordInfoSugar> mWordSugarSet = new LinkedHashSet<>();

    private int mEndFlag = 0;
    private int mEndCount = 0;//count how many level　is choosed
    private boolean mIsStop = false;
    private String mRange;


    public NewPlanFragment() {

    }

    public void init() {

        mChooseLayout.setOnClickListener(this);
        initCheckBox();

    }

    @Override
    protected View createFragmentView() {
        return mInflater.inflate(R.layout.fragment_new_plan, mContainer, false);
    }

    private void initCheckBox() {

        mRandomCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    mManualCheckBox.setChecked(false);
                    mNumberLayout.setVisibility(View.VISIBLE);
                    mChooseLayout.setVisibility(View.GONE);

                } else {

                    mManualCheckBox.setChecked(true);

                    mNumberLayout.setVisibility(View.GONE);
                    mChooseLayout.setVisibility(View.VISIBLE);
                }

            }
        });

        mManualCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    mRandomCheckBox.setChecked(false);

                } else {

                    mRandomCheckBox.setChecked(true);

                }
            }
        });

        mCET4CheckBox.setTag(R.id.checkbox_name, "CET4");
        mCET4CheckBox.setTag(R.id.id_number, Long.parseLong("0"));

        mCET6CheckBox.setTag(R.id.checkbox_name, "CET6");
        mCET6CheckBox.setTag(R.id.id_number,Long.parseLong("0"));


        mKYCheckBox.setTag(R.id.checkbox_name, "考研");
        mKYCheckBox.setTag(R.id.id_number,Long.parseLong("0"));


        mGRECheckBox.setTag(R.id.checkbox_name, "GRE");
        mGRECheckBox.setTag(R.id.id_number,Long.parseLong("0"));


        mTOEFLCheckBox.setTag(R.id.checkbox_name,"TOEFL");
        mTOEFLCheckBox.setTag(R.id.id_number,Long.parseLong("0"));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.chooseword_layout:
                openChooseWordActivity();
                break;
            default:
                break;
        }



}

    public void saveNewPlan() {

        String name = mNameEditText.getText().toString().trim();

        if("".equals(name)){

           Snackbar.make(mFragmentView, "请添加计划名称", Snackbar.LENGTH_SHORT).show();
            return;

        }else {

            List<RecitePlan> list = RecitePlan.listAll(RecitePlan.class);

            for(RecitePlan plan:list){

                if(plan.getName().equals(name)){

                    Snackbar.make(mFragmentView, "计划已存在，不能重复添加", Snackbar.LENGTH_SHORT).show();
                    return;
                }

            }
        }


        int range = getRange();


        if(mRandomCheckBox.isChecked()){



            if(range == 0){

                Snackbar.make(mFragmentView, "先选择单词范围", Snackbar.LENGTH_SHORT).show();
                return;

            }

            String strNumber = mRandomNumber.getText().toString().toString().trim();

            if("".equals(strNumber)){

                strNumber = "10";

            }

            int wordNumber = Integer.parseInt(strNumber);

            if(wordNumber == 0){

                Snackbar.make(mFragmentView, "单词数量不能", Snackbar.LENGTH_SHORT).show();
                return;
            }

            saveRandomWord(name, wordNumber);

        }else {


            if(mWordSugarSet == null || mWordSugarSet.size() == 0){

                Snackbar.make(mFragmentView, "请选择要背记的单词", Snackbar.LENGTH_SHORT).show();
                return;
            }

            for(WordInfoSugar sugar:mWordSugarSet){

                String plan = sugar.getPlan();

                sugar.setPlan(plan + "/" + name);

                sugar.save();
            }

        }



        RecitePlan plan = new RecitePlan(name,mRange,String.valueOf(mWordNmberSet.size()));

        plan.save();

        Snackbar.make(mFragmentView, "存储完毕", Snackbar.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.putExtra("range",range);
        intent.putExtra("name",name);
        intent.putExtra("number",mWordNmberSet.size());

        getActivity().setResult(Config.NEW_PLAN, intent);

        getActivity().finish();
    }

    private void saveRandomWord(String name,int wordNumber) {


        mWordNmberSet.clear();
        Snackbar.make(mFragmentView, "开始储存", Snackbar.LENGTH_SHORT).show();

        while (mEndFlag < mEndCount){

            mEndFlag = 0;

            if(mCET4CheckBox.isChecked()){

                saveRangeWord(name,wordNumber,mCET4CheckBox);

                if(mIsStop){
                    break;
                }
            }

            if(mCET6CheckBox.isChecked()){

                saveRangeWord(name,wordNumber,mCET6CheckBox);

                if(mIsStop){
                    break;
                }

            }

            if(mKYCheckBox.isChecked()){

                saveRangeWord(name,wordNumber,mKYCheckBox);

                if(mIsStop){
                    break;
                }
            }

            if(mGRECheckBox.isChecked()){

                saveRangeWord(name,wordNumber,mGRECheckBox);

                if(mIsStop){
                    break;
                }
            }

            if(mTOEFLCheckBox.isChecked()){

                saveRangeWord(name,wordNumber,mTOEFLCheckBox);

                if(mIsStop){
                    break;
                }
            }

        }
    }

    public void saveRangeWord(String planName,int wordNumber,CheckBox checkBox){

        if(mWordNmberSet.size() == wordNumber){

            mIsStop = true;

            return;
        }

        if(checkBox.isChecked()){

            String name = (String) checkBox.getTag(R.id.checkbox_name).toString();

            String id = ((Long)checkBox.getTag(R.id.id_number)).toString();

            List<WordInfoSugar> list =  WordInfoSugar.findWithQuery(WordInfoSugar.class, "select * from WORD_INFO_SUGAR where LEVEL like '%" + name + "%' and ID > " + id +" order by ID limit 1");

            if(list.size() > 0){

                final WordInfoSugar sugar = list.get(0);

                checkBox.setTag(R.id.id_number,sugar.getId());

                int oldSize = mWordNmberSet.size();

                String str = sugar.getName();

                mWordNmberSet.add(sugar.getName());

                if(mWordNmberSet.size() > oldSize){

                    String plan = sugar.getPlan();
                    sugar.setPlan(plan + "/" + planName);
                    sugar.save();
                }

            }else {

                mEndFlag += 1;
            }

        }

    }

    private void openChooseWordActivity() {

        Intent intent = new Intent(getContext(),ChooseWordActivity.class);

       int i = getRange();

        if(i > 0){

            intent.putExtra("range",i);

            startActivityForResult(intent, 0);

        }else {

            Snackbar.make(mFragmentView, "先选择单词范围", Snackbar.LENGTH_SHORT).show();
        }


    }

    public int getRange(){

        int i = 0;

        mEndCount = 0;

        mRange = "";

        if(mCET4CheckBox.isChecked()){

            mEndCount++;

            mRange += "/" + "CET4";

            i = 1<<5;

        }

        if(mCET6CheckBox.isChecked()){

            mEndCount++;

            mRange += "/" + "CET6";

            i += 1<<4;
        }
        if(mKYCheckBox.isChecked()){

            mEndCount++;

            mRange += "/" + "考研";

            i += 1<<3;
        }
        if(mGRECheckBox.isChecked()){

            mEndCount++;

            mRange += "/" + "GRE";

            i += 1<<2;
        }
        if(mTOEFLCheckBox.isChecked()){

            mEndCount++;

            mRange += "/" + "TOEFL";

            i += 1<<1;
        }

        return i;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){

            Bundle bundle = data.getBundleExtra("bundle");


            if(bundle != null){

                mWordSugarSet = (LinkedHashSet<WordInfoSugar>) bundle.get("chooseword");

                if(mWordSugarSet != null){

                    mWordNmberSet.clear();

                    for(WordInfoSugar sugar:mWordSugarSet){

                        mWordNmberSet.add(sugar.getName());
                    }
                    mChooseNumber.setText("共选择了"+ mWordNmberSet.size() + "个单词");
                }


            }
        }
    }
}
