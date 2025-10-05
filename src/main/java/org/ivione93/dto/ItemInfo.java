package org.ivione93.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.Instant;

@RegisterForReflection
public class ItemInfo {

    public Integer itemCode;
    public String itemDescription;
    public Instant creationDate;

}
