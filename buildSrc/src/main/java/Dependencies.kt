object Dependencies {

    // Base
    const val kotlinStbLib =  "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinStdlibVersion}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val constrainLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupportVersion}"
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExtVersion}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}"

    //Cicerone
    const val cicerone = "com.github.terrakok:cicerone:${Versions.ciceroneVersion}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val converterJackson = "com.squareup.retrofit2:converter-jackson:${Versions.retrofitVersion}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"

    // Koin
    const val koin = "org.koin:koin-android:${Versions.koinVersion}"
    const val koinScope = "org.koin:koin-androidx-scope:${Versions.koinVersion}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koinVersion}"

    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    // Coroutines
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"

    // ImageViewer
    const val imageViewer = "com.github.stfalcon:stfalcon-imageviewer:${Versions.imageViewerVersion}"

    // for Staggered layout flow tags
    const val staggeredLayout = "org.apmem.tools:layouts:${Versions.staggeredLayoutVersion}"

    // chucker
    const val chucker = "com.github.chuckerteam.chucker:library:${Versions.chuckerVersion}"

    // viewBinding
    const val viewBinding = "com.github.yogacp:android-viewbinding:${Versions.viewBindingVersion}"

    //Navigation dependencies
    const val navigationRuntime = "androidx.navigation:navigation-runtime-ktx:${Versions.navigationVersion}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
}