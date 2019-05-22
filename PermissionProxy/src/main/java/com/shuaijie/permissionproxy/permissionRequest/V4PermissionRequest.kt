package com.shuaijie.permissionproxy.permissionRequest

import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.shuaijie.permissionproxy.PermissionProxyInterface
import com.shuaijie.permissionproxy.PermissionUtils

class V4PermissionRequest : Fragment(), PermissionRequest {
    private var mContext: Any? = null
    private var permissions: Array<String> = arrayOf()
    private var requestCode: Int = 0
    var permissionsResult: PermissionProxyInterface<Any>? = null;
    /**
     * 请求权限
     */
    override fun onStart() {
        super.onStart()
        requestPermissions(permissions, requestCode)
    }

    /**
     * 申请权限
     * @param mContext 上下文环境
     * @param permissions 需要申请的权限数组
     * @param requestCode 请求码
     */
    override fun requestPermission(mContext: Any, permissions: Array<String>, requestCode: Int) {
        this.mContext = mContext
        this.permissions = permissions
        this.requestCode = requestCode
        this.permissionsResult = createProxy(mContext)

        // 设置权限监听
        if (mContext is Fragment) mContext.childFragmentManager.beginTransaction().add(
            this,
            this::class.java.simpleName
        ).commitAllowingStateLoss()
        else if (mContext is AppCompatActivity) mContext.supportFragmentManager.beginTransaction().add(
            this,
            this::class.java.simpleName
        ).commitAllowingStateLoss()
        else throw IllegalArgumentException("mContext Type Exception 上下文类型不支持");
    }

    override fun setPermissionsProxy(permissionProxy: PermissionProxyInterface<Any>?) {
        permissionsResult = permissionProxy
    }

    /**
     * @param mContext 创建权限代理对象
     */
    fun createProxy(objects: Any): PermissionProxyInterface<Any> {
        if (permissionsResult == null)
            return (PermissionUtils.getPermissionProxy(objects))
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
        if (mContext is Fragment) (mContext as Fragment).childFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
        else if (mContext is AppCompatActivity) (mContext as AppCompatActivity).supportFragmentManager.beginTransaction().remove(
            this
        ).commitAllowingStateLoss()
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
