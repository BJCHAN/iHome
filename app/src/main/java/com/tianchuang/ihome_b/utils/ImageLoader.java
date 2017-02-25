package com.tianchuang.ihome_b.utils;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tianchuang.ihome_b.R;

/**
 * Created by Abyss on 2017/2/25.
 * description:
 */

public class ImageLoader {
	public static void load(String path, ImageView imageView) {
		Glide.with(Utils.getContext()).load(path)
				.centerCrop()
				.placeholder(ContextCompat.getDrawable(Utils.getContext(), R.mipmap.default_logo))
				.into(imageView);
	}
}
