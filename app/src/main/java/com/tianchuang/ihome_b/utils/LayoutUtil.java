package com.tianchuang.ihome_b.utils;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.tianchuang.ihome_b.TianChuangApplication;

/**
 * Created by Abyss on 2017/1/16.
 * description:转换view对象的工具类
 */

public class LayoutUtil {
//    static class ViewHolder {
//        public static <T extends View> T get(View view, int id) {
//            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
//            if (viewHolder == null) {
//                viewHolder = new SparseArray<View>();
//                view.setTag(viewHolder);
//            }
//            View childView = viewHolder.get(id);
//            if (childView == null) {
//                childView = view.findViewById(id);
//                viewHolder.put(id, childView);
//            }
//            return (T) childView;
//        }
//    }

    public static View inflate(@LayoutRes int id) {
        return View.inflate(TianChuangApplication.application,id,null);
    }
}
