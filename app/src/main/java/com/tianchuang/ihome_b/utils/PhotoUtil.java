package com.tianchuang.ihome_b.utils;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Abyss on 2017/2/27.
 * description:
 */

public class PhotoUtil {

	private static String pathname;

	public static String getPathname() {
		return pathname;
	}

	/**
	 * 创建图片压缩的临时文件
	 */
	public static Uri createImageFile() {

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		pathname = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tianchuang/image/";
		File storageDir = new File(pathname);
		if (!storageDir.exists()) {
			storageDir.mkdirs();
		}

		File image = null;
		try {
			image = File.createTempFile(
					imageFileName,  /* prefix */
					".jpg",         /* suffix */
					storageDir      /* directory */
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Save a file: path for use with ACTION_VIEW intents
		return Uri.fromFile(image);
	}

	public static void copyFileUsingFileChannels(File source, File dest) {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			try {
				inputChannel = new FileInputStream(source).getChannel();
				outputChannel = new FileOutputStream(dest).getChannel();
				outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				inputChannel.close();
				outputChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
