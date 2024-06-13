package br.com.matteusmoreno.accounts_receivable;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PENDING("Pending"),
    PAID("Paid"),
    OVERDUE("Overdue"),
    CANCELED("Canceled");

    private final String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }
}
