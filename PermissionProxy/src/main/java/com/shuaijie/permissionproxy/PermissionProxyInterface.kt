package com.shuaijie.permissionproxy

interface PermissionProxyInterface<T> {
    /**
     * 申请被允许
     * @param mContext
     * @param requestCode
     */
    fun allow(mContext: T, requestCode: Int)

    /**
     * 申请被拒绝
     * @param mContext
     * @param requestCode
     */
    fun refuse(mContext: T, requestCode: Int)

    /**
     * 为申请的权限解释
     * @param mContext
     * @param requestCode
     */
    fun explanation(mContext: T, requestCode: Int)

    /**
     * 是否解释申请权限用途
     * @return
     */
    fun isExplanation(mContext: T, requestCode: Int): Boolean {
        return false
    }
}