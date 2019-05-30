package com.shuaijie.permissionproxy.interfaces

/**
 * @作者：shuaijie
 * @创建时间：2019/5/30 16:09
 * @用途：
 */
interface PermissionExplanation<T> {
    /**
     * 为申请的权限解释
     * @param mContext
     * @param requestCode
     * fun explanation(mContext: T, vararg permissions: String, requestCode: Int)
     */
    fun explanation(mContext: T, vararg permissions: String, requestCode: Int)
}