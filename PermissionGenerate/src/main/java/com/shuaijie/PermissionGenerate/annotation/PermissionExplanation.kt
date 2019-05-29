package com.shuaijie.PermissionGenerate.annotation

/**
 * 申请权限被拒二次申请 解释申请权限用途 标识方法
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class PermissionExplanation(val requestCode: Int)