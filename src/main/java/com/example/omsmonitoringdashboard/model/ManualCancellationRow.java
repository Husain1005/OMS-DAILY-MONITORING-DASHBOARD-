package com.example.omsmonitoringdashboard.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManualCancellationRow {
    @JacksonXmlProperty(localName = "ORDER_NO", isAttribute = true)
    private String orderNo;

    @JacksonXmlProperty(localName = "ENTRY_TYPE", isAttribute = true)
    private String entryType;

    @JacksonXmlProperty(localName = "SHIPMENT_NO", isAttribute = true)
    private String shipmentNo;

    @JacksonXmlProperty(localName = "STATUS", isAttribute = true)
    private String status;

    @JacksonXmlProperty(localName = "NEW_STATUS_DATE", isAttribute = true)
    private String newStatusDate;

    @JacksonXmlProperty(localName = "SHIPMENT_STATUS_DESC", isAttribute = true)
    private String shipmentStatusDesc;

    @JacksonXmlProperty(localName = "CREATEUSERID", isAttribute = true)
    private String createUserId;

    @JacksonXmlProperty(localName = "CREATEPROGID", isAttribute = true)
    private String createProgId;
}
