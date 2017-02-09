package com.tianchuang.ihome_b.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.DrawMenuItem;

import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:主页菜单的适配器
 */
public class DrawMenuAdapter extends BaseQuickAdapter<DrawMenuItem> {
    public DrawMenuAdapter(int layoutResId, List<DrawMenuItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DrawMenuItem item) {
        helper.setText(R.id.tv_title,item.getName());
    }

}
