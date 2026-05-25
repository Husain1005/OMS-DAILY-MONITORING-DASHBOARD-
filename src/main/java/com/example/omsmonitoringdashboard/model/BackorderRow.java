package com.example.omsmonitoringdashboard.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackorderRow {
    @JacksonXmlProperty(localName = "ORDER_NO", isAttribute = true)
    private String orderNo;

    @JacksonXmlProperty(localName = "ENTRY_TYPE", isAttribute = true)
    private String entryType;

    @JacksonXmlProperty(localName = "ITEM_ID", isAttribute = true)
    private String itemId;

    @JacksonXmlProperty(localName = "EXTN_BRAND", isAttribute = true)
    private String extnBrand;

    @JacksonXmlProperty(localName = "PRODUCT_LINE", isAttribute = true)
    private String productLine;

    @JacksonXmlProperty(localName = "SHIPNODE_KEY", isAttribute = true)
    private String shipnodeKey;

    @JacksonXmlProperty(localName = "DESCRIPTION", isAttribute = true)
    private String description;

    @JacksonXmlProperty(localName = "HOLD_FLAG", isAttribute = true)
    private String holdFlag;

    @JacksonXmlProperty(localName = "ORDER_DATE", isAttribute = true)
    private String orderDate;

    @JacksonXmlProperty(localName = "CREATETS", isAttribute = true)
    private String createTs;

    @JacksonXmlProperty(localName = "STATUS_DATE", isAttribute = true)
    private String statusDate;
}
