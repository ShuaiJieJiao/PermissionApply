package com.shuaijie.PermissionGenerate.source

interface Source {
    // 生成源码
    abstract fun generate(): StringBuilder

    // 缩进
    abstract fun indentation(): String

    abstract class SourceBuild() {
        abstract fun build(): Source
    }
}