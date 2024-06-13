package br.com.matteusmoreno.payable_account.payable_account_request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreatePayableAccountRequest(
        @NotNull(message = "Supplier ID is required")
        UUID supplierId,
        String description,
        @NotNull(message = "Amount is required")
        @PositiveOrZero(message = "Amount must be greater than or equal to zero")
        BigDecimal amount,
        @NotNull(message = "Payment date is required")
        LocalDate paymentDate) {
}
