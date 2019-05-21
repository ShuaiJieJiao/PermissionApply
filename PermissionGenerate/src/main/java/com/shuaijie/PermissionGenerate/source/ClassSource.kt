package com.shuaijie.PermissionGenerate.source

class ClassSource private constructor(val builder: Source.SourceBuild) : Source {
    override fun indentation(): String {
        return ""
    }

    override fun generate(): StringBuilder {
        return StringBuilder()
    }

    class SourceBuild(val className: String) : Source.SourceBuild() {
        private var isOpen: Boolean = false
        private var methodList: ArrayList<MethodSource> = arrayListOf()

        fun setOpen(isOpen: Boolean): SourceBuild {
            this.isOpen = isOpen
            return this
        }

        fun addMethod(method: MethodSource): SourceBuild {
            methodList.add(method)
            return this
        }

        override fun build(): Source {
            return ClassSource(this)
        }
    }
}