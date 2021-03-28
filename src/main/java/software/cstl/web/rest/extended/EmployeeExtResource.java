package software.cstl.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.cstl.service.EmployeeQueryService;
import software.cstl.service.EmployeeService;
import software.cstl.service.extended.EmployeeExtService;
import software.cstl.service.mediators.IdCardGeneratorService;
import software.cstl.web.rest.EmployeeResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@RestController
@RequestMapping("/api/ext")
public class EmployeeExtResource extends EmployeeResource {
    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "employee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeExtService employeeService;

    private final EmployeeQueryService employeeQueryService;

    private final IdCardGeneratorService idCardGeneratorService;

    public EmployeeExtResource(EmployeeService employeeService, EmployeeQueryService employeeQueryService, EmployeeExtService employeeService1, EmployeeQueryService employeeQueryService1, @Autowired IdCardGeneratorService idCardGeneratorService) {
        super(employeeService, employeeQueryService);
        this.employeeService = employeeService1;
        this.employeeQueryService = employeeQueryService1;
        this.idCardGeneratorService = idCardGeneratorService;
    }

    @GetMapping(value = "/employees/id-card/{employeeId}", produces = APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateIdCard(@PathVariable Long employeeId) throws DocumentException, IOException, com.lowagie.text.DocumentException {
        ByteArrayInputStream bis = idCardGeneratorService.generateIdCard(employeeId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=id-card.pdf");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
    }
}
