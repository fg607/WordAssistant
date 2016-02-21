package com.hardwork.fg607.wordassistant.presenter;

import com.hardwork.fg607.wordassistant.model.RecitePlan;
import com.hardwork.fg607.wordassistant.model.TestStatistics;
import com.hardwork.fg607.wordassistant.view.RecitePlanView;

import java.util.List;

/**
 * Created by fg607 on 16-1-3.
 */
public class RecitePlanPresenter implements Presenter<RecitePlanView> {

    public static boolean isNewData = true;
    private RecitePlanView mView;

    public RecitePlanPresenter(){

        initStatistics();
    }

    @Override
    public void attachView(RecitePlanView view) {

        mView = view;
    }

    public List<TestStatistics> getStatistics(){


        isNewData = false;
        return TestStatistics.listAll(TestStatistics.class);
    }

    public List<RecitePlan> getPlans() {

         return RecitePlan.listAll(RecitePlan.class);
    }


    public void initStatistics(){

        if(TestStatistics.count(TestStatistics.class) == 0){

            TestStatistics statistics = new TestStatistics("CET4",0,0);

            statistics.save();

            statistics = new TestStatistics("CET6",0,0);

            statistics.save();

            statistics = new TestStatistics("考研",0,0);

            statistics.save();

            statistics = new TestStatistics("GRE",0,0);

            statistics.save();

            statistics = new TestStatistics("TOEFL",0,0);

            statistics.save();

        }
    }

}
