package com.example.skyreserve.util

enum class SignUpResult {
    SUCCESS,
    INVALID_EMAIL,
    EXISTING_EMAIL,
    SHORT_PASSWORD,
    MISSING_CAPITAL_LETTER,
    MISSING_LOWCASE_LETTER,
    MISSING_DIGIT,
    PASSWORD_NO_MATCH,
    MISSING_INFORMATION,
    TERMS_NOT_ACCEPTED,
    NETWORK_ERROR,
    UNKNOWN_ERROR
}