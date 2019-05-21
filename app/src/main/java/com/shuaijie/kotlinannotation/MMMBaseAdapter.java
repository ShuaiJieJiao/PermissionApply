package com.shuaijie.kotlinannotation;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class MMMBaseAdapter extends BaseAdapter {

    private final List<Data> list;
    private final Context mContext;

    public MMMBaseAdapter(List<Data> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item, null);

        if (list.get(position).isSeletor())
            inflate.setBackgroundColor(Color.parseColor("#ff0000"));
        else
            inflate.setBackgroundColor(Color.parseColor("#ff00FF"));

        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Data data : list) data.setSeletor(false);
                list.get(position).setSeletor(true);
                notifyDataSetChanged();

            }
        });
        return inflate;
    }
}
