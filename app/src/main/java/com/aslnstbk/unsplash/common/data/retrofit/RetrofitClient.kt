package com.aslnstbk.unsplash.common.data.retrofit

import android.content.Context
import com.aslnstbk.unsplash.BuildConfig
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitClient {
    private const val ACCESS_KEY = BuildConfig.ACCESS_KEY
    private const val BASE_URL = BuildConfig.BASE_URL

    private fun httpClient(context: Context) = OkHttpClient.Builder()
        .addInterceptor {chain ->
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url()

        val url: HttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("client_id", ACCESS_KEY)
            .build()

        val request = original.newBuilder().url(url).build()

        chain.proceed(request)
    }

    fun instance(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient(context).build())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}