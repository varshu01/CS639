plugins {
    id("com.android.application") version "8.1.1" apply false
}
buildscript {
    repositories {
        google()
    }
    dependencies {
        val navVersion = "2.7.4"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}