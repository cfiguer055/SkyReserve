package com.example.skyreserve.UI

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.skyreserve.Database.Dao.UserAccountDao
import com.example.skyreserve.Database.SkyReserveDatabase
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.Util.LocalSessionManager
// import com.example.skyreserve.Util.DatabaseInitializer
import com.example.skyreserve.Util.UserInteractionLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Indicates that this class is a Dagger module
@InstallIn(SingletonComponent::class) // Specifies the component where this module will be installed
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SkyReserveDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SkyReserveDatabase::class.java,
            "skyreserve_database"
        ).build()
    }


    @Provides
    fun provideUserAccountDao(database: SkyReserveDatabase): UserAccountDao {
        return database.userAccountDao()
    }

    @Provides
    @Singleton
    fun provideUserInteractionLogger(@ApplicationContext context: Context): UserInteractionLogger {
        return UserInteractionLogger(context)
    }

//    @Provides // Indicates that this function provides a dependency
//    fun provideAuthRepository(userAccountDao: UserAccountDao): AuthRepository {
//        return AuthRepository(userAccountDao)
//    }

    @Provides // Indicates that this function provides a dependency
    @Singleton
    fun provideLocalSessionManager(@ApplicationContext context: Context): LocalSessionManager {
        return LocalSessionManager(context) // Creates and returns an instance of LocalSessionManager
    }


    @Provides
    @Singleton
    fun provideAuthRepository(userAccountDao: UserAccountDao): AuthRepository {
        return AuthRepository(userAccountDao)
    }
}