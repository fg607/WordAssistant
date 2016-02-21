package com.hardwork.fg607.wordassistant.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.adapter.RecitePlanAdapter;
import com.hardwork.fg607.wordassistant.adapter.StatisticsAdapter;
import com.hardwork.fg607.wordassistant.model.Config;
import com.hardwork.fg607.wordassistant.model.RecitePlan;
import com.hardwork.fg607.wordassistant.model.TestStatistics;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;
import com.hardwork.fg607.wordassistant.presenter.RecitePlanPresenter;

import java.util.List;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RecitePlanFragment extends BaseFragment implements RecitePlanView{

    @Bind(R.id.plan_list) RecyclerView mPlanRecyclerView;
    @Bind(R.id.no_plan_text) TextView mNoPlanText;
    @Bind(R.id.recyclerview_statistics) RecyclerView mStatisticsRecyclerView;
    @Bind(R.id.textview_statistics) TextView mButtonStatistics;

    private int mPlanNumber = 0;
    private RecitePlanAdapter mPlanAdapter;
    private List<RecitePlan> mPlanList;

    private RecitePlanPresenter mPresenter;

    private StatisticsAdapter mStatisticsAdapter;
    private List<TestStatistics> mStatisticsesList;
    private FloatingActionButton mFab;


    public RecitePlanFragment() {
    }



    @Override
    protected View createFragmentView() {
        return mInflater.inflate(R.layout.fragment_recite_plan, mContainer, false);
    }

    @Override
    protected void init() {

        mPresenter = new RecitePlanPresenter();

        mPresenter.attachView(this);


        getPlans();
        getStatistics();


        mPlanRecyclerView.setLayoutManager(
                new LinearLayoutManager(mPlanRecyclerView.getContext()));

        mPlanAdapter = new RecitePlanAdapter(mContext,mPlanList);

        mPlanAdapter.setOnItemClickListener(new RecitePlanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {

                switch (view.getId()) {

                    case R.id.delete:
                        deletePlan((String) view.getTag(), (Integer) view.getTag(R.id.delete_tag));
                        break;
                    case R.id.button_recite:
                        startRecite((String) view.getTag());
                        break;
                    case R.id.button_test:
                        startTest((String) view.getTag());
                        break;
                    default:
                        break;
                }
            }
        });


        mPlanRecyclerView.setAdapter(mPlanAdapter);




        mStatisticsRecyclerView.setLayoutManager(
                new LinearLayoutManager(mStatisticsRecyclerView.getContext()));

        mStatisticsAdapter = new StatisticsAdapter(mContext,mStatisticsesList);

        mStatisticsRecyclerView.setAdapter(mStatisticsAdapter);

        mButtonStatistics.setOnClickListener(this);

    }


    public void setFab(FloatingActionButton fab){

        mFab = fab;

    }
    private void startRecite(String planName) {

        Intent intent1 = new Intent(mContext,ReciteActivity.class);
        intent1.putExtra("whichFragment", Config.RECITE_FRAGMENT);
        intent1.putExtra("planName", planName);
        startActivity(intent1);

    }

    private void startTest(String planName) {


        Intent intent = new Intent(mContext,TestStyleActivity.class);

        intent.putExtra("planName",planName);

        startActivity(intent);

    }

    private void deletePlan(final String planName, final int listPosition) {

        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("确定删除计划?")
                .setConfirmText("删除!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        RecitePlan.executeQuery("delete from RECITE_PLAN where NAME = '"
                                + planName + "'");

                        int size = mPlanList.size();
                        mPlanList.remove(listPosition);

                        if (mPlanList.size() == 0) {

                            mNoPlanText.setText("还没有计划！");
                        }

                        mPlanAdapter.notifyItemRemoved(listPosition);
                        //刷新传入的position与mPlanList的position同步
                        mPlanAdapter.notifyItemRangeChanged(listPosition,size);
                        deleteWordInfoSugarPlan(planName);


                    }
                })
                .show();

    }

    private void getPlans() {

        mPlanList = mPresenter.getPlans();

        if(mPlanList.size() == 0){

            mNoPlanText.setText("还没有计划！");

        } else{

            mNoPlanText.setText("");
        }
    }

    private void getStatistics() {

        mStatisticsesList = mPresenter.getStatistics();

    }

    public void deleteWordInfoSugarPlan(final String planName){

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<WordInfoSugar> list = WordInfoSugar.findWithQuery(WordInfoSugar.class,
                        "select * from WORD_INFO_SUGAR where PLAN like '%" + planName + "%'");

                for(WordInfoSugar sugar:list){


                    String plan = sugar.getPlan().replaceAll("/" + planName,"");

                    sugar.setPlan(plan);

                    sugar.save();
                }

            }
        }).start();


    }

    @Override
    public void refreshPlan() {

        getPlans();

       mPlanAdapter.setPlanList(mPlanList);

    }

    @Override
    public void refreshStatistics() {

        getStatistics();

        mStatisticsAdapter.setDataList(mStatisticsesList);

    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){

           case R.id.textview_statistics:

               if(mStatisticsRecyclerView.getVisibility() == View.GONE){

                   mStatisticsRecyclerView.setVisibility(View.VISIBLE);

                   if(mFab != null){
                       mFab.hide();
                   }
               }else {

                   mStatisticsRecyclerView.setVisibility(View.GONE);

                   if(mFab != null){
                       mFab.show();
                   }
               }

               break;
           default:
               break;
       }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mPresenter.isNewData){

            refreshStatistics();
        }

    }
}
