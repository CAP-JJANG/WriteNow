plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.writenow"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.writenow"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    // retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson 변환기
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // TedPermission
    implementation("io.github.ParkSangGwon:tedpermission-normal:3.3.0")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")

    implementation("androidx.fragment:fragment-ktx:1.6.1")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")


    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("com.google.android.gms:play-services-wearable:18.1.0")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.wear:wear:1.2.0")
}