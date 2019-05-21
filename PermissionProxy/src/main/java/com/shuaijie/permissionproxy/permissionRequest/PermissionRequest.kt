package com.shuaijie.permissionproxy.permissionRequest

import com.shuaijie.permissionproxy.PermissionProxyInterface

/**
 * 请求权限接口
 */
interface PermissionRequest : PermissionProxyInterface<Any> {
    fun requestPermission(mContext: Any, permissions: Array<String>, requestCode: Int)
    fun setPermissionsProxy(permissionProxy: PermissionProxyInterface<Any>?)
}
