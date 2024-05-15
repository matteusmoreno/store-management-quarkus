package br.com.matteusmoreno.constant;

import lombok.Getter;

@Getter
public enum ServiceOrderStatus {
    PENDING("Pending"),
    IN_PROGRESS("In progress"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private final String displayName;

    ServiceOrderStatus(String displayName) {
        this.displayName = displayName;
    }
}
