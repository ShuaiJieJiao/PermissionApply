# PermissionApply 基于编译期注解 权限申请库<br>

github地址：[PermissionApply 基于编译期注解 权限申请库](https://github.com/ShuaiJieJiao/PermissionApply)

## 项目集成
    首先你的项目需要使用kotlin     
在 Project 的 build.gradle 文件中找到 allprojects 标签<br>
在 repositories 标签下添加 github 的 maven 库地址
```
maven { url 'https://jitpack.io' }
```
#### 模块配置
在需要使用 PermissionApply 库的 module 的 build.gradle 文件中配置<br>

以下配置如果有就不需要再加了
```
// 这是kotlin项目配置
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
// 这是使用kotlin的kapt功能配置
apply plugin: 'kotlin-kapt'
```
##### 配置module依赖项
在 dependencies 标签中添加
```
// 使用注解生成代理类必须 此库是注解扫描器和注解文件
kapt 'com.github.ShuaiJieJiao.PermissionApply:PermissionGenerate:2.0.0'
// 由于kapt不引用这个依赖注解包在开发期不能引用 所以添加编译期依赖 确保开发期可以引用到注解并不打包进apk
compileOnly 'com.github.ShuaiJieJiao.PermissionApply:PermissionGenerate:2.0.0'
// 权限申请工具
implementation 'com.github.ShuaiJieJiao.PermissionApply:PermissionProxy:2.0.0'
```