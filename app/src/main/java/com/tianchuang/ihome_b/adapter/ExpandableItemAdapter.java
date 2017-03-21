package com.tianchuang.ihome_b.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyTaskActivity;
import com.tianchuang.ihome_b.bean.ControlPointItemBean;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.bean.recyclerview.CustomLinearLayoutManager;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/21.
 * description:
 */

public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private final MyTaskActivity holdActivity;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(MyTaskActivity holdActivity, List<MultiItemEntity> data) {
        super(data);
        this.holdActivity = holdActivity;
        addItemType(TYPE_LEVEL_0, R.layout.task_control_point_item_holder);
        addItemType(TYPE_LEVEL_1, R.layout.fragment_task_control_point_edit);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final ControlPointItemBean lv0 = (ControlPointItemBean) item;
//                holder.setText(R.id.title, lv0.title)
//                        .setText(R.id.sub_title, lv0.subTitle)
//                        .setImageResource(R.id.iv, lv0.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);
                holder.setText(R.id.tv_point_name, StringUtils.getNotNull(lv0.getName()))
                        .setText(R.id.tv_point_address, StringUtils.getNotNull(lv0.getPlace()))
                        .setText(R.id.tv_point_date, StringUtils.getNotNull(lv0.getTime()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(pos,false);
                        } else {
                            expand(pos,false);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final FormTypeItemBean lv1 = (FormTypeItemBean) item;
                RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.rv_list);
                initView(recyclerView, lv1);
                break;
        }
    }

    private void initView( RecyclerView mRecyclerView, FormTypeItemBean formTypeItemBean) {
        mRecyclerView.addItemDecoration(new CommonItemDecoration(DensityUtil.dip2px(mRecyclerView.getContext(), 20)));
        List<FormTypeItemBean.FieldsBean> fields = formTypeItemBean.getFields();
        if (fields.size() > 0) {
            SparseArray<Object> editTexts = new SparseArray<>();
            TaskSubmitMultiAdapter submitMultiAdapter = new TaskSubmitMultiAdapter(holdActivity, fields);
            submitMultiAdapter.setSaveEditListener(new TaskSubmitMultiAdapter.SaveEditListener() {
                @Override
                public void SaveEdit(int position, String string) {

                }
            });
            //添加腿部
            View view = LayoutUtil.inflate(R.layout.form_submit_footer_holder);
            view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            submitMultiAdapter.addFooterView(view);
            mRecyclerView.setAdapter(submitMultiAdapter);
            CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(holdActivity);
            customLinearLayoutManager.setScrollEnabled(false);
            mRecyclerView.setLayoutManager(customLinearLayoutManager);
        }
    }
}
