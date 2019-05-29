package com.shuaijie.PermissionGenerate.annotation

/**
 * 申请权限时被拒 标识方法
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class PermissionRefuse(val requestCode: Int)