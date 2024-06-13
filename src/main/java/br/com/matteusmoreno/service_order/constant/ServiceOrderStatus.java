package br.com.matteusmoreno.service_order.constant;

import lombok.Getter;

@Getter
public enum ServiceOrderStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String displayName;

    ServiceOrderStatus(String displayName) {
        this.displayName = displayName;
    }
}
