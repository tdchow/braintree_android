package com.paypal.demojava7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.Card;
import com.braintreepayments.api.CardClient;
import com.braintreepayments.api.CardNonce;
import com.braintreepayments.api.CardTokenizeCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Card card = new Card();
        card.setNumber("4111111111111111");
        card.setExpirationDate("09/2018");

        BraintreeClient braintreeClient = new BraintreeClient(this, "sandbox_tmxhyf7d_dcpspy2brwdjr3qn");
        CardClient cardClient = new CardClient(braintreeClient);
        cardClient.tokenize(card, new CardTokenizeCallback() {
            @Override
            public void onResult(@Nullable @org.jetbrains.annotations.Nullable CardNonce cardNonce, @Nullable @org.jetbrains.annotations.Nullable Exception error) {
                if (cardNonce != null) {
                    Log.d("hello", cardNonce.toString());
                } else if (error != null) {
                    Log.d("hello", error.toString());
                }
            }
        });
    }
}