package com.hardwork.fg607.wordassistant.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.adapter.HistoryAdapter;
import com.hardwork.fg607.wordassistant.model.HistoryWord;
import com.hardwork.fg607.wordassistant.presenter.HistoryPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryFragment extends BaseFragment implements HistoryView{

    @Bind(R.id.recyclerview) RecyclerView mRecyclerView;

    private ArrayList<String> mHistoryList = new ArrayList<>();

    private HistoryAdapter mHistoryAdapter;

    private HistoryPresenter mPresenter;

    private OnQueryHistoryListener mListener;

    public HistoryFragment() {
    }

    @Override
    protected View createFragmentView() {
        return mInflater.inflate(R.layout.fragment_history, mContainer, false);
    }


    @Override
    protected void init() {

        ButterKnife.bind(this, mFragmentView);

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(mRecyclerView.getContext()));

        mHistoryAdapter = new HistoryAdapter(getContext());

        mHistoryAdapter.setOnRecyclerViewItemClickListener(new HistoryAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String word) {

                if (mListener != null) {

                    mListener.OnQueryHistory(word);
                }
            }
        });

        mRecyclerView.setAdapter(mHistoryAdapter);

        mPresenter = new HistoryPresenter();
        mPresenter.attachView(this);

    }

    public void setOnQueryHistoryListener(OnQueryHistoryListener listener){

        mListener = listener;
    }


    public void refreshHistory(){

        if(mPresenter != null){

            mPresenter.obtainHistory();
        }

    }

    @Override
    public void showHistory(List<HistoryWord> wordList) {

        mHistoryAdapter.setHistoryWordList(wordList);
    }


    public interface OnQueryHistoryListener{

        public void OnQueryHistory(String word);

    }
}
