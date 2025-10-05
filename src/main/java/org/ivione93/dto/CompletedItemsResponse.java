package org.ivione93.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.Instant;
import java.util.List;

@RegisterForReflection
public class CompletedItemsResponse {

    public List<ItemResponse> items;
    public Integer currentSize;
    public Integer maxSize;
    public Integer minStay;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ItemResponse {
        public Integer itemCode;
        public String itemDescription;
        public Double retailPrice;
        public Instant creationDate;
        public Instant minStayDate;
        public Boolean isRemovable;

        public ItemResponse(Integer itemCode, String itemDescription, Double retailPrice, Instant creationDate) {
            this.itemCode = itemCode;
            this.itemDescription = itemDescription;
            this.retailPrice = retailPrice;
            this.creationDate = creationDate;
        }
    }

}
