package com.shuaijie.permissionproxy

/**
 * @作者：shuaijie
 * @创建时间：2019/5/30 18:17
 * @用途：提供空实现的代理
 */
class PermissionProxy<T> : PermissionProxyInterface<T> {
    override fun allow(mContext: T, vararg permissions: String, requestCode: Int) {
    }

    override fun refuse(mContext: T, vararg permissions: String, requestCode: Int) {
    }

    override fun explanation(mContext: T, vararg permissions: String, requestCode: Int) {
    }
}