package com.example.omsmonitoringdashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCountRow {

    @JacksonXmlProperty(localName = "ENTRY_TYPE", isAttribute = true)
    private String entryType;

    @JacksonXmlProperty(localName = "SHIPNODE_KEY", isAttribute = true)
    private String shipnodeKey;

    @JacksonXmlProperty(localName = "TOTAL_ORDERS", isAttribute = true)
    private Integer totalOrders;

    public OrderCountRow(String entryType, String shipnodeKey, Integer totalOrders) {
        this.entryType = entryType;
        this.shipnodeKey = shipnodeKey;
        this.totalOrders = totalOrders;
    }
}
