# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/hua/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can saveMedicineRemind the include path and order by changing the proguardFiles
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

# 指定不使用大小写区分混淆后的类名
-dontusemixedcaseclassnames
# 指定混淆的时候不忽略jar中非public的类
-dontskipnonpubliclibraryclasses
# 混淆过程中输出更详细的信息
-verbose

# 混淆的时候不执行优化步骤与预验证步骤，Dex自己会做一些相对应的优化
#-dontoptimize
#-dontpreverify

# 确保在jdk5.0以及更高版本能够使用泛型
-keepattributes Signature
# 不混淆异常,让编译器知道方法会抛出哪种异常
-keepattributes Exceptions
#不混淆注解
-keepattributes *Annotation*
#-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# 不混淆本地方法,includedescriptorclasses选项指定不重命名方法返回类型与参数类型
-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

# 确保views的动画能够正常工作
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# 不混淆Activity里面带view参数的方法，确保XML的onclick属性能正常使用
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# 不混淆枚举类型
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


# 对实现了Parcelable接口的所有类的类名不进行混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#不混淆Serializable的子类
# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 不混淆R.java文件
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 忽略support包新版本警告
-keep class android.support.** { *; }
-dontwarn android.support.**

# 不混淆第三方jar包
-dontshrink
-dontoptimize

# Keep GSON stuff
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
# Gson specific classes
-keep class com.google.gson.stream.** { *; }

# 不混淆ILicensingService类，单向的进程间通讯接口，这个接口的许可检查请求来自google play客户端
#-keep public class com.google.vending.licensing.ILicensingService
#-keep public class com.android.vending.licensing.ILicensingService

# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

#appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
#环信
-keep class com.easemob.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.easemob.**
#2.0.9后的不需要加下面这个keep
#-keep class org.xbill.DNS.** {*;}
#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils
-keep class com.easemob.chatuidemo.utils.SmileUtils {*;}
#注意前面的包名，如果把这个类复制到自己的项目底下，比如放在com.example.utils底下，应该这么写(实际要去掉#)
#-keep class com.example.utils.SmileUtils {*;}
#如果使用easeui库，需要这么写
-keep class com.easemob.easeui.utils.EaseSmileUtils {*;}

#2.0.9后加入语音通话功能，如需使用此功能的api，加入以下keep
-dontwarn ch.imvs.**
-dontwarn org.slf4j.**
-keep class org.ice4j.** {*;}
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}
-keep class internal.** {*;}
-keep class net.** {*;}
-keep class org.** {*;}

-keep class rx.** { *; }
#realm
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**

# RxJava 0.21
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}

# for RxJava:
 -dontwarn sun.misc.Unsafe
 -dontwarn java.lang.invoke.*

  -keep class android.support.v4.** { *; }
  -keep interface android.support.v4.** { *; }
  -keep class android.support.v7.** { *; }

#retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-dontwarn butterknife.internal.**

#Umeng
-keep class com.umeng.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-dontwarn com.umeng.**

#flyco
-keep class com.flyco.** { *; }
-keep class com.github.** { *; }
-keep class com.zhy.** { *; }
-keep class com.timehop.** { *; }
-keep class me.iwf.** { *; }
-keep class com.nineoldandroids.** { *; }

#fullway health device
-keep class cn.com.contec.jar.cmssxt.** { *;}
-keep class com.contec.jar.** { *;}
-dontwarn cn.com.contec.jar.cmssxt.**
-dontwarn com.contec.jar.**

-keep class com.lyft.** {*;}
-dontwarn com.lyft.**

#ping++
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}

-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}

-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}

-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}

-dontwarn  com.unionpay.**
-keep class com.unionpay.** {*;}

-dontwarn com.pingplusplus.**
-keep class com.pingplusplus.** {*;}

-dontwarn com.baidu.**
-keep class com.baidu.** {*;}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

#entity
-keep class com.wonders.xlab.patient.mvp.entity.** {*;}

#common
-keep class com.wonders.xlab.common.** { *; }
-keep class im.hua.utils.** { *; }
-keep class im.hua.uikit.** { *; }

#base
-keep class im.hua.library.base.** { *; }