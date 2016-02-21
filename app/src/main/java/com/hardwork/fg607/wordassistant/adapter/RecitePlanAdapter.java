package com.hardwork.fg607.wordassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.RecitePlan;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fg607 on 15-11-20.
 */
public class RecitePlanAdapter extends RecyclerView.Adapter<RecitePlanAdapter.PlanViewHolder>
        implements View.OnClickListener {

    private List<RecitePlan> mPlanList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClickListener mItemClickListener = null;

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.recite_plan_item,viewGroup,false);
        PlanViewHolder planViewHolder = new PlanViewHolder(itemView);
        planViewHolder.img_delete.setOnClickListener(this);
        planViewHolder.tv_recite.setOnClickListener(this);
        planViewHolder.tv_test.setOnClickListener(this);
        return planViewHolder;
    }

    public RecitePlanAdapter(Context context, List<RecitePlan> planList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.mPlanList = planList;
    }

    public void setPlanList(List<RecitePlan> planList) {
        this.mPlanList = planList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(
            OnItemClickListener listener){

        mItemClickListener = listener;

    }

    @Override
    public void onBindViewHolder(PlanViewHolder planViewHolder, int i) {
        RecitePlan current = mPlanList.get(i);
        planViewHolder.tv_name.setText(current.getName());
        planViewHolder.tv_range.setText("单词范围：" + current.getRange());
        planViewHolder.tv_number.setText("单词数量：" + current.getNumber());
        planViewHolder.img_delete.setTag(current.getName());
        planViewHolder.tv_recite.setTag(current.getName());
        planViewHolder.tv_test.setTag(current.getName());
        planViewHolder.img_delete.setTag(R.id.delete_tag,Integer.valueOf(i));

    }

    @Override
    public int getItemCount() {
        return mPlanList.size();
    }

    @Override
    public void onClick(View v) {

        mItemClickListener.onItemClick(v);
    }

    class PlanViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.plan_name)TextView tv_name;
        @Bind(R.id.range) TextView tv_range;
        @Bind(R.id.number) TextView tv_number;
        @Bind(R.id.delete) ImageView img_delete;
        @Bind(R.id.button_recite) TextView tv_recite;
        @Bind(R.id.button_test) TextView tv_test;

        public PlanViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    public interface OnItemClickListener{

        void onItemClick(View view);
    }
}
