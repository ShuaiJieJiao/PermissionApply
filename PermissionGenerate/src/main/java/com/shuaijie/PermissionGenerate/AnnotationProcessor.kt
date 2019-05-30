package com.shuaijie.PermissionGenerate

import com.shuaijie.PermissionGenerate.annotation.PermissionAllow
import com.shuaijie.PermissionGenerate.annotation.PermissionExplanation
import com.shuaijie.PermissionGenerate.annotation.PermissionRefuse
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic.Kind.NOTE
import javax.tools.Diagnostic.Kind.WARNING

// 源码版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
// 和系统协定代码生成路径key
@SupportedOptions(AnnotationProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class AnnotationProcessor : AbstractProcessor() {
    companion object {
        // 代码生成路径key
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"

        val permssionAnnotations: Array<Class<out Annotation>> =
            arrayOf(PermissionAllow::class.java, PermissionRefuse::class.java, PermissionExplanation::class.java)
    }

    /**
     * 存储需要生成信息
     */
    val infos: MutableMap<String, ProxyInfo> = mutableMapOf()

    /**
     * 设置需要扫描的注解
     */
    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(
        PermissionAllow::class.java.canonicalName,
        PermissionRefuse::class.java.canonicalName,
        PermissionExplanation::class.java.canonicalName
    )

    /**
     * 创建 或 获取当前注解的类信息
     */
    fun getInfo(className: Element, processingEnv: ProcessingEnvironment): ProxyInfo =
        if (infos.containsKey(className.enclosingElement.simpleName.toString()))
            infos.get(className.enclosingElement.simpleName.toString())!!
        else {
            infos.put(className.enclosingElement.simpleName.toString(), ProxyInfo(className, processingEnv))
            getInfo(className, processingEnv)
        }

    /**
     * 开始创建类信息 并生成代码
     */
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        // 获取kotlin 代码在磁盘生成的路径
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        // 向控制台输出信息
        processingEnv.messager.printMessage(WARNING, "${processingEnv.options}")
        // 遍历扫描结果
        permssionAnnotations.forEach { annotation ->
            roundEnv.getElementsAnnotatedWith(annotation).forEach { element ->
                val info = getInfo(element, processingEnv)
                var methodName =
                    "${element.simpleName}${{
                        var methodParameter = element.asType().toString()
                        if (methodParameter.matches(Regex("\\(\\).+"))) "()" else
                            if (methodParameter.matches(Regex("\\(.+List<.+String>\\).+"))) "(permissions.asList())" else
                                throw IllegalArgumentException("${element.enclosingElement}.${element} 参数错误支持List<String>")
                    }()}"
                when (annotation) {
                    PermissionAllow::class.java ->
                        info.addAllow(element.getAnnotation(annotation).requestCode, methodName)
                    PermissionRefuse::class.java ->
                        info.addRefuse(element.getAnnotation(annotation).requestCode, methodName)
                    PermissionExplanation::class.java ->
                        info.addExplanation(element.getAnnotation(annotation).requestCode, methodName)
                }
            }
        }
        // 生成kotlin代码
        infos.forEach { info ->
            File(
                // kotlin 代码生成位置
                "${kaptKotlinGeneratedDir}/${processingEnv.elementUtils.getPackageOf(info.value.element).toString().replace(
                    ".", "/"
                )}",
                // kotlin 代码生成文件名
                "${info.value.element.enclosingElement.simpleName}${ProxyInfo.proxyName}.kt"
            ).apply {
                parentFile.mkdirs()
                val generateKotlin = info.value.generateKotlin()
                processingEnv.messager.printMessage(NOTE, "\n\n${generateKotlin}")
                writeText(generateKotlin)
            }
        }
        return true
    }
}