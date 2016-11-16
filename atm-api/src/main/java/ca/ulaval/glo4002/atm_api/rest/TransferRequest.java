package ca.ulaval.glo4002.atm_api.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {

    public final double amount;

    @JsonCreator
    public TransferRequest(@JsonProperty("amount") double amount) {
        this.amount = amount;
    }

}
