package com.example.omsmonitoringdashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @GetMapping("/order-count")
    public List<Map<String, Object>> orderCount() {
        return List.of(
            Map.of("shipnode_key", "H1F", "entry_type", "Amazon", "from_april", 1683),
            Map.of("shipnode_key", "UEC", "entry_type", "Amazon", "from_april", 4657),
            Map.of("shipnode_key", "UEC", "entry_type", "Flipkart", "from_april", 3),
            Map.of("shipnode_key", "H1F", "entry_type", "Myntra", "from_april", 31656),
            Map.of("shipnode_key", "UEC", "entry_type", "Myntra", "from_april", 88660),
            Map.of("shipnode_key", "UEC", "entry_type", "Nykaa", "from_april", 599),
            Map.of("shipnode_key", "H1F", "entry_type", "TataCliq", "from_april", 2713),
            Map.of("shipnode_key", "UEC", "entry_type", "TataCliq", "from_april", 4515),
            Map.of("shipnode_key", "UEC", "entry_type", "TataLuxury", "from_april", 4),
            Map.of("shipnode_key", "H1F", "entry_type", "SFCC", "from_april", 32768),
            Map.of("shipnode_key", "HMTR", "entry_type", "SFCC", "from_april", 5147),
            Map.of("shipnode_key", "N1F", "entry_type", "SFCC", "from_april", 524),
            Map.of("shipnode_key", "UEC", "entry_type", "SFCC", "from_april", 58372),
            Map.of("shipnode_key", "WEC", "entry_type", "SFCC", "from_april", 1094),
            Map.of("shipnode_key", "WLF", "entry_type", "SFCC", "from_april", 7)
        );
    }

    @GetMapping("/hmtr-orders")
    public List<Map<String, Object>> hmtrOrders() {
        return List.of(
            Map.of("shipnode_key", "H1F", "entry_type", "Amazon", "yesterday_count", 3),
            Map.of("shipnode_key", "H1F", "entry_type", "Myntra", "yesterday_count", 8),
            Map.of("shipnode_key", "H1F", "entry_type", "TataCliq", "yesterday_count", 2),
            Map.of("shipnode_key", "H1F", "entry_type", "SFCC", "yesterday_count", 130),
            Map.of("shipnode_key", "N1F", "entry_type", "SFCC", "yesterday_count", 17),
            Map.of("shipnode_key", "WEC", "entry_type", "SFCC", "yesterday_count", 3)
        );
    }

    @GetMapping("/backorder")
    public Map<String, String> backorder() {
        return Map.of("subject", "Backorder Report - Daily OMS Monitoring");
    }

    @GetMapping("/rejected-at-pos")
    public Map<String, String> rejectedAtPos() {
        return Map.of("subject", "Rejected at POS Report - Daily OMS Monitoring");
    }

    @GetMapping("/manual-cancellation")
    public List<Map<String, Object>> manualCancellation() {
        return List.of();
    }

    @GetMapping("/fraud-check-hold")
    public List<Map<String, Object>> fraudCheckHold() {
        return List.of(
            Map.of("order_no", "SFMI00104395", "createts", "2026-04-19 23:30:46", "ors_createts", "2026-04-19 23:30:46", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "602923ZPZAAS00", "shipnode_key", "H1F", "req_ship_date", null, "reason_text", "More than 1 order placed by the customer today", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFMI00104395", "createts", "2026-04-19 23:30:46", "ors_createts", "2026-04-19 23:30:46", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "602923ZPZAAS00", "shipnode_key", "H1F", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000587177", "createts", "2026-04-19 20:58:45", "ors_createts", "2026-04-19 20:58:45", "description", "Created", "hold_flag", "Y", "currency", "USD", "item_id", "511166SICAGA002JA018042", "shipnode_key", "H1F", "req_ship_date", null, "reason_text", "Order was placed by International Card", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000587177", "createts", "2026-04-19 20:58:45", "ors_createts", "2026-04-19 20:58:45", "description", "Created", "hold_flag", "Y", "currency", "USD", "item_id", "511166SICAGA002JA018042", "shipnode_key", "H1F", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000587177", "createts", "2026-04-19 20:58:45", "ors_createts", "2026-04-19 20:58:45", "description", "Created", "hold_flag", "Y", "currency", "USD", "item_id", "51M3DCPALAAA002EA000090", "shipnode_key", "H1F", "req_ship_date", null, "reason_text", "Order was placed by International Card", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000587177", "createts", "2026-04-19 20:58:45", "ors_createts", "2026-04-19 20:58:45", "description", "Created", "hold_flag", "Y", "currency", "USD", "item_id", "51M3DCPALAAA002EA000090", "shipnode_key", "H1F", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000586572", "createts", "2026-04-19 15:58:51", "ors_createts", "2026-04-19 15:58:50", "description", "Created", "hold_flag", "Y", "currency", "USD", "item_id", "51E5C1FCILAA002JD000059", "shipnode_key", "H1F", "req_ship_date", null, "reason_text", "Order was placed by International Card", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000586572", "createts", "2026-04-19 15:58:51", "ors_createts", "2026-04-19 15:58:50", "description", "Created", "hold_flag", "Y", "currency", "USD", "item_id", "51E5C1FCILAA002JD000059", "shipnode_key", "H1F", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582014", "createts", "2026-04-16 14:58:46", "ors_createts", "2026-04-16 14:58:46", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "eGC mail needs to be scheduled", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582014", "createts", "2026-04-16 14:58:46", "ors_createts", "2026-04-16 14:58:46", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "More than 1 order placed by the customer today", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582014", "createts", "2026-04-16 14:58:46", "ors_createts", "2026-04-16 14:58:46", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582123", "createts", "2026-04-16 14:43:45", "ors_createts", "2026-04-16 14:43:45", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "eGC mail needs to be scheduled", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582123", "createts", "2026-04-16 14:43:45", "ors_createts", "2026-04-16 14:43:45", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "More than 1 order placed by the customer today", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582123", "createts", "2026-04-16 14:43:45", "ors_createts", "2026-04-16 14:43:45", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582119", "createts", "2026-04-16 14:28:47", "ors_createts", "2026-04-16 14:28:47", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "eGC mail needs to be scheduled", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582119", "createts", "2026-04-16 14:28:47", "ors_createts", "2026-04-16 14:28:47", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "More than 1 order placed by the customer today", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582119", "createts", "2026-04-16 14:28:47", "ors_createts", "2026-04-16 14:28:47", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582004", "createts", "2026-04-16 14:18:46", "ors_createts", "2026-04-16 14:18:45", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "600102ZNARAW00", "shipnode_key", "N1F", "req_ship_date", null, "reason_text", "eGC mail needs to be scheduled", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582004", "createts", "2026-04-16 14:18:46", "ors_createts", "2026-04-16 14:18:45", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "600102ZNARAW00", "shipnode_key", "N1F", "req_ship_date", null, "reason_text", "More than 1 order placed by the customer today", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582004", "createts", "2026-04-16 14:18:46", "ors_createts", "2026-04-16 14:18:45", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "600102ZNARAW00", "shipnode_key", "N1F", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582004", "createts", "2026-04-16 14:18:46", "ors_createts", "2026-04-16 14:18:45", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "eGC mail needs to be scheduled", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582004", "createts", "2026-04-16 14:18:46", "ors_createts", "2026-04-16 14:18:45", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "More than 1 order placed by the customer today", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582004", "createts", "2026-04-16 14:18:46", "ors_createts", "2026-04-16 14:18:45", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582000", "createts", "2026-04-16 14:03:47", "ors_createts", "2026-04-16 14:03:47", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "eGC mail needs to be scheduled", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582000", "createts", "2026-04-16 14:03:47", "ors_createts", "2026-04-16 14:03:47", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "More than 1 order placed by the customer today", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000582000", "createts", "2026-04-16 14:03:47", "ors_createts", "2026-04-16 14:03:47", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000581998", "createts", "2026-04-16 14:03:49", "ors_createts", "2026-04-16 14:03:49", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "eGC mail needs to be scheduled", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000581998", "createts", "2026-04-16 14:03:49", "ors_createts", "2026-04-16 14:03:49", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "More than 1 order placed by the customer today", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000581998", "createts", "2026-04-16 14:03:49", "ors_createts", "2026-04-16 14:03:49", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000581994", "createts", "2026-04-16 13:58:47", "ors_createts", "2026-04-16 13:58:47", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "eGC mail needs to be scheduled", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000581994", "createts", "2026-04-16 13:58:47", "ors_createts", "2026-04-16 13:58:47", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", "More than 1 order placed by the customer today", "text_1", null, "status_update_by", "CreateOrderIntegServer"),
            Map.of("order_no", "SFTQ000581994", "createts", "2026-04-16 13:58:47", "ors_createts", "2026-04-16 13:58:47", "description", "Created", "hold_flag", "Y", "currency", "INR", "item_id", "eGCTanishq", "shipnode_key", "WEC", "req_ship_date", null, "reason_text", null, "text_1", null, "status_update_by", "CreateOrderIntegServer")
        );
    }

    @GetMapping("/stuck-orders")
    public List<Map<String, Object>> stuckOrders() {
        return List.of();
    }

    @GetMapping("/egc-gc-orders")
    public List<Map<String, Object>> egcGcOrders() {
        return List.of(
            Map.of("order_no", "SFTQ000582004", "item_id", "eGCTanishq", "createts", "2026-04-16 14:18:46", "description", "Created", "egc_scheduled_date", null),
            Map.of("order_no", "SFMI00101919", "item_id", "GCMia", "createts", "2026-04-10 12:32:58", "description", "Out For Delivery", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000581994", "item_id", "eGCTanishq", "createts", "2026-04-16 13:58:47", "description", "Created", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000582000", "item_id", "eGCTanishq", "createts", "2026-04-16 14:03:47", "description", "Created", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000581998", "item_id", "eGCTanishq", "createts", "2026-04-16 14:03:49", "description", "Created", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000582119", "item_id", "eGCTanishq", "createts", "2026-04-16 14:28:47", "description", "Created", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000582123", "item_id", "eGCTanishq", "createts", "2026-04-16 14:43:45", "description", "Created", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000582014", "item_id", "eGCTanishq", "createts", "2026-04-16 14:58:46", "description", "Created", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000583937", "item_id", "GCTanishq", "createts", "2026-04-19 2:28:47", "description", "Out For Delivery", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000584038", "item_id", "GCTanishq", "createts", "2026-04-19 2:33:47", "description", "In Transit", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000584077", "item_id", "GCTanishq", "createts", "2026-04-19 3:28:47", "description", "Out For Delivery", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000584245", "item_id", "GCTanishq", "createts", "2026-04-19 4:53:46", "description", "In Transit", "egc_scheduled_date", null),
            Map.of("order_no", "SFMI00103873", "item_id", "GCMia", "createts", "2026-04-19 9:06:48", "description", "In Transit", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000587405", "item_id", "GCTanishq", "createts", "2026-04-20 5:33:47", "description", "In Transit", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000587648", "item_id", "GCTanishq", "createts", "2026-04-20 12:08:47", "description", "In Transit", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000587929", "item_id", "eGCTanishq", "createts", "2026-04-20 16:03:46", "description", "Scheduled", "egc_scheduled_date", "2026-04-24 0:00:00"),
            Map.of("order_no", "SFMI00104630", "item_id", "GCMia", "createts", "2026-04-20 19:54:48", "description", "In Transit", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000588325", "item_id", "eGCTanishq", "createts", "2026-04-21 12:28:46", "description", "Scheduled", "egc_scheduled_date", "2026-04-24 0:00:00"),
            Map.of("order_no", "SFTQ000588421", "item_id", "eGCTanishq", "createts", "2026-04-21 12:58:45", "description", "Created", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000588429", "item_id", "eGCTanishq", "createts", "2026-04-21 13:58:46", "description", "Scheduled", "egc_scheduled_date", "2026-04-24 0:00:00"),
            Map.of("order_no", "SFTQ000588627", "item_id", "GCTanishq", "createts", "2026-04-21 20:58:47", "description", "Packed", "egc_scheduled_date", null),
            Map.of("order_no", "SFTQ000588626", "item_id", "GCTanishq", "createts", "2026-04-21 20:58:54", "description", "Packed", "egc_scheduled_date", null)
        );
    }
}
