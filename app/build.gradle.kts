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
        // Required for SARIF report generation in Github Actions workflow
        basePath = rootProject.projectDir.absolutePath
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
    /**
     * Fork of Twitter Jetpack Compose Rules plugin for Detekt - Detekt does not account for Compose
     * conventions by default:
     * https://github.com/mrmans0n/compose-rules
     */
    detektPlugins("io.nlopez.compose.rules:detekt:0.4.12")
}

/**
 * Optional Git Hooks-supporting Gradle task to install hooks for new developers
 *
 * IMPORTANT: If you want to use Type Resolution, update hooks/pre-push.sh to change how Detekt
 * is run
 */

tasks.create<Copy>("copyGitHooks") {
    description = "Copies the git hooks to the .git folder."
    from("$rootDir/hooks/") {
        include("**/*.sh")
        rename("(.*).sh", "$1")
    }
    into("$rootDir/.git/hooks")
}

// This task is created for machines running OSX
tasks.create<Exec>("installGitHooksMac") {
    description = "Installs the pre-commit git hooks from hooks folder"
    group = "git hooks"
    workingDir = rootDir
    commandLine = listOf("chmod")

    args(listOf("-R", "+x", ".git/hooks/"))
    dependsOn(tasks.getByName("copyGitHooks"))
    doLast {
        logger.info("Git hooks installed successfully.")
        println("Git hooks installed successfully.")
    }
}

// This task is created for machines running Windows
tasks.create<Exec>("installGitHooksWindows") {
    description = "Installs the pre-commit git hooks from hooks folder"
    group = "git hooks"
    workingDir = rootDir
    commandLine = listOf("attrib")

    args(listOf("-R", "+x", ".git/hooks/"))
    dependsOn(tasks.getByName("copyGitHooks"))
    doLast {
        logger.info("Git hooks installed successfully.")
        println("Git hooks installed successfully.")
    }
}
