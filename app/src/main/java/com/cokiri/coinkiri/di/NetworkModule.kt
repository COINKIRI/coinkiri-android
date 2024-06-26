package com.cokiri.coinkiri.di

import android.content.Context
import android.content.SharedPreferences
import com.cokiri.coinkiri.BuildConfig
import com.cokiri.coinkiri.data.remote.api.AnalysisApi
import com.cokiri.coinkiri.data.remote.service.auth.AuthInterceptor
import com.cokiri.coinkiri.util.JsonParser
import com.cokiri.coinkiri.data.remote.service.preferences.PreferencesManager
import com.cokiri.coinkiri.data.remote.api.AuthApi
import com.cokiri.coinkiri.data.remote.api.CoinApi
import com.cokiri.coinkiri.data.remote.api.CommentApi
import com.cokiri.coinkiri.data.remote.api.LikeApi
import com.cokiri.coinkiri.data.remote.api.PostApi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * SharedPreferences를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("coinkiri_prefs", Context.MODE_PRIVATE)
    }


    /**
     * PreferencesManager를 제공하는 함수
     */
    @Provides
    @Singleton
    fun providePreferencesManager(sharedPreferences: SharedPreferences): PreferencesManager {
        return PreferencesManager(sharedPreferences)
    }


    /**
     * AuthInterceptor를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideAuthInterceptor(preferencesManager: PreferencesManager): AuthInterceptor {
        return AuthInterceptor(preferencesManager)
    }


    /**
     * JsonParser를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideJsonParser(moshi: Moshi): JsonAdapter<JsonParser> {
        return moshi.adapter(JsonParser::class.java)
    }


    /**
     * Moshi를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }


    /**
     * HttpLoggingInterceptor를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
        }
    }


    /**
     * OkHttpClient를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }


    /**
     * Retrofit을 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.LOCAL_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }


    /**
     * AuthApi를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }


    /**
     * CoinApi를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideCoinApi(retrofit: Retrofit): CoinApi {
        return retrofit.create(CoinApi::class.java)
    }

    /**
     * PostApi를 제공하는 함수
     */
    @Provides
    @Singleton
    fun providePostApi(retrofit: Retrofit): PostApi {
        return retrofit.create(PostApi::class.java)
    }


    /**
     * CommentApi를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideCommentApi(retrofit: Retrofit): CommentApi {
        return retrofit.create(CommentApi::class.java)
    }


    /**
     * AnalysisApi를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideAnalysisApi(retrofit: Retrofit): AnalysisApi {
        return retrofit.create(AnalysisApi::class.java)
    }


    /**
     * LikeApi를 제공하는 함수
     */
    @Provides
    @Singleton
    fun provideLikeApi(retrofit: Retrofit): LikeApi {
        return retrofit.create(LikeApi::class.java)
    }
}