package com.example.skyreserve.app

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.skyreserve.database.room.dao.UserAccountDao
import com.example.skyreserve.database.room.entity.UserAccount
import com.example.skyreserve.repository.AuthRepository
import com.example.skyreserve.repository.UserAccountRepository
import com.example.skyreserve.Util.LocalSessionManager
// import com.example.skyreserve.Util.DatabaseInitializer
import com.example.skyreserve.Util.UserInteractionLogger
import com.example.skyreserve.database.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton


@Module // Indicates that this class is a Dagger module
@InstallIn(SingletonComponent::class) // Specifies the component where this module will be installed
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RoomDatabase::class.java,
            RoomDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : androidx.room.RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Perform operations when the database is created (if needed)
                    CoroutineScope(Dispatchers.IO).launch {
                        // Populate the database in a background thread
                        val userDao = provideDatabase(context).userAccountDao()
                        // Insert initial data
                        val newUserAccount = UserAccount(
                            emailAddress = "john_doe@gmail.com",
                            password = "secretpassword"
                        )
                        userDao.insertUserAccount(newUserAccount)
                    }
                }
            })
            .build()
    }

    @Provides
    fun provideUserAccountDao(database: RoomDatabase): UserAccountDao {
        return database.userAccountDao()
    }


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

    @Provides
    @Singleton
    fun provideUserAccountRepository(database: RoomDatabase): UserAccountRepository {
        return UserAccountRepository(database.userAccountDao())
    }

    @Provides
    @Singleton
    fun provideUserInteractionLogger(@ApplicationContext context: Context): UserInteractionLogger {
        return UserInteractionLogger(context)
    }
}

