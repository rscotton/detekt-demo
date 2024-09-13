plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    // Detekt plugin
    id("io.gitlab.arturbosch.detekt") version("1.23.7")
}

android {
    namespace = "com.bytymoxy.detektdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bytymoxy.detektdemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Detekt configuration
    detekt {
        toolVersion = "1.23.7"
        autoCorrect = true
        buildUponDefaultConfig = true
        config.setFrom("config/detekt/detekt.yml")
        source.setFrom("src/main/java", "src/androidTest/java", "src/test/java")
        // TODO Test this configuration on your project! Maybe it helps improve the run time, maybe not
        // parallel = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Detekt plugins
    /**
     * Adds [formatting rules from KtLint and auto-corrects the code for some rule violations:
     * https://detekt.dev/docs/rules/formatting
     */
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")
}