package com.shuaijie.permissionproxy

interface PermissionProxyInterface<T> {
    companion object {
        fun filterPermission(permiss: Array<String>, results: IntArray, filterValue: Int) =
            { permiss: Array<String>, results: IntArray, c: Int ->
                val permis = arrayListOf<String>()
                for ((index, result) in results.withIndex()) {
                    if (result == c) permis.add(permiss.get(index))
                }
                permis.toArray(arrayOf<String>())
            }(permiss, results, filterValue)

    }

    /**
     * 申请被允许
     * @param mContext
     * @param requestCode
     */
    fun allow(mContext: T, vararg permissions: String, requestCode: Int)

    /**
     * 申请被拒绝
     * @param mContext
     * @param requestCode
     */
    fun refuse(mContext: T, vararg permissions: String, requestCode: Int)

    /**
     * 为申请的权限解释
     * @param mContext
     * @param requestCode
     */
    fun explanation(mContext: T, vararg permissions: String, requestCode: Int)

    /**
     * 是否解释申请权限用途
     * @return
     */
    fun isExplanation(mContext: T, vararg permissions: String, requestCode: Int): Boolean {
        return false
    }
}