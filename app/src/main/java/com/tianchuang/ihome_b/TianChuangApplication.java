package com.tianchuang.ihome_b;

import android.app.Application;

import com.tianchuang.ihome_b.utils.Utils;

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
		Utils.init(this);
	}
}
