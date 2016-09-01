
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

-dontwarn org.greenrobot.greendao.database.**
-dontwarn org.greenrobot.greendao.rx.**
