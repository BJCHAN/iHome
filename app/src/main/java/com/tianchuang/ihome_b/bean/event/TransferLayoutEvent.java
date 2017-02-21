package com.tianchuang.ihome_b.bean.event;

import com.hitomi.tilibrary.TransferImage;

/**
 * Created by Abyss on 2017/2/16.
 * description:拿到浏览图片layout的引用的事件
 */

public class TransferLayoutEvent {
	public TransferImage transferImage;
	public TransferLayoutEvent(TransferImage transferImage) {
		this.transferImage = transferImage;
	}
}
