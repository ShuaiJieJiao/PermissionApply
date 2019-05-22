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
            this, 1,
            permissions = *arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        )
    }

    @PermissionAllow(requestCode = 2)
    fun allowss(aa: List<String>) {
        Toast.makeText(this, "申请权限被允许", Toast.LENGTH_SHORT).show()
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
        PermissionUtils.request(
            this, 1, false,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
        )
    }
}