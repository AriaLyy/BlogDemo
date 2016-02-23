package com.example.arial.bosstransfer.boss;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.arial.bosstransfer.R;
import com.example.arial.bosstransfer.core.AbsOrdinaryAdapter;
import com.example.arial.bosstransfer.core.AbsOrdinaryViewHolder;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by AriaL on 2016/2/22.
 */
public class BossAdapter extends AbsOrdinaryAdapter<BossEntity, BossAdapter.BossHolder> {

    public BossAdapter(Context context, List<BossEntity> data) {
        super(context, data);
    }

    @Override
    protected int setLayoutId(int type) {
        return R.layout.item_boss;
    }

    @Override
    public void bindData(int position, BossHolder helper, BossEntity item) {
        Glide.with(getContext()).load(item.getImg()).into(helper.img);
        helper.titile.setText(item.getTitle());
    }

    @Override
    public BossHolder getViewHolder(View convertView) {
        return new BossHolder(convertView);
    }

    protected class BossHolder extends AbsOrdinaryViewHolder {
        @InjectView(R.id.img)
        ImageView img;
        @InjectView(R.id.title)
        TextView titile;

        public BossHolder(View view) {
            super(view);
        }
    }
}
