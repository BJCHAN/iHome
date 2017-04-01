package com.tianchuang.ihome_b.utils;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.tianchuang.ihome_b.TianChuangApplication;

import java.lang.reflect.Field;

/**
 * Created by Abyss on 2017/1/16.
 * description:转换view对象的工具类
 */

public class LayoutUtil {
//    static class ViewHolder {
//        public static <T extends View> T get(View mView, int id) {
//            SparseArray<View> viewHolder = (SparseArray<View>) mView.getTag();
//            if (viewHolder == null) {
//                viewHolder = new SparseArray<View>();
//                mView.setTag(viewHolder);
//            }
//            View childView = viewHolder.get(id);
//            if (childView == null) {
//                childView = mView.findViewById(id);
//                viewHolder.put(id, childView);
//            }
//            return (T) childView;
//        }
//    }

    public static View inflate(@LayoutRes int id) {
        return View.inflate(TianChuangApplication.application,id,null);
    }

}
