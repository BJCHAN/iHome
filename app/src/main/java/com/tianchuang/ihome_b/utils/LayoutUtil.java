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
    /**
     *去除searchView的下划线
     */
    public  static void removeDevider(SearchView sv) {
        if (sv != null) {
            try {        //--拿到字节码
                Class<?> argClass = sv.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(sv);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
