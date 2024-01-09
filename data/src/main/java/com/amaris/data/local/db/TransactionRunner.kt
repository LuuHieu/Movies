package com.amaris.data.local.db

interface TransactionRunner {
    suspend operator fun <T> invoke(action: suspend () -> T): T
}
