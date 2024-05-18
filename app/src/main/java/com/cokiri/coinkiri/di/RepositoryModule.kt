package com.cokiri.coinkiri.di

import com.cokiri.coinkiri.data.PreferencesManager
import com.cokiri.coinkiri.data.remote.api.AuthApi
import com.cokiri.coinkiri.data.repository.KakaoLoginRepositoryImpl
import com.cokiri.coinkiri.data.repository.UserRepositoryImpl
import com.cokiri.coinkiri.domain.repository.KakaoLoginRepository
import com.cokiri.coinkiri.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideKakaoLoginRepository(
        kakaoLoginRepositoryImpl: KakaoLoginRepositoryImpl
    ): KakaoLoginRepository{
        return kakaoLoginRepositoryImpl
    }


    @Provides
    @Singleton
    fun provideUserRepository(
        authApi: AuthApi,
        preferencesManager: PreferencesManager
    ): UserRepository {
        return UserRepositoryImpl(authApi, preferencesManager)
    }
}