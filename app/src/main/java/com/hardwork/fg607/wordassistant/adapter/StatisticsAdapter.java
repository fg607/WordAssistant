package com.hardwork.fg607.wordassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.TestStatistics;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fg607 on 15-11-20.
 */
public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder> {

    private List<TestStatistics> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private float accuracy;

    @Override
    public StatisticsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.statistics_item, viewGroup, false);
        //itemView.setBackgroundColor(ImageUtils.getRandomColor());
        StatisticsViewHolder statisticsViewHolder = new StatisticsViewHolder(itemView);
        return statisticsViewHolder;
    }

    public StatisticsAdapter(Context context, List<TestStatistics> list) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.mList = list;
    }

    public void setDataList(List<TestStatistics> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(StatisticsViewHolder statisticsViewHolder, int i) {
        TestStatistics current = mList.get(i);
        statisticsViewHolder.level.setText(current.getLevel());
        statisticsViewHolder.count.setText("单词数：" + current.getTotal());

        if(current.getTotal() != 0){

            accuracy = (float) current.getCorrect() * 100 / current.getTotal();

        }else {
            
            accuracy = 0;
        }

        statisticsViewHolder.accuracy.setText("正确率：" + (float) (Math.round(accuracy * 10)) / 10 + "%");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class StatisticsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.textview_level)
        TextView level;
        @Bind(R.id.textview_count)
        TextView count;
        @Bind(R.id.textview_accuracy)
        TextView accuracy;

        public StatisticsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
