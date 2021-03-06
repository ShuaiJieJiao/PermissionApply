package com.shuaijie.kotlinannotation

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.shuaijie.PermissionGenerate.annotation.PermissionAllow
import com.shuaijie.PermissionGenerate.annotation.PermissionExplanation
import com.shuaijie.PermissionGenerate.annotation.PermissionRefuse
import com.shuaijie.permissionproxy.PermissionUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionUtils.request(
            this,
            1,
            isExplantion = false,
            permissions = *arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        )

        // 申请权限
/*
        PermissionUtils.request(
            this,
            permissions = *arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            proxy = object : PermissionProxyInterface<Any> {
                // 是否解释申请权限用途 kotlin 调用有默认实现 返回false
                override fun isExplanation(mContext: Any, vararg permissions: String, requestCode: Int): Boolean {
                    return super.isExplanation(mContext, *permissions, requestCode = requestCode)
                }

                // 申请的权限全部被允许
                override fun allow(mContext: Any, vararg permissions: String, requestCode: Int) {
                }

                // 申请的权限全部或部分被拒绝
                override fun refuse(mContext: Any, vararg permissions: String, requestCode: Int) {
                }

                // 解释申请权限用途 并在合适时间再次申请权限
                override fun explanation(mContext: Any, vararg permissions: String, requestCode: Int) {
                }
            }
        )
*/
    }

    @PermissionAllow(requestCode = 1)
    fun allow(aa: List<String>) {
        Toast.makeText(this, "申请权限被允许", Toast.LENGTH_SHORT).show()
    }

    @PermissionRefuse(requestCode = 1)
    fun refuse(aa: List<String>) {
        Toast.makeText(this, "申请权限被拒绝", Toast.LENGTH_SHORT).show()
    }

    @PermissionExplanation(requestCode = 1)
    fun explanation(aa: List<String>) {
        Toast.makeText(this, "申请权限需要解释", Toast.LENGTH_SHORT).show()
    }
}