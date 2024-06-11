package br.com.matteusmoreno.employee;

import lombok.Getter;

@Getter
public enum EmployeeRole {
    MECHANIC("Mechanic"),
    SALES_ASSOCIATE("Sales Associate"),
    STORE_MANAGER("Store Manager"),
    ACCOUNTANT("Accountant"),
    ADMINISTRATIVE_ASSISTANT("Administrative Assistant");

    private final String displayName;

    EmployeeRole(String displayName) {
        this.displayName = displayName;
    }
}
