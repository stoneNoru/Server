package com.ssafy.home.status;

import org.springframework.http.HttpStatus;

public enum DuplicateHttpStatus {
	DUPLICATE(HttpStatus.CONFLICT.value(), "Duplicate");

    private final int value;
    private final String reasonPhrase;

    DuplicateHttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}


