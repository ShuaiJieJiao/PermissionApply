package com.shuaijie.permissionproxy.interfaces

/**
 * @作者：shuaijie
 * @创建时间：2019/5/30 16:09
 * @用途：
 */
interface PermissionAllow<T> {
    /**
     * 申请被允许
     * @param mContext
     * @param requestCode
     * fun allow(mContext: T, vararg permissions: String, requestCode: Int)
     */
    fun allow(mContext: T, vararg permissions: String, requestCode: Int)
}