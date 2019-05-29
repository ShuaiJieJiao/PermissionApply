package com.shuaijie.PermissionGenerate.annotation

/**
 * 申请权限时被全部允许 标识方法
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class PermissionAllow(val requestCode: Int)