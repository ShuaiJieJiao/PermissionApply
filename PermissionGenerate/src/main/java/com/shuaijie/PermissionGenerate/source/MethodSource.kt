package com.shuaijie.PermissionGenerate.source

class MethodSource private constructor(val builder: SourceBuild) : Source {
    override fun generate(): StringBuilder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun indentation(): String {
        return "\n\t"
    }

    class SourceBuild(val methodName: String) : Source.SourceBuild() {
        private var isOverride: Boolean = false
        private var isFinal: Boolean = true

        // 是否Override 默认false
        fun setOverride(): SourceBuild {
            isOverride = true
            return this
        }

        // 是否final 默认true
        fun setFinal(isFinal: Boolean): SourceBuild {
            this.isFinal = isFinal
            return this
        }

        override fun build(): Source {
            return MethodSource(this)
        }
    }
}