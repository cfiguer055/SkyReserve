package com.example.skyreserve.app

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.skyreserve.database.room.dao.UserAccountDao
import com.example.skyreserve.database.room.entity.UserAccount
import com.example.skyreserve.repository.AuthRepository
import com.example.skyreserve.repository.UserAccountRepository
import com.example.skyreserve.util.LocalSessionManager
// import com.example.skyreserve.Util.DatabaseInitializer
import com.example.skyreserve.util.UserInteractionLogger
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


/**
 * EN:
 * Dagger module responsible for providing application-level dependencies. This includes
 * Room database, DAOs, repositories, and other utility classes needed across the app.
 *
 * ES:
 * Módulo Dagger responsable de proporcionar dependencias a nivel de aplicación. Esto incluye
 * la base de datos Room, DAOs, repositorios y otras clases de utilidad necesarias en la applicación.
 *
 * IT:
 *
 *
 * */
@Module // Indicates that this class is a Dagger module
@InstallIn(SingletonComponent::class) // Specifies the component where this module will be installed
class AppModule {

    /**
     * EN:
     * Provides a singleton instance of the RoomDatabase. Configures the database with a callback
     * to populate initial data when it is first created.
     *
     * ES:
     * Proporciona un instancia singleton de RoomDatabase. Configura la base de datos con un callback
     * para poblar datos iniciales cuando es crea por primera vez.
     *
     * IT:
     *
     */
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

    /**
     * EN:
     * Provides an instance of UserAccountDao for accessing user account data from the database.
     *
     * ES:
     * Proporciana una instancia de UserAccountDao para acceder a los datos de cuentas de usuario
     * desde la base de datos.
     *
     * IT:
     *
     */
    @Provides
    fun provideUserAccountDao(database: RoomDatabase): UserAccountDao {
        return database.userAccountDao()
    }

    /**
     * EN:
     * Provides a singleton instance of LocalSessionManager to manage local session information.
     *
     * ES:
     * Proporciona una instancia singleton de LocalSessionManager para gestionar la información de la
     * sesión local.
     *
     * IT:
     *
     * */
    @Provides // Indicates that this function provides a dependency
    @Singleton
    fun provideLocalSessionManager(@ApplicationContext context: Context): LocalSessionManager {
        return LocalSessionManager(context) // Creates and returns an instance of LocalSessionManager
    }

    /**
     * EN:
     * Provides a singleton instance of AuthRepository, which handles authentication logic
     * using UserAccountDao.
     *
     * ES:
     * Proporciona una instancia singleton de AuthRepository, que maneja la logica de autenticación
     * utilizando UserAccountDao.
     *
     * IT:
     *
     * */
    @Provides
    @Singleton
    fun provideAuthRepository(userAccountDao: UserAccountDao): AuthRepository {
        return AuthRepository(userAccountDao)
    }

    /**
     * EN:
     * Provides a singleton instance of UserAccountRepository, allowing access to user account
     * data in the database through the UserAccountDao.
     *
     * ES:
     * Proporciona una instancia singleton de UserAccountRepository, permitiendo el acceso a los
     * datos de cuentas de usuario en la base de datos a través de UserAccountDao.
     *
     * IT:
     *
     * */
    @Provides
    @Singleton
    fun provideUserAccountRepository(database: RoomDatabase): UserAccountRepository {
        return UserAccountRepository(database.userAccountDao())
    }

    /**
     * EN:
     * Provides a singleton instance of UserInteractionLogger for tracking user interactions within
     * the app.
     *
     * ES:
     * Proporciona una instancia singleton de UserInteractionLogger para rastrear interacciones
     * del usuario dentro de la aplicación.
     *
     * IT:
     *
     * */
    @Provides
    @Singleton
    fun provideUserInteractionLogger(@ApplicationContext context: Context): UserInteractionLogger {
        return UserInteractionLogger(context)
    }
}

