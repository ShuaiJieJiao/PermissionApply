# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
 #-------------------------------------------定制化区域----------------------------------------------
 #---------------------------------1.实体类---------------------------------
-keep class com.yuntu.mobilelibrary.sdk.vo.** {*;}
-keep class com.yuntu.mobilelibrary.vo.** {*;}

 #-------------------------------------------------------------------------

 #---------------------------------2.第三方包-------------------------------
#EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

#Glide
-dontwarn com.bumptech.glide.**
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
#自己写的api列表，这里一定要加上，不然使用Retrofit会报400
-keep class com.yuntu.mobilelibrary.sdk.api.ApiService {*;}
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

 #-------------------------------------------------------------------------

 #---------------------------------3.与js互相调用的类------------------------

-keep class com.yuntu.mobilelibrary.ui.js.** { *; }

 #-------------------------------------------------------------------------

 #---------------------------------4.反射相关的类和方法-----------------------



 #----------------------------------------------------------------------------
 #---------------------------------------------------------------------------------------------------

 #-------------------------------------------基本不用动区域--------------------------------------------
 #---------------------------------基本指令区----------------------------------
 -optimizationpasses 5
 -dontusemixedcaseclassnames
 -dontskipnonpubliclibraryclasses
 -dontskipnonpubliclibraryclassmembers
 -dontpreverify
 -verbose
 -printmapping proguardMapping.txt
 -optimizations !code/simplification/cast,!field/*,!class/merging/*
 -keepattributes *Annotation*,InnerClasses
 -keepattributes Signature
 -keepattributes SourceFile,LineNumberTable
 #忽略警告，这一行挺重要，不加的话某些情况下打包会报错中断
 -ignorewarnings
 #----------------------------------------------------------------------------
 -keep public class * implements com.shuaijie.permissionproxy.PermissionProxyInterface
 #---------------------------------默认保留区---------------------------------
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Application
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference
 -keep public class * extends android.view.View
 -keep public class com.android.vending.licensing.ILicensingService
 -keep class android.support.** {*;}
-keep class com.google.**{*;}
# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
 -keepclasseswithmembernames class * {
     native <methods>;
 }
 -keepclassmembers class * extends android.app.Activity{
     public void *(android.view.View);
 }
 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }
 -keep public class * extends android.view.View{
     *** get*();
     void set*(***);
     public <init>(android.content.Context);
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }
 -keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }
 -keep class * implements android.os.Parcelable {
   public static final android.os.Parcelable$Creator *;
 }
 -keepclassmembers class * implements java.io.Serializable {
     static final long serialVersionUID;
     private static final java.io.ObjectStreamField[] serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
 }
 -keep class **.R$* {
  *;
 }
 -keepclassmembers class * {
     void *(**On*Event);
 }
 # 保留本地native方法不被混淆
 -keepclasseswithmembernames class * {
     native <methods>;
 }
 #----------------------------------------------------------------------------

 #---------------------------------webview------------------------------------
 -keepclassmembers class fqcn.of.javascript.interface.for.Webview {
    public *;
 }
 -keepclassmembers class * extends android.webkit.WebViewClient {
     public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
     public boolean *(android.webkit.WebView, java.lang.String);
 }
 -keepclassmembers class * extends android.webkit.WebViewClient {
     public void *(android.webkit.WebView, jav.lang.String);
 }