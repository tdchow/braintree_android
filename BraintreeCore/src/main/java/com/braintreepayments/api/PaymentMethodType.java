package com.braintreepayments.api;

public enum PaymentMethodType {
    AMEX("American Express"),
    GOOGLE_PAY("Google Pay"),
    DINERS_CLUB("Diners"),
    DISCOVER("Discover"),
    JCB("JCB"),
    MAESTRO("Maestro"),
    MASTERCARD("MasterCard"),
    PAYPAL("PayPal"),
    VISA("Visa"),
    VENMO("Venmo"),
    UNIONPAY("UnionPay"),
    HIPER("Hiper"),
    HIPERCARD("Hipercard"),
    UNKNOWN("Unknown");

    PaymentMethodType fromString(String value) {
        for (PaymentMethodType type : PaymentMethodType.values()) {
            if (type.canonicalName.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return PaymentMethodType.UNKNOWN;
    }

    private final String canonicalName;

    PaymentMethodType(String canonicalName) {
        this.canonicalName = canonicalName;
    }
}
