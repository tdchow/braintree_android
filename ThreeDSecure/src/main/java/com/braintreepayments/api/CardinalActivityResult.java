package com.braintreepayments.api;

import android.content.Intent;

import com.cardinalcommerce.cardinalmobilesdk.models.ValidateResponse;

class CardinalActivityResult {

    private final ThreeDSecureResult threeDSecureResult;
    private final ValidateResponse validateResponse;
    private final String jwt;

    static CardinalActivityResult fromIntent(Intent data) {
        ThreeDSecureResult threeDSecureResult = data.getParcelableExtra(ThreeDSecureActivity.EXTRA_THREE_D_SECURE_RESULT);
        ValidateResponse validateResponse = (ValidateResponse) data.getSerializableExtra(ThreeDSecureActivity.EXTRA_VALIDATION_RESPONSE);
        String jwt = data.getStringExtra(ThreeDSecureActivity.EXTRA_JWT);
        return new CardinalActivityResult(threeDSecureResult, validateResponse, jwt);
    }

    private CardinalActivityResult(ThreeDSecureResult threeDSecureResult, ValidateResponse validateResponse, String jwt) {
        this.threeDSecureResult = threeDSecureResult;
        this.validateResponse = validateResponse;
        this.jwt = jwt;
    }

    public ThreeDSecureResult getThreeDSecureResult() {
        return threeDSecureResult;
    }

    public ValidateResponse getValidateResponse() {
        return validateResponse;
    }

    public String getJwt() {
        return jwt;
    }
}
