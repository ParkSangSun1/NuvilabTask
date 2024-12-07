package com.pss.nuvilabtask.common

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RequestRetryQueue {

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