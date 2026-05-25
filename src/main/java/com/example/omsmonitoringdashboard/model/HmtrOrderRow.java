package com.example.omsmonitoringdashboard.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HmtrOrderRow {
    @JacksonXmlProperty(localName = "ENTRY_TYPE", isAttribute = true)
    private String entryType;

    @JacksonXmlProperty(localName = "SHIPNODE_KEY", isAttribute = true)
    private String shipnodeKey;

    @JacksonXmlProperty(localName = "YESTERDAY_COUNT", isAttribute = true)
    private String yesterdayCount;
}
