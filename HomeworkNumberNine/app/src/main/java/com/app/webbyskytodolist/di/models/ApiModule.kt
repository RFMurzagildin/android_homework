package com.app.webbyskytodolist.di.models

import android.content.Context
import com.app.webbyskytodolist.data.remote.ApiService
import com.app.webbyskytodolist.data.remote.RemoteDataSource
import com.app.webbyskytodolist.data.repositories.AuthRepositoryImpl
import com.app.webbyskytodolist.data.repositories.TaskRepositoryImpl
import com.app.webbyskytodolist.data.repositories.UserRepositoryImpl
import com.app.webbyskytodolist.domain.repositories.AuthRepository
import com.app.webbyskytodolist.domain.repositories.TaskRepository
import com.app.webbyskytodolist.domain.repositories.UserRepository
import com.app.webbyskytodolist.domain.usecases.GetSavedTokenUseCase
import com.app.webbyskytodolist.domain.usecases.GetTasksByUserAndDateUseCase
import com.app.webbyskytodolist.domain.usecases.IsUserLoggedInUseCase
import com.app.webbyskytodolist.domain.usecases.LogoutUserUseCase
import com.app.webbyskytodolist.domain.usecases.SaveTokenUseCase
import com.app.webbyskytodolist.domain.usecases.ValidateCredentialsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    @Provides
    fun provideTaskRepository(remoteDataSource: RemoteDataSource): TaskRepository {
        return TaskRepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideUserRepository(remoteDataSource: RemoteDataSource): UserRepository {
        return UserRepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideAuthRepository(@ApplicationContext context: Context): AuthRepository {
        return AuthRepositoryImpl(context)
    }

    @Provides
    fun provideSaveTokenUseCase(authRepository: AuthRepository): SaveTokenUseCase {
        return SaveTokenUseCase(authRepository)
    }

    @Provides
    fun provideLogoutUserUseCase(authRepository: AuthRepository): LogoutUserUseCase {
        return LogoutUserUseCase(authRepository)
    }

    @Provides
    fun provideIsUserLoggedInUseCase(authRepository: AuthRepository): IsUserLoggedInUseCase {
        return IsUserLoggedInUseCase(authRepository)
    }

    @Provides
    fun provideGetSavedTokenUseCase(authRepository: AuthRepository): GetSavedTokenUseCase {
        return GetSavedTokenUseCase(authRepository)
    }

    @Provides
    fun provideValidateCredentialsUseCase(): ValidateCredentialsUseCase {
        return ValidateCredentialsUseCase()
    }

    @Provides
    fun getTasksByUserAndDateUseCase(taskRepository: TaskRepository): GetTasksByUserAndDateUseCase {
        return GetTasksByUserAndDateUseCase(taskRepository);
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesPostService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

}