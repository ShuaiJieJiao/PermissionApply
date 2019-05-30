package com.shuaijie.permissionproxy.interfaces

/**
 * @作者：shuaijie
 * @创建时间：2019/5/30 16:09
 * @用途：
 */
interface PermissionIsExplanation<T> {
    /**
     * 是否解释申请权限用途
     * @return
     */
    fun isExplanation(mContext: T, vararg permissions: String, requestCode: Int): Boolean = false
}