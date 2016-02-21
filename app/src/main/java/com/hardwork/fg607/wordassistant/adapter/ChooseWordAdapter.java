package com.hardwork.fg607.wordassistant.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hardwork.fg607.wordassistant.R;
import com.hardwork.fg607.wordassistant.model.WordInfoSugar;

import java.util.List;

/**
 * Created by fg607 on 16-1-11.
 */
public class ChooseWordAdapter extends BaseAdapter {

    private Context mContext;
    private List<WordInfoSugar> mList;

    public ChooseWordAdapter(Context mContext, List<WordInfoSugar> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    public void setList(List<WordInfoSugar> list){

        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        TextView name;
        CheckBox checkBox;

        if(convertView == null){

            view = View.inflate(mContext, R.layout.chooseword_item, null);
        }else {

            view = convertView;
        }

        name = (TextView) view.findViewById(R.id.text);
        checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        name.setText(mList.get(position).getName());
        view.setTag(mList.get(position));

        return view;
    }
}
