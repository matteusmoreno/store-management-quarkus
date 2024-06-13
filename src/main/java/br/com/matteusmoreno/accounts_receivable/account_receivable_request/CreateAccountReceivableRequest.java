package br.com.matteusmoreno.accounts_receivable.account_receivable_request;

import org.bson.types.ObjectId;

public record CreateAccountReceivableRequest(
        ObjectId serviceOrder) {
}
