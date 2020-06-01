package com.shuaijie.permissionproxy

import android.os.Build
import android.support.v4.app.ActivityCompat
import com.shuaijie.permissionproxy.Utils.checkPermissions
import com.shuaijie.permissionproxy.Utils.getPermissionProxy
import com.shuaijie.permissionproxy.Utils.getPermissionRequest

/**
 * 权限申请工具
 */
@SuppressWarnings("all")
object PermissionUtils {
    const val proxyName = "PermissionProxy"

    @JvmOverloads
    @JvmStatic
    fun request(
        mContext: Any, isExplantion: Boolean = true,
        /* 用于不适用注解生成类方式 */proxy: PermissionProxyInterface<Any>? = null,
        vararg permissions: String
    ) = request(mContext, 1, isExplantion, *permissions, proxy = proxy)

    /**
     * 申请权限
     * @param mContext      上下文环境 android.support.v4.app.Fragment | android.app.Fragment | android.app.Activity | android.support.v7.app.AppCompatActivity
     * @param requestCode   请求权限的请求码
     * @param permissions   请求权限数组
     * @param isExplantion  是否解释请求权限用途 默认在使用解释权限注解时解释
     * @param proxy         申请权限回调的代理对象
     */
    @JvmOverloads
    @JvmStatic
    fun request(
        mContext: Any, requestCode: Int, isExplantion: Boolean = true, vararg permissions: String,
        /* 用于不适用注解生成类方式 */proxy: PermissionProxyInterface<Any>? = null
    ) {
        val permissionRequest = getPermissionRequest(mContext)
        permissionRequest.setPermissionsProxy(if (proxy == null) getPermissionProxy(mContext) else proxy)
        //小于android 6.0 权限不需要申请
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            permissionRequest.allow(mContext, *permissions, requestCode = requestCode)
        else {
            // 检查是否有权限被拒绝
            val denyPermissions = checkPermissions(
                if (mContext is android.support.v4.app.Fragment) mContext.context!! else if (mContext is android.app.Fragment) mContext.context else (mContext as android.app.Activity),
                *permissions
            ).toTypedArray()
            //判断被拒绝权限是否提供申请权限解释
            if (denyPermissions.size > 0) {
                //未授权权限是否给用户提供权限解释
                var isRationale = false
                if (isExplantion)
                    for (denyPermission in denyPermissions)
                    //如果未授权的权限列表中有上次用户拒绝的权限并且用户需要权限解释(写有权限解释注解),
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                if (mContext is android.support.v4.app.Fragment) mContext.activity!! else if (mContext is android.app.Fragment) mContext.activity!! else (mContext as android.app.Activity),
                                denyPermission
                            ) && permissionRequest.isExplanation(
                                mContext,
                                *permissions,
                                requestCode = requestCode
                            )
                        ) {
                            isRationale = true
                            break
                        }
                if (isRationale)
                // 需要权限解释,可通过isExplantion = false 重新申请权限
                    permissionRequest.explanation(
                        mContext,
                        *denyPermissions,
                        requestCode = requestCode
                    )
                else
                //不需要权限解释,直接申请权限
                    permissionRequest.requestPermission(mContext, denyPermissions, requestCode)
            } else
            //申请的权限都被授权,直接调用允许操作方法
                permissionRequest.allow(mContext, *permissions, requestCode = requestCode)
        }
    }
}
