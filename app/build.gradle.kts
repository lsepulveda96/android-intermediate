plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // para que funcione dagger hilt
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.luciano.horoscapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.luciano.horoscapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        // Configuracion de los tipos de build
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "lucianoName", "HoroscApp")

            buildConfigField("String", "BASE_URL", "\"https://newastro.vercel.app/\"") // url base de la api. Deberia cambiar de acuerdo a si es produccion o desarrollo
        }
        getByName("debug"){
            isDebuggable = true
            // para cambiar el nombre de la app en el debug// podria tambien guardar una apiKey, para produccion y desarrollo
            resValue("string", "lucianoName", "[DEBUG] HoroscApp")
            buildConfigField("String", "BASE_URL", "\"https://newastro-debug.vercel.app/\"") // url base de la api. Deberia cambiar de acuerdo a si es produccion o desarrollo
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
    kotlin{
        jvmToolchain(11)
    }
}

dependencies {

    val navVersion = "2.7.1"

    //NavComponent
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // libreria para inyeccion de dependencias
    implementation("com.google.dagger:hilt-android:2.48")
    // para usar hilt en el proyecto, necesita kapt para autogenerar codigo, proveedores de dependencias
    kapt("com.google.dagger:hilt-compiler:2.48")

    // Retrofit, para hacer llamadas a la api
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // libreria para convertir objetos a json y viceversa
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // ofrece una forma de interceptar las peticiones y respuestas de la api (mostrar mas info de la peticion)
    implementation ("com.squareup.okhttp3:logging-interceptor:4.3.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}