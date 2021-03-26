package com.braintreepayments.demo;

import androidx.annotation.NonNull;

import com.braintreepayments.api.Authorization;

public interface AuthorizationCallback {
    void onResult(@NonNull Authorization authorization);
}
