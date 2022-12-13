package de.muhameddhouibi.gpstracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.muhameddhouibi.gpstracker.db.AppDatabase
import de.muhameddhouibi.gpstracker.utils.Constants.DATABASE_NAME

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTrackingDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideTrackerDao(db: AppDatabase) = db.getTrackerDao()
}