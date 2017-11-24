package com.tianchuang.ihome_b.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.tianchuang.ihome_b.R
import com.tianchuang.ihome_b.adapter.PropertyListAdapter
import com.tianchuang.ihome_b.bean.PropertyListItemBean
import com.tianchuang.ihome_b.bean.event.SwitchSuccessEvent
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration
import com.tianchuang.ihome_b.mvp.MVPBaseFragment
import com.tianchuang.ihome_b.mvp.contract.PropertyListContract
import com.tianchuang.ihome_b.mvp.presenter.PropertyListPresenter
import com.tianchuang.ihome_b.mvp.ui.activity.MainActivity
import com.tianchuang.ihome_b.utils.DensityUtil
import com.tianchuang.ihome_b.utils.ToastUtil
import com.tianchuang.ihome_b.utils.UserUtil
import com.tianchuang.ihome_b.view.viewholder.EmptyViewHolder
import org.greenrobot.eventbus.EventBus
import java.util.*


/**
 * Created by Abyss on 2017/2/21.
 * description:物业列表页面
 */

class PropertyListFragment : MVPBaseFragment<PropertyListContract.View, PropertyListPresenter>(), PropertyListContract.View {
    private  var rvList: RecyclerView? = null
    private  var listAdapter: PropertyListAdapter?=null

    override val layoutId: Int
        get() = R.layout.fragment_property_list

    override fun onStart() {
        super.onStart()
        val value = holdingActivity as MainActivity
        value.setSpinnerText("我的物业")
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        rvList = view?.findViewById(R.id.rv_list)
        rvList?.apply {
            layoutManager = LinearLayoutManager(holdingActivity)
            addItemDecoration(CommonItemDecoration(DensityUtil.dip2px(context, 10f)))
        }
    }

    override fun initData() {
        //请求物业列表的数据
        mPresenter?.requestPropertyListData()
    }

    override fun initAdapter(data: ArrayList<PropertyListItemBean>) {
        listAdapter = PropertyListAdapter(data)
        val emptyViewHolder = EmptyViewHolder()
        emptyViewHolder.bindData(getString(R.string.property_no_join))
        listAdapter?.emptyView = emptyViewHolder.getholderView()
        rvList?.adapter = listAdapter
        initmListener()
    }

    /**
     * ui设置常用
     */
    override fun notifyUISetOften(position: Int) {
        listAdapter?.selsctedPostion = position
        listAdapter?.notifyDataSetChanged()
    }


    private fun initmListener() {
        //设置常用
        rvList?.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                when (view.id) {
                    R.id.fl_often_btn -> {
                        val propertyListItemBean = listAdapter!!.data[position]
                        if (!propertyListItemBean.oftenUse) {//已经是常用的不用再请求
                            mPresenter!!.requestSetOften(propertyListItemBean, position)
                        }
                    }
                }
            }
        })
        //切换物业
        rvList?.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                for (propertyListItemBean in listAdapter!!.data) {
                    propertyListItemBean.isCurrentProperty = false
                }
                val propertyListItemBean = listAdapter!!.data[position]
                propertyListItemBean.isCurrentProperty = true//当前物业
                val loginBean = UserUtil.propertyListItemBeanToLoginBean(propertyListItemBean)
                UserUtil.setLoginBean(loginBean)//更新内存中的loginbean
                listAdapter!!.notifyDataSetChanged()
                removeFragment()
                EventBus.getDefault().post(SwitchSuccessEvent(listAdapter!!.data.size))
                ToastUtil.showToast(holdingActivity, "切换成功")
            }
        })
        //长按删除需求隐藏,以免后来又加上
        //        //长按物业删除弹窗
        //        rvList.addOnItemTouchListener(new OnItemLongClickListener() {
        //            @Override
        //            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        //                PropertyListDeleteDialogFragment.newInstance().setConfirmDeleteListener(() -> {//确认删除的回调
        //                    mPresenter.requestDelete(position);
        //                }).show(getFragmentManager(), PropertyListDeleteDialogFragment.class.getSimpleName());
        //            }
        //        });
    }

    override fun deleteItem(position: Int) {
        listAdapter!!.remove(position)
    }

    companion object {
        fun newInstance(): PropertyListFragment {
            return PropertyListFragment()
        }
    }
}
