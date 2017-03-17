package com.tianchuang.ihome_b.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.utils.ImageLoader;

import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:故障图片的适配器
 */
public class FaultDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private final List<String> data;

    public FaultDetailAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String url) {
        ImageView view = (ImageView) helper.getView(R.id.iv_fault);
        ImageLoader.load(url,view);
    }

}
