package br.com.matteusmoreno.constant;

import lombok.Getter;

@Getter
public enum Role {
    MECHANIC("Mechanic"),
    SALES_ASSOCIATE("Sales Associate"),
    STORE_MANAGER("Store Manager"),
    ACCOUNTANT("Accountant"),
    ADMINISTRATIVE_ASSISTANT("Administrative Assistant");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }
}
