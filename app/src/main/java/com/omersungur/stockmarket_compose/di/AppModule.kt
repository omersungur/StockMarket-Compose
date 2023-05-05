package com.omersungur.stockmarket_compose.di

import android.app.Application
import androidx.room.Room
import com.omersungur.stockmarket_compose.data.local.StockDatabase
import com.omersungur.stockmarket_compose.data.remote.StockAPI
import com.omersungur.stockmarket_compose.util.Constants.BASE_URL
import com.omersungur.stockmarket_compose.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application) : StockDatabase {
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

}