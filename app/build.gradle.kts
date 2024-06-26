import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    id("com.google.android.gms.oss-licenses-plugin")
}


val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

val KAKAO_OAUTH_HOST: String = properties.getProperty("kakao_oauth_host")     // 카카오 로그인 API 호스트



android {
    namespace = "com.cokiri.coinkiri"

    compileSdk = 34

    defaultConfig {
        applicationId = "com.coin.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            properties["kakao_native_app_key"] as String
        )

        buildConfigField(
            "String",
            "LOCAL_URL",
            properties["local_url"] as String
        )

        resValue(
            "string",
            "kakao_oauth_host",
            KAKAO_OAUTH_HOST
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    /* implementation */
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material1)
    implementation(libs.androidx.compose.material3)

    // MPAndroidChart
    implementation(libs.mpAndroidChart)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)

    // Moshi
    implementation(libs.moshi.core)
    implementation(libs.moshi.kotlin)

    // 카카오 로그인 API 모듈
    implementation(libs.kakao.sdk.v2.user)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation("com.google.accompanist:accompanist-swiperefresh:0.24.13-rc")
    implementation("io.coil-kt:coil:2.1.0")


    /* testImplementation */
    testImplementation(libs.junit)


    /* androidTestImplementation */
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)


    /* debugImplementation */
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}