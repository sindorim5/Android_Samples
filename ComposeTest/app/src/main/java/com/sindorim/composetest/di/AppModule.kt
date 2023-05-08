package com.sindorim.composetest.di

import com.sindorim.composetest.BASE_URL
import com.sindorim.composetest.BuildConfig
import com.sindorim.composetest.domain.api.SwApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpInterceptorClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpInterceptorApi

class OkHttpInterceptor @Inject constructor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        proceed(newRequest)
    }
} // End of okHttpInterceptor class

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @OkHttpInterceptorClient
    fun provideInterceptorOkHttpClient(okHttpInterceptor: OkHttpInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .addInterceptor(okHttpInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()
    } // End of provideInterceptorOkHttpClient

    @Provides
    fun provideSwApi(@OkHttpInterceptorClient interceptor: OkHttpClient): SwApi {
        return Retrofit.Builder()
            .client(interceptor)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SwApi::class.java)
    } // End of provideSwApi

} // End of AppModule