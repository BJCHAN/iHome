package com.tianchuang.ihome_b.utils;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Abyss on 2017/2/25.
 * description:上传表单的帮助类
 */

public class MultipartBuilder {

	/**
	 * 把File转化成MultipartBody.Part
	 */
	public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
		List<MultipartBody.Part> parts = new ArrayList<>(files.size());
		for (File file : files) {
			String path = file.getPath();
			if (file.exists() && (MediaFile.isPngImageType(path) || MediaFile.isJpegImageType(file.getPath()))) {//判断是图片类型
				file = FileUtils.scal(Uri.parse(path));
				RequestBody requestBody;
				if (MediaFile.isPngImageType(path)) {
					requestBody = RequestBody.create(MediaType.parse("image/png"), file);
				} else {
					requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
				}
				MultipartBody.Part part = MultipartBody.Part.createFormData("livePhoto", file.getName(), requestBody);
				parts.add(part);
			}
		}
		if (parts.isEmpty()) {
			parts.add(MultipartBody.Part.createFormData("livePhoto", ""));
		}
		return parts;
	}

	/**
	 * 把File转化成MultipartBody.Part
	 * 指定表单名
	 */
	public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files,String formName) {
		List<MultipartBody.Part> parts = new ArrayList<>(files.size());
		for (File file : files) {
			String path = file.getPath();
			if (file.exists() && (MediaFile.isPngImageType(path) || MediaFile.isJpegImageType(file.getPath()))) {//判断是图片类型
				file = FileUtils.scal(Uri.parse(path));
				RequestBody requestBody;
				if (MediaFile.isPngImageType(path)) {
					requestBody = RequestBody.create(MediaType.parse("image/png"), file);
				} else {
					requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
				}
				MultipartBody.Part part = MultipartBody.Part.createFormData(formName, file.getName(), requestBody);
				parts.add(part);
			}
		}
		if (parts.isEmpty()) {
			parts.add(MultipartBody.Part.createFormData(formName, ""));
		}
		return parts;
	}
}
