/*
package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
*/
/**
 * Test class for the PayrollManagementResource REST controller.
 *
 * @see PayrollManagementResource
 *//*

@SpringBootTest(classes = CodeNodeErpApp.class)
public class PayrollManagementResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        PayrollManagementResource payrollManagementResource = new PayrollManagementResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(payrollManagementResource)
            .build();
    }

    */
/**
     * Test generateEmptySalaries
     *//*

    @Test
    public void testGenerateEmptySalaries() throws Exception {
        restMockMvc.perform(get("/api/payroll-management/generate-empty-salaries"))
            .andExpect(status().isOk());
    }

    */
/**
     * Test generateSalaries
     *//*

    @Test
    public void testGenerateSalaries() throws Exception {
        restMockMvc.perform(get("/api/payroll-management/generate-salaries"))
            .andExpect(status().isOk());
    }
}
*/
