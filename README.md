# PermissionApply 基于编译期注解 权限申请库<br>

github地址：[PermissionApply 基于编译期注解 权限申请库](https://github.com/ShuaiJieJiao/PermissionApply)

[![](https://jitpack.io/v/ShuaiJieJiao/PermissionApply.svg)](https://jitpack.io/#ShuaiJieJiao/PermissionApply)

    前言

###### 集成前置
    首先你的项目需要配置 kotlin
    
    kotlin配置
    
    在 Perject 的 build.gradle 文件中 
    
    buildscript 标签定义 应用kotlin版本
```groovy
ext.kotlin_version = '1.3.31'
```
    然后在 buildscript -> dependencies 中定义 kotlin gradler插件版本
```groovy
classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
```
## 项目集成
在 Project 的 build.gradle 文件中找到 allprojects 标签<br>
在 repositories 标签下添加 github 的 maven 库地址
```groovy
maven { url 'https://jitpack.io' }
```
#### 模块配置
在需要使用 PermissionApply 库的 module 的 build.gradle 文件中配置<br>

以下配置如果有就不需要再加了
```groovy
// 这是kotlin项目配置
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
// 这是使用kotlin的kapt功能配置
apply plugin: 'kotlin-kapt'
```
##### 配置module依赖项
在 dependencies 标签中添加
```groovy
def permissionVersion = "2.2.0"
// 如果不使用注解生成代理类可以 去除PermissionGenerate配置
// 使用注解生成代理类必须 此库是注解扫描器和注解文件
kapt "com.github.ShuaiJieJiao.PermissionApply:PermissionGenerate:$permissionVersion"
// 由于kapt不引用这个依赖注解包在开发期不能引用 所以添加编译期依赖 确保开发期可以引用到注解并不打包进apk
compileOnly "com.github.ShuaiJieJiao.PermissionApply:PermissionAnnotation:$permissionVersion"
// 权限申请工具
implementation "com.github.ShuaiJieJiao.PermissionApply:PermissionProxy:$permissionVersion"
```

如果编译出现 annotationProcessor 相关问题可以添加 排除PermissionGenerate导致的问题

```groovy
// 再次添加 annotationProcessor 对导致注解扫描器工作两次 对项目没有影响 会多消耗编译电脑的性能
annotationProcessor 'com.github.ShuaiJieJiao.PermissionApply:PermissionGenerate:2.1.0'
```

或 在 module 的 build.gradle 文件的 android -> defaultConfig 中添加

```groovy
javaCompileOptions {annotationProcessorOptions {includeCompileClasspath true}}
```

### 混淆配置
```proguard
-keep public class * implements com.shuaijie.permissionproxy.PermissionProxyInterface
```

## 使用篇

##### 注解说明

注解类 | 参数 | 用途 | 被标注方法参数说明
------------ | ------------- | ------------ | ------------
PermissionAllow | requestCode 接收对应申请对话 | 标注允许的方法 | 没有参数 或 List\<String>接收被允许的权限
PermissionRefuse | requestCode 接收对应申请对话  | 标注拒绝的方法 | 没有参数 或 List\<String>接收被拒绝的权限
PermissionExplanation | requestCode 接收对应申请对话 | 标注解释的方法 | 没有参数 或 List\<String>接收需要解释的权限

PermissionUtils.request 参数含义
```kotlin
/** 
 * 申请权限
 * @param mContext      上下文环境 android.support.v4.app.Fragment | android.app.Fragment | android.app.Activity | android.support.v7.app.AppCompatActivity
 * @param requestCode   请求权限的请求码
 * @param permissions   请求权限数组
 * @param isExplantion  是否解释请求权限用途 默认在使用解释权限注解时解释
 * @param proxy         申请权限回调的代理对象
 */
```

###### 参数说明
java 代码不使用注解生成库示例
```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 申请权限
        PermissionUtils.INSTANCE.request(this, true, new PermissionProxyInterface<Object>() {
            // 是否解释申请权限用途
            @Override
            public boolean isExplanation(Object mContext, @NotNull String[] permissions, int requestCode) {
                return false;
            }
            // 申请的权限全部被允许
            @Override
            public void allow(Object mContext, @NotNull String[] permissions, int requestCode) {
        
            }
            // 申请的权限全部或部分被拒绝
            @Override
            public void refuse(Object mContext, @NotNull String[] permissions, int requestCode) {
        
            }
            // 解释申请权限用途 并在合适时间再次申请权限
            @Override
            public void explanation(Object mContext, @NotNull String[] permissions, int requestCode) {
        
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }
}

```
java 代码使用注解生成库示例
```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 申请权限
        PermissionUtils.INSTANCE.request(this, 1, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    @PermissionAllow(requestCode = 1)
    public void allow(List<String> aa) {
        Toast.makeText(this, "申请权限被允许", Toast.LENGTH_SHORT).show();
    }

    @PermissionRefuse(requestCode = 1)
    public void refuse(List<String> aa) {
        Toast.makeText(this, "申请权限被拒绝", Toast.LENGTH_SHORT).show();
    }

    @PermissionExplanation(requestCode = 1)
    public void explanation(List<String> aa) {
        Toast.makeText(this, "申请权限需要解释", Toast.LENGTH_SHORT).show();
    }
}
```
<br>
kotlin 代码不使用注解生成库示例

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 申请权限
        PermissionUtils.request(
            this,
            permissions = *arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            proxy = object : PermissionProxyInterface<Any> {
                // 是否解释申请权限用途 kotlin 调用有默认实现 返回false
                override fun isExplanation(mContext: Any, vararg permissions: String, requestCode: Int): Boolean {
                    return super.isExplanation(mContext, *permissions, requestCode = requestCode)
                }
                
                // 申请的权限全部被允许
                override fun allow(mContext: Any, vararg permissions: String, requestCode: Int) {
                }
                
                // 申请的权限全部或部分被拒绝
                override fun refuse(mContext: Any, vararg permissions: String, requestCode: Int) {
                }
                
                // 解释申请权限用途 并在合适时间再次申请权限
                override fun explanation(mContext: Any, vararg permissions: String, requestCode: Int) {
                }
            }
        )
    }
}
```
kotlin 代码使用注解生成库示例

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 申请权限
        PermissionUtils.request(
            this, requestCode = 1, permissions = *arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        )
    }

    @PermissionAllow(requestCode = 1)
    fun allow(aa: List<String>) {
        Toast.makeText(this, "申请权限被允许", Toast.LENGTH_SHORT).show()
    }

    @PermissionRefuse(requestCode = 1)
    fun refuse(aa: List<String>) {
        Toast.makeText(this, "申请权限被拒绝", Toast.LENGTH_SHORT).show()
    }

    @PermissionExplanation(requestCode = 1)
    fun explanation(aa: List<String>) {
        Toast.makeText(this, "申请权限需要解释", Toast.LENGTH_SHORT).show()
    }
}
```

    版本预告
- [ ]  支持build构建申请权限
- [ ]  支持配置参数修改回调行为
