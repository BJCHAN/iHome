package com.tianchuang.ihome_b.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Abyss on 2017/2/25.
 * description:
 */

public class MultipartBuilder {
//	/**
//	 * 把File对象转化成MultipartBody
//	 */
//	public static MultipartBody filesToMultipartBody(List<File> files) {
//		MultipartBody.Builder builder = new MultipartBody.Builder();
//		int i = 0;
//		for (File file : files) {
//			//  这里为了简单起见，没有判断file的类型
//			if (file.exists()) {
//				RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
//				builder.addFormDataPart("file", file.getName(), requestBody);
//			}
//			i++;
//		}
//
//		builder.setType(MultipartBody.FORM);
//		MultipartBody multipartBody = builder.build();
//		return multipartBody;
//	}

	/**
	 * 把File转化成MultipartBody.Part
	 */
	public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
		List<MultipartBody.Part> parts = new ArrayList<>(files.size());
		for (File file : files) {
			String path = file.getPath();
			if (file.exists() && (MediaFile.isPngImageType(path) || MediaFile.isJpegImageType(file.getPath()))) {//判断是图片类型
				RequestBody requestBody;
				if (MediaFile.isPngImageType(path)) {
					requestBody = RequestBody.create(MediaType.parse("image/png"), file);
				} else {
					requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
				}
				MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
				parts.add(part);
			}

		}
		if (parts.isEmpty()) {
			parts.add(MultipartBody.Part.createFormData("image",""));
		}
		return parts;
	}
}
