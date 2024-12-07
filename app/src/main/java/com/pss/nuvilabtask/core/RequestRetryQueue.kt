package com.pss.nuvilabtask.core

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object RequestRetryQueue {

    private val queue = mutableListOf<suspend () -> Unit>()
    private val mutex = Mutex()

    suspend fun addRequest(request: suspend () -> Unit) {
        mutex.withLock {
            queue.add(request)
        }
    }

    suspend fun retryAll() {
        mutex.withLock {
            val requests = queue.toList()
            queue.clear()
            requests.forEach { it() }
        }
    }
}