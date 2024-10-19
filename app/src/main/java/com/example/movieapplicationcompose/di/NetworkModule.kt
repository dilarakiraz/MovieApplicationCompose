package com.example.movieapplicationcompose.di

import com.example.movieapplicationcompose.domain.ApiInterface
import com.example.movieapplicationcompose.domain.OpenAiService
import com.example.movieapplicationcompose.utils.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${Util.ChatApiKey}")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    @MovieRetrofit
    fun provideMovieRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Util.BaseMovie)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @ApiRetrofit
    fun provideOpenAiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Util.BaseAl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(@MovieRetrofit movieRetrofit: Retrofit): ApiInterface {
        return movieRetrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideOpenAiService(@ApiRetrofit retrofit: Retrofit): OpenAiService {
        return retrofit.create(OpenAiService::class.java)
    }
}

//   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