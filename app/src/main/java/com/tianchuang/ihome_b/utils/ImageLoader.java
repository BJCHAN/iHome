package com.tianchuang.ihome_b.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.tianchuang.ihome_b.R;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Abyss on 2017/2/25.
 * description:图片加载器
 */

public class ImageLoader {
	public static void load(String path, ImageView imageView) {
		Glide.with(Utils.getContext()).load(path)
				.centerCrop()
				.placeholder(ContextCompat.getDrawable(Utils.getContext(), R.mipmap.default_logo))
				.into(imageView);
	}

	//加载图片
	public static Observable<Bitmap> loadPhoto(final String photoUrl) {
		return Observable.just(photoUrl)//部分手机加载图片方向错误
				.map(new Func1<String, Bitmap>() {
					@Override
					public Bitmap call(String s) {
						FutureTarget<File> future = Glide.with(Utils.getContext()).load(photoUrl).downloadOnly(500, 500);//方法中设置<span style="font-fami
						return ImageLoader.getBitmapByFutreTarget(future);
					}
				})
				.filter(new Func1<Bitmap, Boolean>() {
					@Override
					public Boolean call(Bitmap bitmap) {
						return bitmap != null;
					}
				});
	}

	public static Bitmap getBitmapFromPath(String path) {
		if (!new File(path).exists()) {
			System.err.println("getBitmapFromPath: file not exists");
			return null;
		}
		byte[] buf = new byte[1024 * 1024];// 1M
		Bitmap bitmap = null;

		try {

			FileInputStream fis = new FileInputStream(path);
			int len = fis.read(buf, 0, buf.length);
			bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
			if (bitmap == null) {
				System.out.println("len= " + len);
				System.err
						.println("path: " + path + "  could not be decode!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return bitmap;
	}

	/**
	 * 通过glide加载，部分机型图片方向错误
	 * return 获取正常的图片
	 */
	public static Bitmap getBitmapByFutreTarget(FutureTarget<File> future) {
		Bitmap bitmap = null;
		try {
			File cacheFile = future.get();
			String path = cacheFile.getAbsolutePath();
			//旋转图片
			bitmap = FileUtils.rotateBitmap(ImageLoader.getBitmapFromPath(path), FileUtils.readPictureDegree(path));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return bitmap;

	}


}
