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

# ----------------------------------------------------------------
# Help Resources:
# - https://proandroiddev.com/android-default-proguard-rules-guide-20058ba7a486
# ----------------------------------------------------------------

# ----------------------------------------------------------------
# Models
# ----------------------------------------------------------------
# Nothing individual so far...

# ----------------------------------------------------------------
# Moshi
# ----------------------------------------------------------------
# Note: Moshi contains minimally required rules for its own internals to work without need for consumers to embed their own.

# ----------------------------------------------------------------
# Parcelable
# ----------------------------------------------------------------
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# ----------------------------------------------------------------
# Maps
# ----------------------------------------------------------------
# Link: https://developers.google.com/maps/documentation/android-sdk/v3-client-migration
-keep,allowoptimization class com.google.android.libraries.maps.** { *; }

# ----------------------------------------------------------------
# Retrofit
# Source: https://github.com/square/retrofit/blob/master/retrofit/src/main/resources/META-INF/proguard/retrofit2.pro
# ----------------------------------------------------------------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep generic signature of Call (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call

# ----------------------------------------------------------------
# OkHttp
# Source: https://square.github.io/okhttp/r8_proguard/
# ----------------------------------------------------------------
# Note: If you use OkHttp as a dependency in an Android project which uses R8 as a default compiler you don’t have to do anything.

# ----------------------------------------------------------------
# okio
# Source: https://square.github.io/okio/#r8-proguard
# ----------------------------------------------------------------
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

