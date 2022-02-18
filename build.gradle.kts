import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    kotlin("kapt") version "1.3.70"
    id("org.jetbrains.compose") version "1.0.0-alpha3"
}



repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation("androidx.annotation:annotation:1.3.0-alpha01")
    implementation (compose.desktop.currentOs)

    //Сетевое взаимодействие
    val retrofit_version = "2.9.0"
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation ("com.squareup.retrofit2:converter-moshi:$retrofit_version")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:$retrofit_version")
    implementation ("com.squareup.retrofit2:converter-scalars:$retrofit_version")
    implementation ("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation ("com.squareup.moshi:moshi-adapters:1.12.0")
    implementation ("com.github.kittinunf.fuel:fuel:1.12.1")
    implementation ("com.github.kittinunf.fuel:fuel-rxjava:1.12.1")
    implementation ("com.github.kittinunf.fuel:fuel-gson:1.12.1")
    implementation ("commons-io:commons-io:2.11.0")
    implementation ("com.github.pengrad:java-telegram-bot-api:5.2.0")
    implementation("com.notkamui.libs:kourrier:0.2.2")
    implementation("com.sun.mail:javax.mail:1.6.2")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.12.0")
    implementation("org.jetbrains.compose.material:material-icons-extended:1.0.0-alpha1")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.6")
    //Хранение данных
    implementation(Badoo.Reaktive.reaktive)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation ("com.squareup.okhttp3:okhttp-tls:4.9.3")
    implementation ("com.github.pengrad:java-telegram-bot-api:5.7.0")
}

object Badoo {
    object Reaktive {
        private const val VERSION = "1.1.22"
        const val reaktive = "com.badoo.reaktive:reaktive:$VERSION"
        const val reaktiveTesting = "com.badoo.reaktive:reaktive-testing:$VERSION"
        const val utils = "com.badoo.reaktive:utils:$VERSION"
        const val coroutinesInterop = "com.badoo.reaktive:coroutines-interop:$VERSION"
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "alice-local-ws-control"
            linux {
                packageVersion = "9.10.3.dfsg.P4"
            }
            macOS {
                packageVersion = "1.0.0"
            }
            windows {
                packageVersion = "1.0.0"
            }
        }
    }
}