package org.ivione93.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ItemPrice {

    public Integer itemCode;
    public Double retailPrice;
    public Double royaltyRetailPrice;
    public Double nonRoyaltyRetailPrice;

}
