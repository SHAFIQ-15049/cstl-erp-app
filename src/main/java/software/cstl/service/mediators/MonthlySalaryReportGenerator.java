package software.cstl.service.mediators;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import liquibase.pro.packaged.C;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.ResourceLoader;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.*;
import software.cstl.repository.*;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.JxlsGenerator;
import software.cstl.service.dto.AttendanceSummaryDTO;
import software.cstl.service.dto.salary.SalaryReportDto;
import software.cstl.utils.CodeNodeErpUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@Slf4j
public class MonthlySalaryReportGenerator {
    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 8.5f);
    Font bodyFontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);




    private final MonthlySalaryRepository monthlySalaryRepository;
    private final CompanyRepository companyRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceSummaryService attendanceSummaryService;
    private final OverTimeRepository overTimeRepository;
    private final FineRepository fineRepository;
    private final FinePaymentHistoryRepository finePaymentHistoryRepository;
    private final AdvanceRepository advanceRepository;
    private final AdvancePaymentHistoryRepository advancePaymentHistoryRepository;

    public MonthlySalaryReportGenerator(MonthlySalaryRepository monthlySalaryRepository, CompanyRepository companyRepository, AttendanceRepository attendanceRepository, AttendanceSummaryService attendanceSummaryService, OverTimeRepository overTimeRepository, FineRepository fineRepository, FinePaymentHistoryRepository finePaymentHistoryRepository, AdvanceRepository advanceRepository, AdvancePaymentHistoryRepository advancePaymentHistoryRepository) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.companyRepository = companyRepository;
        this.attendanceRepository = attendanceRepository;
        this.attendanceSummaryService = attendanceSummaryService;
        this.overTimeRepository = overTimeRepository;
        this.fineRepository = fineRepository;
        this.finePaymentHistoryRepository = finePaymentHistoryRepository;
        this.advanceRepository = advanceRepository;
        this.advancePaymentHistoryRepository = advancePaymentHistoryRepository;
    }

    public ByteArrayInputStream createSalaryReport(Long monthlySalaryId) throws DocumentException {
        MonthlySalary monthlySalary = monthlySalaryRepository.getOne(monthlySalaryId);

        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        document.setMargins(2f,2f,2f,2f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();




        List<MonthlySalaryDtl> monthlySalaryDtls = new ArrayList<>(monthlySalary.getMonthlySalaryDtls())
            .stream()
            .sorted((o1,o2)-> o1.getEmployee().getId().compareTo(o2.getEmployee().getId()))
            .collect(Collectors.toList());

        Map<Department, List<MonthlySalaryDtl>> departmentSalaryMap = getDepartmentAndSalaryMap(monthlySalaryDtls);
        for(Map.Entry<Department, List<MonthlySalaryDtl>> entry: departmentSalaryMap.entrySet()){
            PdfPTable headerTable = getPageHeader(monthlySalary);
            document.add(headerTable);

            Paragraph paragraph = new Paragraph("        Department: " + entry.getKey().getName(), bodyFontBold);
            paragraph.setSpacingAfter(10f);
            document.add(paragraph);

            float[] detailsTableSize = {1f, 4f,1f,2.5f,2f,2f,2f, 4f,1f,1f,1f,1f, 1f, 6f, 2f, 2f, 2f, 2f, 2.5f, 3f};
            PdfPTable salaryDetailsTable = new PdfPTable(detailsTableSize);
            salaryDetailsTable.setWidthPercentage(98);
            salaryDetailsTable.setPaddingTop(10f);

            createSalaryDetailsTableHeader(salaryDetailsTable);
            Integer sequence = 0;
            List<MonthlySalaryDtl> monthlySalaryDtlList = entry.getValue();

            for(MonthlySalaryDtl monthlySalaryDtl: monthlySalaryDtlList){
                createSalaryDetailsTableBody(salaryDetailsTable, sequence, monthlySalaryDtl);
            }

            document.add(salaryDetailsTable);

            sequence+=1;
            if(sequence< departmentSalaryMap.size())
                document.newPage();
        }
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void createSalaryDetailsTableBody(PdfPTable salaryDetailsTable, Integer serial, MonthlySalaryDtl monthlySalaryDtl) {
        Paragraph paragraph;
        PdfPCell serialCell = new PdfPCell(new Paragraph((serial+1)+"", bodyFont));
        salaryDetailsTable.addCell(serialCell);

        PdfPTable employeeTable = new PdfPTable(1);
        paragraph = new Paragraph(monthlySalaryDtl.getEmployee().getName(), bodyFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        PdfPCell employeeCellDetails = new PdfPCell(paragraph);
        employeeCellDetails.setHorizontalAlignment(Element.ALIGN_CENTER);
        employeeTable.addCell(employeeCellDetails);

        paragraph = new Paragraph(monthlySalaryDtl.getEmployee().getDesignation().getName(), bodyFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        employeeCellDetails = new PdfPCell(paragraph);
        employeeCellDetails.setHorizontalAlignment(Element.ALIGN_CENTER);
        employeeTable.addCell(employeeCellDetails);


        paragraph = new Paragraph(monthlySalaryDtl.getEmployee().getId().toString(), bodyFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        employeeCellDetails = new PdfPCell(paragraph);
        employeeCellDetails.setHorizontalAlignment(Element.ALIGN_CENTER);
        employeeTable.addCell(employeeCellDetails);

        paragraph = new Paragraph(monthlySalaryDtl.getEmployee().getLocalId(), bodyFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        employeeCellDetails = new PdfPCell(paragraph);
        employeeCellDetails.setHorizontalAlignment(Element.ALIGN_CENTER);
        employeeTable.addCell(employeeCellDetails);

        paragraph = new Paragraph(monthlySalaryDtl.getEmployee().getJoiningDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")), bodyFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        employeeCellDetails = new PdfPCell(paragraph);
        employeeCellDetails.setHorizontalAlignment(Element.ALIGN_CENTER);
        employeeTable.addCell(employeeCellDetails);

        PdfPCell employeeCell = new PdfPCell(employeeTable);


        salaryDetailsTable.addCell(employeeCell);

        PdfPCell gradeCell = new PdfPCell();
        paragraph = new Paragraph(monthlySalaryDtl.getEmployee().getGrade()!=null? monthlySalaryDtl.getEmployee().getGrade().getName(): "", bodyFont);
        gradeCell.addElement(paragraph);
        gradeCell.setRotation(90);
        salaryDetailsTable.addCell(gradeCell);

        PdfPCell salaryCell = new PdfPCell();
        paragraph = new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(monthlySalaryDtl.getGross()), bodyFont);
        salaryCell.addElement(paragraph);
        paragraph = new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(monthlySalaryDtl.getBasic()), bodyFont);
        salaryCell.addElement(paragraph);
        paragraph = new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(monthlySalaryDtl.getHouseRent()), bodyFont);
        salaryCell.addElement(paragraph);
        salaryDetailsTable.addCell(salaryCell);

        PdfPCell mainSalaryCell = new PdfPCell();
        paragraph = new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(monthlySalaryDtl.getBasic()), bodyFont);
        mainSalaryCell.addElement(paragraph);
        mainSalaryCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        mainSalaryCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(mainSalaryCell);

        PdfPCell houseRent = new PdfPCell();
        paragraph = new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(monthlySalaryDtl.getHouseRent()), bodyFont);
        houseRent.addElement(paragraph);
        houseRent.setHorizontalAlignment(Element.ALIGN_CENTER);
        houseRent.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(houseRent);

        PdfPCell medicalAllowanceCell = new PdfPCell();
        medicalAllowanceCell.addElement(new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(monthlySalaryDtl.getMedicalAllowance()), bodyFont));
        medicalAllowanceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        medicalAllowanceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(medicalAllowanceCell);

        PdfPTable leaveMainTable = new PdfPTable(1);


        PdfPTable leaveInnerTable = new PdfPTable(4);
        PdfPCell cell = new PdfPCell();
        cell.addElement(new Paragraph("0", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        leaveInnerTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph("0", bodyFont));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        leaveInnerTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph("0", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        leaveInnerTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph("0", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        leaveInnerTable.addCell(cell);

        cell = new PdfPCell(leaveInnerTable);
        leaveMainTable.addCell(cell);

        PdfPCell leaveCell = new PdfPCell(leaveMainTable);
        salaryDetailsTable.addCell(leaveCell);


        cell = new PdfPCell();
        cell.addElement(new Paragraph("0", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);

        List<AttendanceSummaryDTO> attendanceSummaryDTOS = attendanceSummaryService.findAll(monthlySalaryDtl.getMonthlySalary().getFromDate().atZone(ZoneId.systemDefault()).toLocalDate(), monthlySalaryDtl.getMonthlySalary().getToDate().atZone(ZoneId.systemDefault()).toLocalDate());

        int totalDays = attendanceSummaryDTOS.size();

        cell = new PdfPCell();
        cell.addElement(new Paragraph("0", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);


        List<AttendanceSummaryDTO> employeeAttendanceSummary = attendanceSummaryService.findAll(monthlySalaryDtl.getEmployee().getId(),monthlySalaryDtl.getMonthlySalary().getFromDate().atZone(ZoneId.systemDefault()).toLocalDate(), monthlySalaryDtl.getMonthlySalary().getToDate().atZone(ZoneId.systemDefault()).toLocalDate() );


        cell = new PdfPCell();
        cell.addElement(new Paragraph(employeeAttendanceSummary.size()+"", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph((totalDays-attendanceSummaryDTOS.size())+"", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph(totalDays+"", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);


        // overtime
        PdfPTable overtimeMainTable = new PdfPTable(1);

        OverTime overTime = overTimeRepository.findByYearAndMonthAndEmployee(monthlySalaryDtl.getMonthlySalary().getYear(), monthlySalaryDtl.getMonthlySalary().getMonth(), monthlySalaryDtl.getEmployee());

        PdfPTable overtimeInnerTable = new PdfPTable(3);
        cell = new PdfPCell();
        cell.addElement(new Paragraph(overTime.getTotalOverTime().toString(), bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        overtimeInnerTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(overTime.getTotalAmount().divide(new BigDecimal(overTime.getTotalOverTime()))), bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        overtimeInnerTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(overTime.getTotalAmount()), bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        overtimeInnerTable.addCell(cell);


        cell = new PdfPCell(overtimeInnerTable);
        overtimeMainTable.addCell(cell);

        PdfPCell overtimeCell = new PdfPCell(overtimeMainTable);
        salaryDetailsTable.addCell(overtimeCell);
        // end overtime


        cell = new PdfPCell();
        cell.addElement(new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(BigDecimal.ZERO), bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(BigDecimal.ZERO), bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);


        FinePaymentHistory finePaymentHistory = finePaymentHistoryRepository.findByFine_EmployeeAndYearAndMonthType(monthlySalaryDtl.getEmployee(), monthlySalaryDtl.getMonthlySalary().getYear(), monthlySalaryDtl.getMonthlySalary().getMonth());

        cell = new PdfPCell();
        cell.addElement(new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(finePaymentHistory.getAmount()), bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);

        AdvancePaymentHistory advancePaymentHistory = advancePaymentHistoryRepository.findByAdvance_EmployeeAndYearAndMonthType(monthlySalaryDtl.getEmployee(), monthlySalaryDtl.getMonthlySalary().getYear(), monthlySalaryDtl.getMonthlySalary().getMonth());

        cell = new PdfPCell();
        cell.addElement(new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(advancePaymentHistory.getAmount()), bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph(CodeNodeErpUtils.currencyWithChosenLocalisation(monthlySalaryDtl.getGross()), bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);
    }


    private void createSalaryDetailsTableHeader(PdfPTable salaryDetailsTable) {
        Paragraph paragraph;
        PdfPCell serialCell = new PdfPCell(new Paragraph("S/N", bodyFont));
        salaryDetailsTable.addCell(serialCell);

        PdfPTable employeeTable = new PdfPTable(1);
        paragraph = new Paragraph("Name", bodyFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        PdfPCell employeeCellDetails = new PdfPCell(paragraph);
        employeeCellDetails.setHorizontalAlignment(Element.ALIGN_CENTER);
        employeeTable.addCell(employeeCellDetails);

        paragraph = new Paragraph("Designation", bodyFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        employeeCellDetails = new PdfPCell(paragraph);
        employeeCellDetails.setHorizontalAlignment(Element.ALIGN_CENTER);
        employeeTable.addCell(employeeCellDetails);


        paragraph = new Paragraph("Registration Serial", bodyFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        employeeCellDetails = new PdfPCell(paragraph);
        employeeCellDetails.setHorizontalAlignment(Element.ALIGN_CENTER);
        employeeTable.addCell(employeeCellDetails);

        paragraph = new Paragraph("Id No", bodyFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        employeeCellDetails = new PdfPCell(paragraph);
        employeeCellDetails.setHorizontalAlignment(Element.ALIGN_CENTER);
        employeeTable.addCell(employeeCellDetails);

        paragraph = new Paragraph("Joining Date", bodyFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        employeeCellDetails = new PdfPCell(paragraph);
        employeeCellDetails.setHorizontalAlignment(Element.ALIGN_CENTER);
        employeeTable.addCell(employeeCellDetails);

        PdfPCell employeeCell = new PdfPCell(employeeTable);


        salaryDetailsTable.addCell(employeeCell);

        PdfPCell gradeCell = new PdfPCell();
        paragraph = new Paragraph("Grade", bodyFont);
        gradeCell.addElement(paragraph);
        gradeCell.setRotation(90);
        salaryDetailsTable.addCell(gradeCell);

        PdfPCell salaryCell = new PdfPCell();
        paragraph = new Paragraph("Main Salary /", bodyFont);
        salaryCell.addElement(paragraph);
        paragraph = new Paragraph("Basic Salary /", bodyFont);
        salaryCell.addElement(paragraph);
        paragraph = new Paragraph("House Rent", bodyFont);
        salaryCell.addElement(paragraph);
        salaryCell.setRotation(90);
        salaryDetailsTable.addCell(salaryCell);

        PdfPCell mainSalaryCell = new PdfPCell();
        paragraph = new Paragraph("Main Salary", bodyFont);
        mainSalaryCell.addElement(paragraph);
        mainSalaryCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        mainSalaryCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        mainSalaryCell.setRotation(90);
        salaryDetailsTable.addCell(mainSalaryCell);

        PdfPCell houseRent = new PdfPCell();
        paragraph = new Paragraph("House Rent", bodyFont);
        houseRent.addElement(paragraph);
        houseRent.setHorizontalAlignment(Element.ALIGN_CENTER);
        houseRent.setVerticalAlignment(Element.ALIGN_MIDDLE);
        houseRent.setRotation(90);
        salaryDetailsTable.addCell(houseRent);

        PdfPCell medicalAllowanceCell = new PdfPCell();
        paragraph = new Paragraph("Medical", bodyFont);
        medicalAllowanceCell.addElement(paragraph);
        medicalAllowanceCell.addElement(new Paragraph("Allownce", bodyFont));
        medicalAllowanceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        medicalAllowanceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        medicalAllowanceCell.setRotation(90);
        salaryDetailsTable.addCell(medicalAllowanceCell);

        PdfPTable leaveMainTable = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Paragraph("Leave Days", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        leaveMainTable.addCell(cell);

        PdfPTable leaveInnerTable = new PdfPTable(4);
        cell = new PdfPCell();
        cell.addElement(new Paragraph("Regular", bodyFont));
        cell.addElement(new Paragraph("Leave", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        leaveInnerTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph("Sick", bodyFont));
        cell.addElement(new Paragraph("Leave", bodyFont));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        leaveInnerTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph("Earned Leave", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        leaveInnerTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph("Compensation", bodyFont));
        cell.addElement(new Paragraph("Leave", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        leaveInnerTable.addCell(cell);

        cell = new PdfPCell(leaveInnerTable);
        leaveMainTable.addCell(cell);

        PdfPCell leaveCell = new PdfPCell(leaveMainTable);
        salaryDetailsTable.addCell(leaveCell);


        cell = new PdfPCell(new Paragraph("Festival Leave", bodyFont));
        cell.setRotation(90);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Weekly Holidays", bodyFont));
        cell.setRotation(90);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Presence", bodyFont));
        cell.setRotation(90);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Absence", bodyFont));
        cell.setRotation(90);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Total Working", bodyFont));
        cell.addElement(new Paragraph("Days", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        salaryDetailsTable.addCell(cell);


        // overtime
        PdfPTable overtimeMainTable = new PdfPTable(1);
        cell = new PdfPCell(new Paragraph("Overtime", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        overtimeMainTable.addCell(cell);

        PdfPTable overtimeInnerTable = new PdfPTable(3);
        cell = new PdfPCell();
        cell.addElement(new Paragraph("Total overtime", bodyFont));
        cell.addElement(new Paragraph("Hour", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        overtimeInnerTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph("Overtime Hourly", bodyFont));
        cell.addElement(new Paragraph("Salary", bodyFont));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        overtimeInnerTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph("Total Overtime", bodyFont));
        cell.addElement(new Paragraph("Salary", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        overtimeInnerTable.addCell(cell);


        cell = new PdfPCell(overtimeInnerTable);
        overtimeMainTable.addCell(cell);

        PdfPCell overtimeCell = new PdfPCell(overtimeMainTable);
        salaryDetailsTable.addCell(overtimeCell);
        // end overtime


        cell = new PdfPCell();
        cell.addElement(new Paragraph("Presence Bonus", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph("Absence Fine", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Fine", bodyFont));
        cell.setRotation(90);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Advance", bodyFont));
        cell.setRotation(90);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Paragraph("Total Payable", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRotation(90);
        salaryDetailsTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Sign", bodyFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        salaryDetailsTable.addCell(cell);
    }

    private Map<Department, List<MonthlySalaryDtl>> getDepartmentAndSalaryMap(List<MonthlySalaryDtl> monthlySalaryDtls) {
        Map<Department, List<MonthlySalaryDtl>> departmentSalaryMap = new HashMap<>();
        for(MonthlySalaryDtl monthlySalaryDtl: monthlySalaryDtls){
            if(departmentSalaryMap.containsKey(monthlySalaryDtl.getEmployee().getDepartment())){
                List<MonthlySalaryDtl> monthlySalaryDtlList = departmentSalaryMap.get(monthlySalaryDtl.getEmployee().getDepartment());
                monthlySalaryDtlList.add(monthlySalaryDtl);
                departmentSalaryMap.put(monthlySalaryDtl.getEmployee().getDepartment(), monthlySalaryDtlList);
            }else{
                List<MonthlySalaryDtl> monthlySalaryDtlList = new ArrayList<>();
                monthlySalaryDtlList.add(monthlySalaryDtl);
                departmentSalaryMap.put(monthlySalaryDtl.getEmployee().getDepartment(), monthlySalaryDtlList);
            }
        }
        return departmentSalaryMap;
    }

    private PdfPTable getPageHeader(MonthlySalary monthlySalary) {
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(95);
        PdfPCell addressCell = new PdfPCell();
        Company company = companyRepository.findAll().get(0);
        Paragraph paragraph = new Paragraph(company.getName().toString(), headerFont);
        addressCell.addElement(paragraph);
        paragraph = new Paragraph(company.getAddress()!=null?company.getAddress():"", bodyFont);
        addressCell.addElement(paragraph);
        addressCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(addressCell);


        PdfPCell titleCell = new PdfPCell();
        paragraph = new Paragraph("Salary Register and Salary Slip", headerFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        titleCell.addElement(paragraph);
        titleCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(titleCell);

        PdfPCell dateCell = new PdfPCell();
        paragraph = new Paragraph();
        Chunk chunk = new Chunk("From : ", bodyFontBold);
        paragraph.add(chunk);
        chunk = new Chunk(monthlySalary.getFromDate().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), bodyFont);
        paragraph.add(chunk);

        paragraph.setAlignment(Element.ALIGN_RIGHT);
        dateCell.addElement(paragraph);

        paragraph = new Paragraph();
        chunk = new Chunk("To : ", bodyFontBold);
        paragraph.add(chunk);
        chunk = new Chunk(monthlySalary.getToDate().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), bodyFont);
        paragraph.add(chunk);
        paragraph.setAlignment(Element.ALIGN_RIGHT);

        dateCell.addElement(paragraph);

        dateCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(dateCell);
        return headerTable;
    }
}
