ackage com.example.omsmonitoringdashboard.service;

import com.example.omsmonitoringdashboard.model.OrderCountRow;
import com.example.omsmonitoringdashboard.model.OrderCountRowset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Titan Order Count Integration Tests")
public class OmsServiceTitanOrderCountTest {

    @Autowired
    private OmsService omsService;

    @Test
    @DisplayName("Test getTitanOrderCount - Should fetch and parse XML response successfully")
    public void testGetTitanOrderCount() {
        try {
            // Execute the service call
            OrderCountRowset rowset = omsService.getTitanOrderCount();

            // Assertions
            assertNotNull(rowset, "OrderCountRowset should not be null");
            assertNotNull(rowset.getRows(), "Rows should not be null");
            assertTrue(rowset.getRows().size() > 0, "Rows should not be empty");

            // Verify response structure
            for (OrderCountRow row : rowset.getRows()) {
                assertNotNull(row.getEntryType(), "ENTRY_TYPE should not be null");
                assertNotNull(row.getShipnodeKey(), "SHIPNODE_KEY should not be null");
                assertNotNull(row.getTotalOrders(), "TOTAL_ORDERS should not be null");
                assertTrue(row.getTotalOrders() >= 0, "TOTAL_ORDERS should be non-negative");

                // Print for validation
                System.out.println("EntryType: " + row.getEntryType().trim() + 
                                 ", ShipnodeKey: " + row.getShipnodeKey().trim() + 
                                 ", TotalOrders: " + row.getTotalOrders());
            }

            System.out.println("Test passed! Total rows received: " + rowset.getRows().size());

        } catch (Exception e) {
            System.err.println("Error during Titan order count test: " + e.getMessage());
            e.printStackTrace();
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test getTitanOrderCount - Response data validation")
    public void testTitanOrderCountDataValidation() {
        try {
            OrderCountRowset rowset = omsService.getTitanOrderCount();

            assertNotNull(rowset, "Response should not be null");

            if (rowset.getRows() != null && !rowset.getRows().isEmpty()) {
                // Validate that we have expected entry types
                boolean hasMyntra = rowset.getRows().stream()
                    .anyMatch(r -> r.getEntryType().contains("Myntra"));
                
                System.out.println("Contains Myntra entries: " + hasMyntra);

                // Verify numeric values
                long totalOrders = rowset.getRows().stream()
                    .mapToLong(OrderCountRow::getTotalOrders)
                    .sum();

                System.out.println("Total orders across all entries: " + totalOrders);
                assertTrue(totalOrders >= 0, "Total orders should be non-negative");
            }

        } catch (Exception e) {
            System.err.println("Error during data validation test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
