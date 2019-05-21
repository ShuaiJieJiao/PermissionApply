package com.shuaijie.kotlinannotation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity

class Main2Activity : AppCompatActivity() {
    // 申请权限请求码
    val requestCode = 1
    // 是否解释
    var isexplanation: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                requestCode
            )
        }
        Handler().sendMessage(Message())

    }

    fun allow() {}// 权限申请允许
    fun refuse() {}// 权限申请拒绝

    fun isExplanation(): Boolean {
        return false// 是否提供解释
    }

    fun explanation() {
        isexplanation = false// 解释
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            requestCode
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) // 权限申请回调
        if (grantResults.contains(PackageManager.PERMISSION_DENIED)) {
            when (requestCode) {
                requestCode -> allow()
            }
        } else {
            var isExplanation: Boolean = false;
            when (requestCode) {
                requestCode -> {
                    val refusePermissions: ArrayList<String> = arrayListOf()
                    permissions.forEach { permission ->
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission) && isExplanation()) {
                            refusePermissions.add(permission)
                            isExplanation = true
                        }
                    }
                    if (isExplanation && isexplanation) {
                        explanation()
                    } else {
                        refuse()
                    }
                }
            }
        }
    }
}
