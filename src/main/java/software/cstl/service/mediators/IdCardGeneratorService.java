package software.cstl.service.mediators;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.languages.IndicLigaturizer;
import liquibase.pro.packaged.I;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.model.IText;
import org.xhtmlrenderer.pdf.ITextRenderer;
import software.cstl.domain.Employee;
import software.cstl.domain.IdCardManagement;
import software.cstl.repository.IdCardManagementRepository;
import software.cstl.service.EmployeeService;
import software.cstl.service.IdCardManagementService;
import software.cstl.service.extended.EmployeeExtService;
import software.cstl.service.reports.ThymeleafReportService;
import software.cstl.utils.CodeNodeErpUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Component
@Transactional(readOnly = true)
public class IdCardGeneratorService {


    private final Logger log = LoggerFactory.getLogger(IdCardGeneratorService.class);

    private final EmployeeExtService employeeExtService;
    private final ThymeleafReportService thymeleafReportService;
    private final IdCardManagementRepository idCardManagementRepository;

    public IdCardGeneratorService(EmployeeExtService employeeExtService, ThymeleafReportService thymeleafReportService, IdCardManagementRepository idCardManagementRepository) {
        this.employeeExtService = employeeExtService;
        this.thymeleafReportService = thymeleafReportService;
        this.idCardManagementRepository = idCardManagementRepository;
    }

    public ByteArrayInputStream generateIdCard(Long idCardManagementId) throws DocumentException, IOException, com.lowagie.text.DocumentException {

        IdCardManagement idCardManagement = idCardManagementRepository.getOne(idCardManagementId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        InputStream is = IdCardGeneratorService.class.getResourceAsStream("/templates/jxls/IdCard.xls");
        Context context = new Context();
        context.putVar("cardNo", idCardManagement.getCardNo());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String issueDate = idCardManagement.getIssueDate().format(formatter);
        context.putVar("issueDate", CodeNodeErpUtils.getDigitBanglaFromEnglish(issueDate));
        context.putVar("ticketNo", CodeNodeErpUtils.getDigitBanglaFromEnglish(idCardManagement.getTicketNo()));
        context.putVar("photo", idCardManagement.getEmployee().getPersonalInfo().getPhoto());
        context.putVar("instituteName", idCardManagement.getEmployee().getCompany().getNameInBangla());
        context.putVar("employeeName", idCardManagement.getEmployee().getPersonalInfo().getBanglaName());
        context.putVar("designation", idCardManagement.getEmployee().getDesignation().getNameInBangla());
        context.putVar("department", idCardManagement.getEmployee().getDepartment().getNameInBangla());
        context.putVar("floor", "()");
        String joiningDate = idCardManagement.getEmployee().getJoiningDate().format(formatter);
        context.putVar("joiningDate", joiningDate);
        String validTill = idCardManagement.getValidTill().format(formatter);
        context.putVar("validTill", validTill);
        context.putVar("address",  idCardManagement.getEmployee().getAddress().getPermanentArea());

        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        jxlsHelper.processTemplate(is, out, context);

        return new ByteArrayInputStream(out.toByteArray());
    }
}
