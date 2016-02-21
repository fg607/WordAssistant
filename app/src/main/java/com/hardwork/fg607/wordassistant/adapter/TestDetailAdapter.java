package com.hardwork.fg607.wordassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fg607 on 15-11-20.
 */
public class TestDetailAdapter extends RecyclerView.Adapter<TestDetailAdapter.DetailViewHolder>
        implements View.OnClickListener {

    private ArrayList<WordInfoSugar> mAnswerList;
    private ArrayList<String> mCommitList;
    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClickListener mItemClickListener = null;
    private String mAnswer;
    private String mCommit;

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.test_detail_item,viewGroup,false);
        DetailViewHolder detailViewHolder = new DetailViewHolder(itemView);
        return detailViewHolder;
    }

    public TestDetailAdapter(Context context, ArrayList<WordInfoSugar> answerList,
                             ArrayList<String> commitList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.mAnswerList = answerList;
        this.mCommitList = commitList;
    }


    public void setOnItemClickListener(
            OnItemClickListener listener){

        mItemClickListener = listener;

    }

    @Override
    public void onBindViewHolder(DetailViewHolder detailViewHolder, int i) {
        mAnswer = mAnswerList.get(i).getName();
        if(mCommitList.size() > i){

            mCommit = mCommitList.get(i);

            if(TextUtils.isEmpty(mCommit)){


                mCommit = "没作答！";
            }

        }else {

            mCommit = "没作答！";
        }
        detailViewHolder.id.setText("第"+(i+1)+"题");
        detailViewHolder.commit.setText(mCommit);
        if(mCommit.equals(mAnswer)){

            detailViewHolder.image.setImageDrawable(
                    mContext.getResources().getDrawable(R.drawable.pic_choose));
        }else {

            detailViewHolder.image.setImageDrawable(
                    mContext.getResources().getDrawable(R.drawable.pic_delete));
        }

        detailViewHolder.answer.setText(mAnswer);

    }

    @Override
    public int getItemCount() {
        return mAnswerList.size();
    }

    @Override
    public void onClick(View v) {

        mItemClickListener.onItemClick(v);
    }

    class DetailViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.id)TextView id;
        @Bind(R.id.commit) TextView commit;
        @Bind(R.id.image_rw) ImageView image;
        @Bind(R.id.answer)  TextView answer;

        public DetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    public interface OnItemClickListener{

        void onItemClick(View view);
    }
}
