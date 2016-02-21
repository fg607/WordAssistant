package com.hardwork.fg607.wordassistant.view;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    protected LayoutInflater mInflater;
    protected ViewGroup mContainer;
    protected ListView mFileListView;
    protected Context mContext;
    protected View mFragmentView;
    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){

            if(!mIsDetached){
                onHandleMessage(msg);
            }

        }
    };
    protected Message mMessage = null;
    protected SharedPreferences mSharePreferences;
    protected boolean mIsDetached;


    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mSharePreferences = mContext.getSharedPreferences("config", mContext.MODE_PRIVATE);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mIsDetached = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mIsDetached = true;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mContainer = container;

        mFragmentView = createFragmentView();
        ButterKnife.bind(this, mFragmentView);

        init();

        return mFragmentView;
    }

    protected abstract void init();

    protected  void onHandleMessage(Message msg){


    }

    protected abstract View createFragmentView();

    /**
     * UpdateUI
     * @param what
     */
    public void updateUI(int what ){

        mMessage = mHandler.obtainMessage();

        mMessage.what = what;

        mHandler.sendMessage(mMessage);


    }


    public void updateUI(int what,Object obj){
        mMessage = mHandler.obtainMessage();
        mMessage.what = what;
        mMessage.obj = obj;
        mHandler.sendMessage(mMessage);
    }

    public void showMsg(String msg){

        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public Handler getHandler(){

        return mHandler;
    }

    @Override
    public void onClick(View view) {

    }
}
