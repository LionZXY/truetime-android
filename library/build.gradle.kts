import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// remove this on upgrading android gradle plugin to 8
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.maven.publish)
  id("maven-publish")
}

android {
  namespace = "com.instacart.truetime"

  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    aarMetadata { minCompileSdk = libs.versions.compileSdk.get().toInt() }
  }

  buildTypes { getByName("release") { isMinifyEnabled = false } }

  buildFeatures { buildConfig = false }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }

  kotlinOptions {
    jvmTarget = "21"
  }
}

dependencies { api(libs.kotlinx.coroutines.core) }

mavenPublishing {
  coordinates("uk.kulikov", "truetime", libs.versions.trueTime.get())

  pom {
    name.set("Truetime for Android by instacart")
    description.set("Android NTP time library. Get the true current time impervious to device clock time changes")
    inceptionYear.set("2025")
    url.set("https://github.com/LionZXY/truetime-android")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
    developers {
      developer {
        id.set("lionzxy")
        name.set("LionZXY")
        url.set("https://github.com/LionZXY/")
      }
      developer {
        id.set("instacart")
        name.set("Instacart")
        url.set("https://github.com/instacart/")
      }
    }
    scm {
      url.set("https://github.com/LionZXY/truetime-android")
      connection.set("scm:git:git://github.com/LionZXY/truetime-android.git")
      developerConnection.set("scm:git:ssh://git@github.com/LionZXY/truetime-android.git")
    }
  }

  publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
  signAllPublications()
}
