package com.amaris.data.di

import android.content.Context
import android.os.Debug
import androidx.room.Room
import com.amaris.data.local.db.MovieDatabase
import com.amaris.data.local.db.MovieRoomDatabase
import com.amaris.data.local.db.RoomTransactionRunner
import com.amaris.data.local.db.TransactionRunner
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieRoomDatabase {
        val builder =
            Room.databaseBuilder(context, MovieRoomDatabase::class.java, "movie_db")
                .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) builder.allowMainThreadQueries()

        return builder.build()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object DAOModule {

    @Provides
    fun provideMovieDao(db: MovieRoomDatabase) = db.movieDao()

    @Provides
    fun provideMovieRemoteKeysDao(db: MovieRoomDatabase) = db.movieRemoteKeysDao()
}


@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModuleBinds {
    @Binds
    abstract fun bindMovieDatabase(db: MovieRoomDatabase): MovieDatabase

    @Singleton
    @Binds
    abstract fun provideTransactionRunner(runner: RoomTransactionRunner): TransactionRunner
}