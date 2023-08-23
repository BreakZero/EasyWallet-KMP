# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn kotlinx.serialization.KSerializer
-dontwarn kotlinx.serialization.Serializable

-keep class java.util.concurrent.ConcurrentHashMap { *; }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    public static final synthetic <fields>;
}
