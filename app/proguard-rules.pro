-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions

-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep,allowobfuscation,allowshrinking class kotlinx.coroutines.flow.Flow

-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

-keep class retrofit2.converter.gson.GsonConverterFactory { *; }
-dontwarn retrofit2.converter.gson.GsonConverterFactory

-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }

-keep class com.pss.nuvilabtask.data.model.** { *; }
-keep class com.pss.nuvilabtask.model.** { *; }