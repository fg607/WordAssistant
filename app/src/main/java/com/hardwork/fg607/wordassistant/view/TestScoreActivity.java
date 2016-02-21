package com.hardwork.fg607.wordassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.adapter.TestDetailAdapter;
import com.hardwork.fg607.wordassistant.model.TestStatistics;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;
import com.hardwork.fg607.wordassistant.presenter.RecitePlanPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestScoreActivity extends AppCompatActivity {

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.textview_score)
    TextView mTextViewScore;

    private ArrayList<WordInfoSugar> mAnswerList;
    private ArrayList<String> mCommitList;
    private TestDetailAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_score);

        ButterKnife.bind(this);

        setTitle("小测试成绩");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initIntent();

        initRecyclerView();

        showScore();
    }

    private void showScore() {

        int answerSize = mAnswerList.size();
        int commitSize = mCommitList.size();
        String answer, commit;
        int count = 0;

        for (int i = 0; i < commitSize; i++) {

            answer = mAnswerList.get(i).getName();
            commit = mCommitList.get(i);


            if (answer.equals(commit)) {

                count++;

                doStatistics(mAnswerList.get(i).getLevel(),true);

            }else {

                doStatistics(mAnswerList.get(i).getLevel(),false);
            }



        }

        RecitePlanPresenter.isNewData = true;

        float score = (float) count * 100 / answerSize;

        score = (float)(Math.round(score*100))/100;//保留两位小数

        mTextViewScore.setText("成绩：" + score + "分");
    }

    private void doStatistics(String level, boolean b) {

        String[] levels = level.split("/");

        StringBuilder builder = new StringBuilder();
        builder.append("(");

        for(int i = 0;i<levels.length;i++){

            if(levels[i].trim() != ""){

                if(i+1<levels.length){

                    builder.append("'"+levels[i].trim()+"'" + ",");
                }else {

                    builder.append("'"+levels[i].trim()+"'");
                }


            }
        }

        builder.append(")");


        List<TestStatistics> list = TestStatistics.findWithQuery(TestStatistics.class,
                "select * from TEST_STATISTICS where LEVEL in " + builder.toString());

        for(TestStatistics statistics:list){

            statistics.setTotal(statistics.getTotal()+1);

            if(b){

                statistics.setCorrect(statistics.getCorrect()+1);

            }

            statistics.save();
        }
    }

    private void initRecyclerView() {

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(mRecyclerView.getContext()));

        mAdapter = new TestDetailAdapter(this, mAnswerList, mCommitList);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initIntent() {

        Intent intent = getIntent();

        mAnswerList = (ArrayList<WordInfoSugar>) intent.getSerializableExtra("answerList");
        mCommitList = (ArrayList<String>) intent.getSerializableExtra("commitList");


    }

}
