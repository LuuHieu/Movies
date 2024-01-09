package com.amaris.data.local.db

import androidx.room.withTransaction
import javax.inject.Inject

class RoomTransactionRunner @Inject constructor(private val db: MovieRoomDatabase) :
    TransactionRunner {
    override suspend fun <T> invoke(action: suspend () -> T): T {
        return db.withTransaction { action.invoke() }
    }
}
