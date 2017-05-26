package com.tianchuang.ihome_b.bean

import java.io.Serializable
import java.util.ArrayList

/**
 * Created by Abyss on 2017/3/9.
 * description:加载更多的数据基类
 */

open class BaseListLoadBean<T : BaseItemLoadBean> : Serializable {
    var pageSize: Int = 0
    var listVo: ArrayList<T>? = null
}
