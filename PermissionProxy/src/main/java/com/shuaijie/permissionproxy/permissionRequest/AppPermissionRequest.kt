package com.shuaijie.permissionproxy.permissionRequest

import android.app.Activity
import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Build
import com.shuaijie.permissionproxy.PermissionProxyInterface
import com.shuaijie.permissionproxy.PermissionUtils

@SuppressWarnings("all")
class AppPermissionRequest : Fragment(), PermissionRequest {
    var permissions: Array<String> = arrayOf()
    var requestCode: Int = 0
    var mContext: Any? = null;
    var permissionsResult: PermissionProxyInterface<Any>? = null;
    /**
     * 请求权限
     */
    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, requestCode)
    }

    /**
     * 申请权限
     * @param mContext 上下文环境
     * @param permissions 需要申请的权限数组
     * @param requestCode 请求码
     */
    override fun requestPermission(mContext: Any, permissions: Array<String>, requestCode: Int) {
        this.permissions = permissions
        this.requestCode = requestCode
        this.mContext = mContext
        this.permissionsResult = createProxy(mContext)

        // 设置权限监听
        if (mContext is Fragment) mContext.fragmentManager.beginTransaction().add(
            this,
            this::class.java.simpleName
        ).commitAllowingStateLoss() else if (mContext is Activity) mContext.fragmentManager.beginTransaction().add(
            this,
            this::class.java.simpleName
        ).commitAllowingStateLoss() else throw IllegalArgumentException("mContext Type Exception 上下文类型不支持");
    }

    override fun setPermissionsProxy(permissionProxy: PermissionProxyInterface<Any>?) {
        permissionsResult = permissionProxy
    }

    /**
     * @param mContext 创建权限代理对象
     */
    fun createProxy(mContext: Any): PermissionProxyInterface<Any> {
        if (permissionsResult == null)
            return (PermissionUtils.getPermissionProxy(mContext))
        else return permissionsResult!!;
    }

    /**
     * 权限回调
     * @param requestCode 请求权限 响应码
     * @param permissions 请求权限数组
     * @param grantResults 请求权限回应状态
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 权限回调结果分发
        if (grantResults.contains(PackageManager.PERMISSION_DENIED))
            refuse(
                mContext!!,
                *filterPermission(permissions, grantResults, PackageManager.PERMISSION_DENIED),
                requestCode = requestCode
            )
        else allow(
            mContext!!,
            *filterPermission(permissions, grantResults, PackageManager.PERMISSION_GRANTED),
            requestCode = requestCode
        )

        // 移除监听
        if (mContext is Fragment) (mContext as Fragment).fragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
        else if (mContext is Activity) (mContext as Activity).fragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
    }

    override fun allow(mContext: Any, vararg permissions: String, requestCode: Int) {
        createProxy(mContext).allow(mContext, *permissions, requestCode = requestCode)
    }

    override fun refuse(mContext: Any, vararg permissions: String, requestCode: Int) {
        createProxy(mContext).refuse(mContext, *permissions, requestCode = requestCode)
    }

    override fun explanation(mContext: Any, vararg permissions: String, requestCode: Int) {
        createProxy(mContext).explanation(mContext, *permissions, requestCode = requestCode)
    }

    override fun isExplanation(mContext: Any, vararg permissions: String, requestCode: Int): Boolean {
        return createProxy(mContext).isExplanation(mContext, *permissions, requestCode = requestCode)
    }
}
