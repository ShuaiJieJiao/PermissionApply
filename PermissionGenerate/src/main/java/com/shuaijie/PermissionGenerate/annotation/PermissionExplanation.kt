package com.shuaijie.PermissionGenerate.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class PermissionExplanation(val requestCode: Int)