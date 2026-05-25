package com.example.omsmonitoringdashboard.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "ROWSET")
public class BackorderRowset {
    @JacksonXmlProperty(localName = "ROW")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BackorderRow> rows = new ArrayList<>();
}
