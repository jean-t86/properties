package me.tadebois.properties.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.tadebois.properties.api.PropertyApi
import me.tadebois.properties.api.PropertyApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PropertyApiModule {
    @Provides
    @Singleton
    fun providePropertyApi(apiService: PropertyApiService): PropertyApi {
        return PropertyApi(apiService)
    }

    @Provides
    @Singleton
    fun provideNedApiService(): PropertyApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://f213b61d-6411-4018-a178-53863ed9f8ec.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(PropertyApiService::class.java)
    }
}
