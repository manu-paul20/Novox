# Novox R8/ProGuard Rules

# --- Kotlin Serialization ---
# Keep the serializable classes and their serializers
-keepattributes *Annotation*, InnerClasses
-keepclassmembers class com.manu.novox.** {
    *** Companion;
    *** $serializer;
}
-keep @kotlinx.serialization.Serializable class com.manu.novox.** { *; }
-keepclassmembers class com.manu.novox.** {
    @kotlinx.serialization.SerialName <fields>;
}

# --- Firebase Realtime Database ---
# Firebase uses reflection to map data to these classes.
# We need to keep the classes, their fields, and the default no-arg constructors.
-keepattributes Signature
-keep class com.manu.novox.data.local.entity.** {
    public <fields>;
    public <init>(...);
}
-keep class com.manu.novox.domain.model.** {
    public <fields>;
    public <init>(...);
}

# --- Hilt / Dagger ---
# Hilt usually provides its own rules, but these ensure reflection-based lookups work.
-keepattributes *Annotation*
-keep class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}

# --- Room ---
# Room handles most rules itself, but keeping the entities is safe for reflection/debugging.
-keep class com.manu.novox.data.local.entity.** { *; }

# --- Navigation Component ---
# Needed for type-safe navigation with Kotlin Serialization
-keep class com.manu.novox.core.navigation.Routes** { *; }

# --- General ---
# Keep line numbers for better crash reports
-keepattributes SourceFile, LineNumberTable
-renamesourcefileattribute SourceFile
