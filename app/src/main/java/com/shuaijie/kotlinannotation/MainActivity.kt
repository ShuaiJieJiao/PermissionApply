package com.shuaijie.kotlinannotation

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import com.google.gson.Gson
import com.shuaijie.PermissionGenerate.annotation.PermissionAllow
import com.shuaijie.PermissionGenerate.annotation.PermissionExplanation
import com.shuaijie.PermissionGenerate.annotation.PermissionRefuse
import com.shuaijie.kotlinannotation.adapter.MyAdapter
import com.shuaijie.permissionproxy.PermissionProxyInterface
import com.shuaijie.permissionproxy.PermissionUtils
import se.emilsjolander.stickylistheaders.StickyListHeadersListView

class MainActivity : AppCompatActivity() {
    var list: StickyListHeadersListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionUtils.request(
            this, 1,
            permissions = *arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        )
        initView()
    }

    fun initView() {
        list = findViewById(R.id.list)

        list!!.adapter = MyAdapter(this)

        list!!.setSelection(1)
    }

    @PermissionAllow(requestCode = 1)
    fun allow() {
        Toast.makeText(this, "申请权限被允许", Toast.LENGTH_SHORT).show()
    }

    @PermissionRefuse(requestCode = 1)
    fun refuse() {
        Toast.makeText(this, "申请权限被拒绝", Toast.LENGTH_SHORT).show()
    }

    @PermissionExplanation(requestCode = 1)
    fun explanation() {
        Toast.makeText(this, "申请权限需要解释", Toast.LENGTH_SHORT).show()
        PermissionUtils.request(
            this, 1, false,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
        )
    }
}