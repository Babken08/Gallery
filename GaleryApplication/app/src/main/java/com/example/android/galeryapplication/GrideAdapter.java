package com.example.android.galeryapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Android on 23.11.2017.
 */

public class GrideAdapter extends BaseAdapter {
    List<GrideItemModel> list;
    Context context;

    public GrideAdapter(Context context, List<GrideItemModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public GrideItemModel getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View rootView = layoutInflater.inflate(R.layout.row_item, viewGroup, false);

        ImageView itemImg1 =  rootView.findViewById(R.id.item_img1);
        itemImg1.setImageBitmap(getItem(i).getImg1());

        return rootView;
    }




}
