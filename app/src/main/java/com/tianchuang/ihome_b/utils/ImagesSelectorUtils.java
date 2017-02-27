package com.tianchuang.ihome_b.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tianchuang.ihome_b.R;
import com.yuyh.library.imgsel.ImgSelConfig;

/**
 * Created by Abyss on 2017/2/27.
 * description:
 */

public class ImagesSelectorUtils {


	public static ImgSelConfig getImgSelConfig() {
		Context context = Utils.getContext();
		return new ImgSelConfig.Builder(context, new com.yuyh.library.imgsel.ImageLoader() {
			@Override
			public void displayImage(Context context, String path, ImageView imageView) {
				Glide.with(context).load(path).into(imageView);
			}
		})

				.multiSelect(true)// 是否多选, 默认true
				// 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
				.rememberSelected(false)
				// “确定”按钮背景色
				.btnBgColor(Color.BLACK)
				// “确定”按钮文字颜色
				.btnTextColor(Color.WHITE)
				// 使用沉浸式状态栏
				.statusBarColor(ContextCompat.getColor(context, R.color.app_primary_color))
				// 返回图标ResId
				.backResId(R.mipmap.back)
				// 标题
				.title("图片")
				// 标题文字颜色
				.titleColor(Color.WHITE)
				// TitleBar背景色
				.titleBgColor(ContextCompat.getColor(context, R.color.app_primary_color))
				// 裁剪大小。needCrop为true的时候配置
				.cropSize(1, 1, 200, 200)
				.needCrop(false)
				// 第一个是否显示相机，默认true
				.needCamera(true)
				// 最大选择图片数量，默认9
				.maxNum(3)
				.build();
	}
}
