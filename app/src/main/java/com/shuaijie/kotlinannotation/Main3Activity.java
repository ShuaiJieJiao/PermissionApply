package com.shuaijie.kotlinannotation;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.shuaijie.PermissionGenerate.annotation.PermissionAllow;
import com.shuaijie.PermissionGenerate.annotation.PermissionExplanation;
import com.shuaijie.PermissionGenerate.annotation.PermissionRefuse;
import com.shuaijie.permissionproxy.PermissionUtils;

import java.util.List;

public class Main3Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        PermissionUtils.INSTANCE.request(this, true, new PermissionProxyInterface<Object>() {
            @Override
            public boolean isExplanation(Object mContext, @NotNull String[] permissions, int requestCode) {
                return false;
            }

            @Override
            public void allow(Object mContext, @NotNull String[] permissions, int requestCode) {

            }

            @Override
            public void refuse(Object mContext, @NotNull String[] permissions, int requestCode) {

            }

            @Override
            public void explanation(Object mContext, @NotNull String[] permissions, int requestCode) {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
*/

        PermissionUtils.INSTANCE.request(this, 1, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    @PermissionAllow(requestCode = 1)
    public void allow(List<String> aa) {
        Toast.makeText(this, "申请权限被允许", Toast.LENGTH_SHORT).show();
    }

    @PermissionRefuse(requestCode = 1)
    public void refuse(List<String> aa) {
        Toast.makeText(this, "申请权限被拒绝", Toast.LENGTH_SHORT).show();
    }

    @PermissionExplanation(requestCode = 1)
    public void explanation(List<String> aa) {
        Toast.makeText(this, "申请权限需要解释", Toast.LENGTH_SHORT).show();
    }
}