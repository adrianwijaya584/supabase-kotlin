plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

android {
  namespace = "com.adrian.supabase"
  compileSdk = 34

  packagingOptions {
    exclude("META-INF/versions/9/previous-compilation-data.bin")
  }

  buildFeatures {
    viewBinding= true
  }

  defaultConfig {
    applicationId = "com.adrian.supabase"
    minSdk = 24
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {

  implementation("io.ktor:ktor-client-okhttp:2.3.4")
  implementation(platform("io.github.jan-tennert.supabase:bom:1.3.1"))
  implementation("io.github.jan-tennert.supabase:postgrest-kt:1.3.1")
  implementation("io.github.jan-tennert.supabase:realtime-kt:1.3.1")
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.9.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}