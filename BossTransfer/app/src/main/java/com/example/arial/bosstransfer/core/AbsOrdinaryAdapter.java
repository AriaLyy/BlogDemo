package com.example.arial.bosstransfer.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Lyy on 2015/3/12.
 * 可扩展的适配器
 */
public abstract class AbsOrdinaryAdapter<T, ViewHolder extends AbsOrdinaryViewHolder> extends BaseAdapter {
    private LayoutInflater mInflater;
    protected List<T> mData;
    private Context mContext;

    public AbsOrdinaryAdapter(Context context, List<T> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    /**
     * item 的type
     *
     * @param type
     * @return
     */
    protected abstract int setLayoutId(int type);

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public abstract void bindData(int position, ViewHolder helper, T item);

    public abstract ViewHolder getViewHolder(View convertView);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(setLayoutId(getItemViewType(position)), null);
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindData(position, viewHolder, mData.get(position));
        return convertView;
    }
}
