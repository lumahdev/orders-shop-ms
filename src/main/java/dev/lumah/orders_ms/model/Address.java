package dev.lumah.orders_ms.model;

import lombok.Data;

@Data
public class Address {

    private String cep;
    private String street;
    private String number;
    private String additional;
    private String neighborhood;
    private String state;
}
