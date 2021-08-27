package com.braintreepayments.api

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@InternalCoroutinesApi
suspend fun CardClient.tokenize(card: Card) =
    suspendCancellableCoroutine<CardNonce> { continuation ->
        tokenize(card) { cardNonce, error ->
            if (isActive) {
                cardNonce?.let {
                    continuation.resume(it)
                }
                error?.let {
                    continuation.resumeWithException(it)
                }
            }
        }
    }