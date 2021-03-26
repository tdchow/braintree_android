package com.braintreepayments.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.app.Activity.RESULT_OK;

class CardinalActivityResultContract extends ActivityResultContract<ThreeDSecureResult, CardinalActivityResult> {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, ThreeDSecureResult input) {
        Bundle extras = new Bundle();
        extras.putParcelable(ThreeDSecureActivity.EXTRA_THREE_D_SECURE_RESULT, input);

        Intent intent = new Intent(context, ThreeDSecureActivity.class);
        intent.putExtras(extras);
        return intent;
    }

    @Override
    public CardinalActivityResult parseResult(int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            return CardinalActivityResult.fromIntent(data);
        }
        return null;
    }
}
