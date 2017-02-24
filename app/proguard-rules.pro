# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in e:\IDE\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#【优化处理，优化系数为5。不做优化-dontoptimize】
-optimizationpasses 5
#【优化方式】
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#【混淆时不产生混合的类名】
-dontusemixedcaseclassnames

#【不忽略非公共的库类】
-dontskipnonpubliclibraryclasses

#【不做预校验】
-dontpreverify

#【处理过程中显示更多运行信息】
-verbose

#【保护使用反射机制的类】
-keepattributes *Annotation*

#【保护使用泛型的类】
-keepattributes Signature

-keepattributes InnerClasses

-keepattributes InnerClasses,EnclosingMethod

#【保护使用本地方法的类，即使用NDK相关类】
-keepclasseswithmembernames class * {
    native <methods>;
}

#【保护枚举类】
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#【保护Parcelable类】
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

#【保护Serializable类】
-keepnames class * implements java.io.Serializable

# 保留Serializable 序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
   static final long serialVersionUID;
   private static final java.io.ObjectStreamField[] serialPersistentFields;
   !static !transient <fields>;
   private void writeObject(java.io.ObjectOutputStream);
   private void readObject(java.io.ObjectInputStream);
   java.lang.Object writeReplace();
   java.lang.Object readResolve();
}
# 对于带有回调函数onXXEvent的，不能混淆
-keepclassmembers class * {
    void *(**On*Event);
}


#=========================Android API相关====start==================================
#【保护Android相关组件类】
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.v8.**

# 保留Activity中的方法参数是view的方法，
# 从而我们在layout里面编写onClick就不会影响
-keepclassmembers class * extends android.app.Activity {
    public void * (android.view.View);
}
# 保留自定义控件(继承自View)不能被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(***);
    *** get* ();
}

################gson##################
-keep class com.google.gson.** {*;}
#-keep class com.google.**{*;}
-keep class com.google.gson.stream.** { *; }
-keep class com.google.** {
    <fields>;
    <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-dontwarn com.google.gson.**

 #不混淆R类
-keep public class com.tianchuang.ihome_b.R$*{
    public static final int *;
}
#【保护R资源索引类】
-keepclassmembers class **.R$* {
    public static <fields>;
}

#权限工具类不参与混淆
-keep class com.tianchuang.ihome_b.permission.** { *; }

-keepclassmembers class ** {
    @com.tianchuang.ihome_b.permission.OnMPermissionGranted <methods>;
}
-keepclassmembers class ** {
    @com.tianchuang.ihome_b.permission.OnMPermissionDenied <methods>;
}

-keepclassmembers class ** {
    @com.tianchuang.ihome_b.permission.OnMPermissionNeverAskAgain <methods>;
}
################common###############
-keep class com.tianchuang.ihome_b.bean.** { *; } #实体类不参与混淆
-keep class com.tianchuang.ihome_b.view.** { *; } #自定义控件不参与混淆
#【eventbus】
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#【rxJava】
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
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# baseRecyclerAdapterHelper
-keep class com.chad.library.adapter.** {*;}
#【litepal】
-keep class org.litepal.** { *; }
-keep class * extends org.litepal.crud.DataSupport{*;}
