package com.example.omsmonitoringdashboard.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudHoldRow {
    @JacksonXmlProperty(localName = "ORDER_NO", isAttribute = true)
    private String orderNo;

    @JacksonXmlProperty(localName = "CREATETS", isAttribute = true)
    private String createTs;

    @JacksonXmlProperty(localName = "DESCRIPTION", isAttribute = true)
    private String description;

    @JacksonXmlProperty(localName = "HOLD_FLAG", isAttribute = true)
    private String holdFlag;

    @JacksonXmlProperty(localName = "CURRENCY", isAttribute = true)
    private String currency;

    @JacksonXmlProperty(localName = "ITEM_ID", isAttribute = true)
    private String itemId;

    @JacksonXmlProperty(localName = "SHIPNODE_KEY", isAttribute = true)
    private String shipnodeKey;

    @JacksonXmlProperty(localName = "REQ_SHIP_DATE", isAttribute = true)
    private String reqShipDate;

    @JacksonXmlProperty(localName = "REASON_TEXT", isAttribute = true)
    private String reasonText;

    @JacksonXmlProperty(localName = "TEXT_1", isAttribute = true)
    private String text1;

    @JacksonXmlProperty(localName = "CREATEUSERID", isAttribute = true)
    private String createUserId;
}
