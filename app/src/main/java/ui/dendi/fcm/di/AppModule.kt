package ui.dendi.fcm.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.fcm.data.db.FirebaseMessageDatabase
import ui.dendi.fcm.data.db.MessageDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseMessagingDatabase(app: Application): FirebaseMessageDatabase {
        return Room.databaseBuilder(
            app,
            FirebaseMessageDatabase::class.java,
            FirebaseMessageDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMessageDao(database: FirebaseMessageDatabase) : MessageDao {
        return database.messageDao
    }
}