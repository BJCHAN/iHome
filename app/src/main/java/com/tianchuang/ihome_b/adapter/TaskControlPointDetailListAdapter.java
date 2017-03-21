package com.tianchuang.ihome_b.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyTaskActivity;
import com.tianchuang.ihome_b.base.BaseActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.ControlPointItemBean;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.view.viewholder.ControlPointViewHolder;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/16.
 * description:控制点的adapter
 */

public class TaskControlPointDetailListAdapter extends BaseQuickAdapter<ControlPointItemBean, BaseViewHolder> {
    private final HashMap<Integer, FrameLayout> containerList;//容器的集合
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private TaskSubmitMultiAdapter submitMultiAdapter;
    private List<FormTypeItemBean.FieldsBean> fields;
    private SparseArray<Object> editTexts;
    private MyTaskActivity holdingActivity;
    private HashMap<Integer, BaseFragment> fragmentHashMap;

    public TaskControlPointDetailListAdapter(BaseActivity holdingActivity, List<ControlPointItemBean> data) {
        super(R.layout.task_control_point_item_holder, data);
        this.holdingActivity = (MyTaskActivity) holdingActivity;
        containerList = new HashMap<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, final ControlPointItemBean item) {
        helper.setIsRecyclable(false);
        helper.setText(R.id.tv_point_name, StringUtils.getNotNull(item.getName()))
                .setText(R.id.tv_point_address, StringUtils.getNotNull(item.getPlace()))
                .setText(R.id.tv_point_date, StringUtils.getNotNull(item.getTime()));

        final FrameLayout frameLayout = (FrameLayout) helper.getView(R.id.fl_fragment_container);
        frameLayout.setTag(item.getId() + "");
        ControlPointViewHolder controlPointViewHolder = new ControlPointViewHolder(holdingActivity);
        controlPointViewHolder.bindData(item.getFormTypeVo());
        frameLayout.addView(controlPointViewHolder.getholderView());
        item.setAddFragment(true);
        frameLayout.setVisibility(View.GONE);
        //设置展开按钮的点击事件
        helper.getView(R.id.tv_expand_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item.isExpand()) {//未展开状态
                    frameLayout.setVisibility(View.VISIBLE);
                    item.setExpand(true);
                } else {//展开状态
                    frameLayout.setVisibility(View.GONE);
                    item.setExpand(false);
                }

            }
        });
    }


    public LayoutFinishListener layoutFinishListener;

    public void setLayoutFinishListener(LayoutFinishListener layoutFinishListener) {
        this.layoutFinishListener = layoutFinishListener;
    }

    public interface LayoutFinishListener {
        void layoutFinished();
    }

}
