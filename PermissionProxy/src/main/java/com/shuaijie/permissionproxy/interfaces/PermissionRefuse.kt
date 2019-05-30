package com.shuaijie.permissionproxy.interfaces

/**
 * @作者：shuaijie
 * @创建时间：2019/5/30 16:09
 * @用途：
 */
interface PermissionRefuse<T> {
    /**
     * 申请被拒绝
     * @param mContext
     * @param requestCode
     * fun refuse(mContext: T, vararg permissions: String, requestCode: Int)
     */
    fun refuse(mContext: T, vararg permissions: String, requestCode: Int)
}