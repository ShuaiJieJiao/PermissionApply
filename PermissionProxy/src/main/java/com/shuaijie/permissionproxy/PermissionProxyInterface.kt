package com.shuaijie.permissionproxy

import com.shuaijie.permissionproxy.interfaces.PermissionAllow
import com.shuaijie.permissionproxy.interfaces.PermissionExplanation
import com.shuaijie.permissionproxy.interfaces.PermissionIsExplanation
import com.shuaijie.permissionproxy.interfaces.PermissionRefuse

interface PermissionProxyInterface<T> : PermissionAllow<T>, PermissionRefuse<T>, PermissionExplanation<T>,
    PermissionIsExplanation<T> {
    companion object {
        fun filterPermission(permiss: Array<String>, results: IntArray, filterValue: Int) = {
            val permis = arrayListOf<String>()
            for ((index, result) in results.withIndex())
                if (result == filterValue) permis.add(permiss.get(index))
            permis.toArray(arrayOf<String>())
        }()
    }
}