package com.hardwork.fg607.wordassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.HistoryWord;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fg607 on 15-11-23.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>
        implements OnClickListener {
    private List<HistoryWord> mWordList = null;
    private LayoutInflater mInflater;
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public HistoryAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void  setHistoryWordList(List<HistoryWord> wordList){

        this.mWordList = wordList;
        notifyDataSetChanged();

    }

    public void setOnRecyclerViewItemClickListener(
            OnRecyclerViewItemClickListener listener){

        mOnItemClickListener = listener;
    }

    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.history_item,parent,false);

        HistoryViewHolder holder = new HistoryViewHolder(view);

        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.HistoryViewHolder holder, int position) {

        String word = mWordList.get(position).getWord();

        holder.word.setText(word);
        holder.itemView.setTag(word);

    }

    @Override
    public int getItemCount() {

        if(mWordList != null){

            return mWordList.size();
        }else {
            return 0;
        }

    }

    @Override
    public void onClick(View v) {

        if (mOnItemClickListener != null) {

            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }

    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.history_word) TextView word;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String word);
    }
}
