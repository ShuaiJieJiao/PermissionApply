package com.shuaijie.permissionproxy

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.shuaijie.permissionproxy.permissionRequest.AppPermissionRequest
import com.shuaijie.permissionproxy.permissionRequest.PermissionRequest
import com.shuaijie.permissionproxy.permissionRequest.V4PermissionRequest
import java.util.*

/**
 * 权限申请工具
 */
@SuppressWarnings("all")
object PermissionUtils {
    var proxyName = "PermissionProxy"

    /**
     * 申请权限
     * @param mContext      上下文环境 android.support.v4.app.Fragment | android.app.Fragment | android.app.Activity | android.support.v7.app.AppCompatActivity
     * @param requestCode   请求权限的请求码
     * @param permissions   请求权限数组
     * @param isExplantion  是否解释请求权限用途 默认在使用解释权限注解时解释
     */
    @JvmOverloads
    fun request(mContext: Any, requestCode: Int, isExplantion: Boolean = true, vararg permissions: String) {
        val permissionRequest = getPermissionRequest(mContext)
        permissionRequest.setPermissionsProxy(getPermissionProxy(mContext))
        //小于android 6.0 权限不需要申请
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionRequest.allow(mContext, *permissions, requestCode = requestCode)
        } else {
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
                    for (denyPermission in denyPermissions) {
                        //如果未授权的权限列表中有上次用户拒绝的权限并且用户需要权限解释(写有权限解释注解),
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                if (mContext is android.support.v4.app.Fragment) mContext.activity!! else if (mContext is android.app.Fragment) mContext.activity!! else (mContext as android.app.Activity),
                                denyPermission
                            ) && permissionRequest.isExplanation(mContext, *permissions, requestCode = requestCode)
                        ) {
                            isRationale = true
                            break
                        }
                    }
                if (isRationale) {
                    // 需要权限解释,可通过isExplantion = false 重新申请权限
                    permissionRequest.explanation(mContext, *denyPermissions, requestCode = requestCode)
                } else {
                    //不需要权限解释,直接申请权限
                    permissionRequest.requestPermission(mContext, denyPermissions, requestCode)
                }
            } else {
                //申请的权限都被授权,直接调用允许操作方法
                permissionRequest.allow(mContext, *permissions, requestCode = requestCode)
            }
        }
    }

    /**
     * 创建权限监听
     * @param mContext 上下文环境 android.support.v4.app.Fragment | android.app.Fragment | android.app.Activity | android.support.v4.app.ActivityCompat
     * @return 权限监听
     */
    fun getPermissionRequest(mContext: Any): PermissionRequest {
        // 根据上下文 使用不同包下的 Fragment监听权限的申请
        return if (mContext is android.support.v7.app.AppCompatActivity || mContext is android.support.v4.app.Fragment)
            V4PermissionRequest() // 使用V4包下Fragment或Activity 作为上下文
        else if (mContext is android.app.Activity || mContext is android.app.Fragment)
            AppPermissionRequest()// 使用的是App包下的Fragment作为上下文
        else // 检测上下文环境
            throw IllegalArgumentException(
                "PermissionUtils.getPermissionRequest: 当前上下文环境${mContext::class.java.name}请确保上下文属于以下类型对象\n" +
                        "android.support.v4.app.Fragment || android.app.Fragment || android.app.Activity || android.support.v7.app.AppCompatActivity"
            )
    }

    /**
     * 创建权限回调代理
     * @param mContext 上下文环境 android.support.v4.app.Fragment | android.app.Fragment | android.app.Activity | android.support.v4.app.ActivityCompat
     * @return 权限回调代理
     */
    fun getPermissionProxy(mContext: Any): PermissionProxyInterface<Any> {
        try {
            val forName = Class.forName(mContext.javaClass.canonicalName!! + proxyName)
            return forName.newInstance() as PermissionProxyInterface<Any>
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
        throw IllegalStateException(proxyName + "类未找到,请确认新增权限注解后项目是否重新编译\n请确保使用com.shuaijie.PermissionGenerate生成代理类")
    }

    /**
     * 检查权限列表,并返回未授权情况
     * @param context 上下文环境
     * @param permissions 申请权限数组
     * @return 返回被拒绝的权限数组
     */
    private fun checkPermissions(context: Context, vararg permissions: String): List<String> {
        val denyPermissions = ArrayList<String>()
        for (permission in permissions) {
            //如果权限未被授权,则加入未授权列表
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(permission)
            }
        }
        return denyPermissions
    }
}
