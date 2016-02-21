package com.hardwork.fg607.wordassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.adapter.ChooseWordAdapter;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;

import java.util.LinkedHashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChooseWordActivity extends AppCompatActivity implements OnClickListener ,OnItemClickListener{


    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.fab) FloatingActionButton mFab;

    @Bind(R.id.cet4_title) RelativeLayout mCET4TitleLayout;
    @Bind(R.id.cet6_title) RelativeLayout mCET6TitleLayout;
    @Bind(R.id.ky_title) RelativeLayout mKYTitleLayout;
    @Bind(R.id.gre_title) RelativeLayout mGRETitleLayout;
    @Bind(R.id.toefl_title) RelativeLayout mTOEFLTitleLayout;
    @Bind(R.id.cet4_img) ImageView mCET4Image;
    @Bind(R.id.cet6_img) ImageView mCET6Image;
    @Bind(R.id.ky_img) ImageView mKYImage;
    @Bind(R.id.gre_img) ImageView mGREImage;
    @Bind(R.id.toefl_img) ImageView mTOEFLImage;
    @Bind(R.id.cet4_list) ListView mCET4ListView;
    @Bind(R.id.cet6_list) ListView mCET6ListView;
    @Bind(R.id.ky_list) ListView mKYListView;
    @Bind(R.id.gre_list) ListView mGREListView;
    @Bind(R.id.toefl_list) ListView mTOEFLListView;

    private RotateAnimation mRotateAnimation;

    private LinkedHashSet<WordInfoSugar> mChoosedSet = new LinkedHashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_word);

        initView();

        initAnimation();

        initIntent();

    }

    private void initAnimation() {

        mRotateAnimation = new RotateAnimation(0,180, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        mRotateAnimation.setDuration(500);
        mRotateAnimation.setFillAfter(true);
    }

    private void initView() {

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mFab.setOnClickListener(this);

        mCET4TitleLayout.setOnClickListener(this);

        mCET6TitleLayout.setOnClickListener(this);

        mKYTitleLayout.setOnClickListener(this);

        mGRETitleLayout.setOnClickListener(this);

        mTOEFLTitleLayout.setOnClickListener(this);

        mCET4ListView.setOnItemClickListener(this);
        mCET6ListView.setOnItemClickListener(this);
        mKYListView.setOnItemClickListener(this);
        mGREListView.setOnItemClickListener(this);
        mTOEFLListView.setOnItemClickListener(this);


    }

    private void initIntent() {

        Intent intent = getIntent();

        if(intent != null){

            int range = intent.getIntExtra("range", 0);

            String str = "";

            if((range & 32) == 32){

                str = "CET4";

                List<WordInfoSugar> list =  WordInfoSugar.findWithQuery(WordInfoSugar.class, "select * from WORD_INFO_SUGAR where LEVEL like '%"+str+"%'");

                ChooseWordAdapter cet4Adapter = new ChooseWordAdapter(this,list);

                mCET4ListView.setAdapter(cet4Adapter);

                mCET4TitleLayout.setVisibility(View.VISIBLE);

            }

            if((range & 16) == 16){

                str = "CET6";

                List<WordInfoSugar> list =  WordInfoSugar.findWithQuery(WordInfoSugar.class, "select * from WORD_INFO_SUGAR where LEVEL like '%"+str+"%'");

                ChooseWordAdapter cet6Adapter = new ChooseWordAdapter(this,list);

                mCET6ListView.setAdapter(cet6Adapter);

                mCET6TitleLayout.setVisibility(View.VISIBLE);
            }
            if((range & 8) == 8){

                str = "考研";

                List<WordInfoSugar> list =  WordInfoSugar.findWithQuery(WordInfoSugar.class, "select * from WORD_INFO_SUGAR where LEVEL like '%"+str+"%'");

                ChooseWordAdapter kyAdapter = new ChooseWordAdapter(this,list);

                mKYListView.setAdapter(kyAdapter);

                mKYTitleLayout.setVisibility(View.VISIBLE);
            }
            if((range & 4) == 4){

                str = "GRE";

                List<WordInfoSugar> list =  WordInfoSugar.findWithQuery(WordInfoSugar.class, "select * from WORD_INFO_SUGAR where LEVEL like '%"+str+"%'");

                ChooseWordAdapter greAdapter = new ChooseWordAdapter(this,list);

                mGREListView.setAdapter(greAdapter);

                mGRETitleLayout.setVisibility(View.VISIBLE);
            }
            if((range & 2) == 2){

                str = "TOEFL";

                List<WordInfoSugar> list =  WordInfoSugar.findWithQuery(WordInfoSugar.class, "select * from WORD_INFO_SUGAR where LEVEL like '%"+str+"%'");

               ChooseWordAdapter toeflAdapter = new ChooseWordAdapter(this,list);

                mTOEFLListView.setAdapter(toeflAdapter);

                mTOEFLTitleLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cet4_title:

                toggleList(mCET4ListView);

                mCET4Image.startAnimation(mRotateAnimation);

                break;
            case R.id.cet6_title:

                toggleList(mCET6ListView);

                mCET6Image.startAnimation(mRotateAnimation);

                break;
            case R.id.ky_title:

                toggleList(mKYListView);

                mKYImage.startAnimation(mRotateAnimation);

                break;
            case R.id.gre_title:

                toggleList(mGREListView);

                mGREImage.startAnimation(mRotateAnimation);

                break;
            case R.id.toefl_title:

                toggleList(mTOEFLListView);

                mTOEFLImage.startAnimation(mRotateAnimation);

                break;
            case R.id.fab:

                Intent intent = new Intent();

                Bundle bundle = new Bundle();

                bundle.putSerializable("chooseword",mChoosedSet);

                intent.putExtra("bundle",bundle);

                setResult(0, intent);

                finish();

                break;
            default:
                break;
        }

    }

    public void toggleList(ListView listView){

        if(listView.getVisibility() == View.GONE){

            listView.setVisibility(View.VISIBLE);

            mRotateAnimation.setDuration(0);
            mRotateAnimation.setFillAfter(true);

        }else {

            listView.setVisibility(View.GONE);

            mRotateAnimation.setDuration(0);
            mRotateAnimation.setFillAfter(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);

        checkBox.setChecked(!checkBox.isChecked());

        if(checkBox.isChecked()){

            mChoosedSet.add((WordInfoSugar) view.getTag());

        }else {

            mChoosedSet.remove(view.getTag());

        }



    }
}
