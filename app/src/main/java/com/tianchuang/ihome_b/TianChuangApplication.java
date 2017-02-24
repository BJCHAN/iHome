package com.tianchuang.ihome_b;

import android.app.Application;

import com.tianchuang.ihome_b.utils.Utils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.litepal.LitePal;

/**
 * Created by Abyss on 2017/2/9.
 * description:
 */

public class TianChuangApplication extends Application {
	public static TianChuangApplication application;

	@Override
	public void onCreate() {
		if (application == null)
			application = this;
		Utils.init(this);//初始化工具类
		LitePal.initialize(this);
		ZXingLibrary.initDisplayOpinion(this);//初始化zxing扫码
		LitePal.getDatabase();//初始化表单
	}
}
