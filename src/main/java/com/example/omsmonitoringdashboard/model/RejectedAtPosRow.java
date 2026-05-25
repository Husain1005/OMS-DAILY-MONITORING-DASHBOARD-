package com.example.omsmonitoringdashboard.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectedAtPosRow {
    @JacksonXmlProperty(localName = "ORDER_NO", isAttribute = true)
    private String orderNo;

    @JacksonXmlProperty(localName = "ORDER_NAME", isAttribute = true)
    private String orderName;

    @JacksonXmlProperty(localName = "ITEM_ID", isAttribute = true)
    private String itemId;

    @JacksonXmlProperty(localName = "DESCRIPTION", isAttribute = true)
    private String description;

    @JacksonXmlProperty(localName = "STATUS", isAttribute = true)
    private String status;

    @JacksonXmlProperty(localName = "PROCESS_TYPE_KEY", isAttribute = true)
    private String processTypeKey;
}
