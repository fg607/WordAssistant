package com.hardwork.fg607.wordassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReciteActivity extends AppCompatActivity {

    @Bind(R.id.fab) FloatingActionButton mFab;

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private int mFragmentFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recite);

        ButterKnife.bind(this);

        initToolBar();

        initFragment();

        initFloatActionButton();
    }

    private void initFloatActionButton() {

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (mFragmentFlag) {

                    case Config.NEWPLAN_FRAGMENT:

                        NewPlanFragment newPlanFragment = (NewPlanFragment) mFragment;

                        newPlanFragment.saveNewPlan();

                        break;
                    case Config.TEST_FRAGMENT:

                        TestFragment testFragment = (TestFragment) mFragment;
                        testFragment.commit();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void initFragment() {

        Intent intent = getIntent();

        if(intent != null){

            int whichFrament = intent.getIntExtra("whichFragment",-1);

            mFragmentFlag = whichFrament;

            switch (whichFrament){

                case Config.NEWPLAN_FRAGMENT:
                    setTitle("制定背记计划");
                    mFragment = new NewPlanFragment();
                    break;
                case Config.RECITE_FRAGMENT:
                    initReciteFragment(intent.getStringExtra("planName"));
                    break;
                case Config.TEST_FRAGMENT:

                    mFragment = new TestFragment();

                    String planName = intent.getStringExtra("planName");

                    int style = intent.getIntExtra("style", -1);

                    TestFragment fragment = (TestFragment) mFragment;

                    fragment.bindPlan(planName,style);

                    setTitle(planName);

                    break;
                default:
                    break;
            }

            mFragmentManager = getSupportFragmentManager();

            mFragmentManager.beginTransaction().replace(R.id.container,mFragment).commit();

        }


    }

    private void initReciteFragment(String planName) {

        mFab.hide();
        mFragment = new ReciteFragment();
        ReciteFragment fragment = (ReciteFragment) mFragment;
        fragment.bindPlan(planName);
        setTitle(planName);

    }

    private void initToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
