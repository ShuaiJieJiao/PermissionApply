package com.shuaijie.PermissionGenerate

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element

class ProxyInfo(var element: Element, processingEnv: ProcessingEnvironment) {
    companion object {
        const val proxyInterfece = "com.shuaijie.permissionproxy.PermissionProxyInterface<T>"
        const val proxyName = "PermissionProxy"
    }

    // 包信息
    var packageName: String
    // 导包信息
    var importList: ArrayList<String> = arrayListOf();
    // 存储允许授权方法
    var allow: MutableMap<Int, String> = mutableMapOf()
    // 存储拒绝授权方法
    var refuse: MutableMap<Int, String> = mutableMapOf()
    // 存储解释授权方法
    var explanation: MutableMap<Int, String> = mutableMapOf()

    init {
        packageName = "package ${processingEnv.elementUtils.getPackageOf(element)}"
        addImport(proxyInterfece)
    }

    // 添加导包
    fun addImport(importStr: String) = importList.add(importStr)

    // 添加允许方法
    fun addAllow(requestCode: Int, methodName: String) = allow.put(requestCode, methodName)

    // 添加拒绝方法
    fun addRefuse(requestCode: Int, methodName: String) = refuse.put(requestCode, methodName)

    // 添加解释方法
    fun addExplanation(requestCode: Int, methodName: String) = explanation.put(requestCode, methodName)

    // 生成方法
    fun generateMethod(methodName: String, methodBody: Map<Int, String>): String =
        "\n\toverride fun $methodName(mContext: T, vararg permissions: String, requestCode: Int) {\n" +
                "\t\twhen (requestCode) {\n" + "${{
            var method = StringBuffer()
            methodBody.entries.forEach { entry -> method.append("\t\t\t${entry.key} -> mContext.${entry.value}\n") }
            method
        }()}\t\t}\n\t}\n"

    // 生成是否解释判断方法
    fun generateIsExplanationMethod(): String =
        "\n\toverride fun isExplanation(mContext: T, vararg permissions: String, requestCode: Int): Boolean {\n" +
                "\t\treturn when (requestCode) {\n" + "${{
            var method = StringBuffer()
            explanation.entries.forEach { entry -> method.append("\t\t\t${entry.key} -> ${true}\n") }
            method.append("\t\t\telse ->${false}\n")
            method
        }()}\t\t}\n\t}\n"

    // 生成Kotlin类
    fun generateKotlin(): String = "$packageName\n\n" +
            "${{
                val import = StringBuffer()
                // 添加导包
                importList.forEach { importName ->
                    import.append(
                        "import ${importName.replace(
                            Regex("<[\\w]+>"),
                            ""
                        )}\n"
                    )
                }
                import.toString()
            }()}\n" +
            // 类名
            "class ${element.enclosingElement.simpleName}$proxyName<T : ${element.enclosingElement.simpleName}> : ${proxyInterfece.substring(
                proxyInterfece.lastIndexOf(".") + 1
            )} {" +
            // 权限允许方法
            "${generateMethod("allow", allow)} " +
            // 权限拒绝方法
            "${generateMethod("refuse", refuse)} " +
            // 权限解释方法
            "${generateMethod("explanation", explanation)} " +
            // 是否解释方法
            "${generateIsExplanationMethod()}" +
            "}"
}