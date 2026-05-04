# Novox R8/ProGuard Rules

# --- Kotlin Serialization ---
-keepattributes *Annotation*, InnerClasses, EnclosingMethod, Signature
-keepclassmembers class **$serializer {
    public static ** INSTANCE;
}
-keepclassmembers class com.manu.novox.** {
    *** Companion;
    *** $serializer;
}
-keep @kotlinx.serialization.Serializable class com.manu.novox.** { *; }
-keepclassmembers class com.manu.novox.** {
    @kotlinx.serialization.SerialName <fields>;
}
-keepnames class kotlinx.serialization.internal.EnumSerializer

# --- Firebase Realtime Database ---
-keepattributes Signature
-keep class com.manu.novox.data.local.entity.** {
    public <fields>;
    public <init>(...);
}
-keep class com.manu.novox.domain.model.** {
    public <fields>;
    public <init>(...);
}
-keep class com.google.firebase.database.** { *; }

# --- Cloudinary ---
-keep class com.cloudinary.** { *; }
# Cloudinary has optional dependencies on Glide and Picasso
-dontwarn com.bumptech.glide.**
-dontwarn com.squareup.picasso.**

# --- Room ---
-keep class * extends androidx.room.RoomDatabase
-keep class com.manu.novox.data.local.entity.** { *; }

# --- Hilt / Dagger ---
-keep class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}

# --- Navigation Component ---
-keep class com.manu.novox.core.navigation.Routes** { *; }
-keep class * implements com.manu.novox.core.navigation.Routes { *; }

# --- Enums ---
# Keep enum members that might be accessed via reflection (Serialization/Firebase/Room)
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# --- Others / Constants ---
-keep class com.manu.novox.others.** { *; }

# --- General ---
-keepattributes SourceFile, LineNumberTable
-renamesourcefileattribute SourceFile
