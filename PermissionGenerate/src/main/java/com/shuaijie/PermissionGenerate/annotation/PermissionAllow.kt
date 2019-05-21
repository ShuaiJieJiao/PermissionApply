package com.shuaijie.PermissionGenerate.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class PermissionAllow(val requestCode: Int)