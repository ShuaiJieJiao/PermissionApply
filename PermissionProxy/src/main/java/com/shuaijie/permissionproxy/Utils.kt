package com.shuaijie.permissionproxy

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.shuaijie.permissionproxy.permissionRequest.AppPermissionRequest
import com.shuaijie.permissionproxy.permissionRequest.PermissionRequest
import com.shuaijie.permissionproxy.permissionRequest.V4PermissionRequest
import java.util.*

/**
 * @作者：shuaijie
 * @创建时间：2019/6/1 19:38
 * @用途：
 */
object Utils {
    /**
     * 创建权限监听
     * @param mContext 上下文环境 android.support.v4.app.Fragment | android.app.Fragment | android.app.Activity | android.support.v4.app.ActivityCompat
     * @return 权限监听
     */
    fun getPermissionRequest(mContext: Any): PermissionRequest =
        // 根据上下文 使用不同包下的 Fragment监听权限的申请
        if (mContext is android.support.v7.app.AppCompatActivity || mContext is android.support.v4.app.Fragment)
            V4PermissionRequest() // 使用V4包下Fragment或Activity 作为上下文
        else if (mContext is android.app.Activity || mContext is android.app.Fragment)
            AppPermissionRequest()// 使用的是App包下的Fragment作为上下文
        else // 检测上下文环境
            throw IllegalArgumentException(
                "PermissionUtils.getPermissionRequest: 当前上下文环境${mContext::class.java.name}请确保上下文属于以下类型对象\n" +
                        "android.support.v4.app.Fragment || android.app.Fragment || android.app.Activity || android.support.v7.app.AppCompatActivity"
            )

    /**
     * 创建权限回调代理
     * @param mContext 上下文环境 android.support.v4.app.Fragment | android.app.Fragment | android.app.Activity | android.support.v4.app.ActivityCompat
     * @return 权限回调代理
     */
    fun getPermissionProxy(mContext: Any): PermissionProxyInterface<Any> = try {
        val forName = Class.forName(mContext.javaClass.canonicalName!! + PermissionUtils.proxyName)
        forName.newInstance() as PermissionProxyInterface<Any>
    } catch (e: Exception) {
        throw IllegalStateException(PermissionUtils.proxyName + "类未找到,请确认新增权限注解后项目是否重新编译\n请确保使用com.shuaijie.PermissionGenerate生成代理类")
    }

    /**
     * 检查权限列表,并返回未授权情况
     * @param context 上下文环境
     * @param permissions 申请权限数组
     * @return 返回被拒绝的权限数组
     */
    fun checkPermissions(context: Context, vararg permissions: String): List<String> = {
        val denyPermissions = ArrayList<String>()
        for (permission in permissions)
        //如果权限未被授权,则加入未授权列表
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                denyPermissions.add(permission)
        denyPermissions
    }()
}