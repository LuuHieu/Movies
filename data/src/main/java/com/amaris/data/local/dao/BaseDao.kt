package com.amaris.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import com.amaris.data.local.entities.BaseEntity

abstract class BaseDao<in E: BaseEntity> {
    @Insert
    abstract suspend fun insert(entity: E): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(vararg entity: E)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: List<E>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: E)

    @Delete
    abstract suspend fun delete(entity: E)

//    @Transaction
//    open suspend fun withTransaction(tx: suspend () -> Unit) = tx.invoke()
//
//    suspend fun upsert(entity: E): Long {
//        if (0L == entity.) return insert(entity)
//        update(entity)
//        return entity.id
//    }
//
//    @Transaction
//    open suspend fun upsert(vararg entity: E) {
//        entity.forEach { upsert(it) }
//    }
//
//    @Transaction
//    open suspend fun upsert(entity: List<E>) {
//        entity.forEach { upsert(it) }
//    }
}