package com.example.omsmonitoringdashboard.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EgcGcOrderRow {
    @JacksonXmlProperty(localName = "ORDER_NO", isAttribute = true)
    private String orderNo;

    @JacksonXmlProperty(localName = "ITEM_ID", isAttribute = true)
    private String itemId;

    @JacksonXmlProperty(localName = "CREATETS", isAttribute = true)
    private String createTs;

    @JacksonXmlProperty(localName = "DESCRIPTION", isAttribute = true)
    private String description;

    @JacksonXmlProperty(localName = "EXPECTED_DATE", isAttribute = true)
    private String expectedDate;
}
