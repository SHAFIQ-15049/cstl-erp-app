package software.cstl.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Department;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.service.dto.AttendanceSummaryDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttendanceSummaryReportService {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryReportService.class);

    private final AttendanceSummaryService attendanceSummaryService;

    public static Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);

    static Font TIME_ROMAN_10 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
    static Font TIMES_BOLD_10 = FontFactory.getFont(FontFactory.TIMES_BOLD, 10);
    static Font TIME_ROMAN_11 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    static Font TIMES_BOLD_11 = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
    static Font TIME_ROMAN_12 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
    static Font TIME_BOLD_12 = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    static Font TIME_ROMAN_13 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13);
    static Font TIME_BOLD_13 = FontFactory.getFont(FontFactory.TIMES_BOLD, 13);
    static Font TIME_ROMAN_14 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);
    static Font TIMES_BOLD_14 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
    static Font TIME_ROMAN_16 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16);
    static Font TIMES_BOLD_16 = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);
    static Font TIME_ROMAN_18 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18);
    static Font TIMES_BOLD_18 = FontFactory.getFont(FontFactory.TIMES_BOLD, 18);
    static Font TIME_ROMAN_20 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20);
    static Font TIMES_BOLD_20 = FontFactory.getFont(FontFactory.TIMES_BOLD, 20);

    public AttendanceSummaryReportService(AttendanceSummaryService attendanceSummaryService) {
        this.attendanceSummaryService = attendanceSummaryService;
    }

    public ByteArrayInputStream download(Long departmentId, String empId, Long employeeId, LocalDate fromDate, LocalDate toDate, AttendanceMarkedAs attendanceMarkedAs) throws DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(20, 20, 25, 25);
        document.addTitle("Attendance Summary");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new headerAndFooter());
        document.open();

        List<AttendanceSummaryDTO> attendanceSummaryDTOS = attendanceSummaryService.findAll(departmentId, empId, employeeId, fromDate, toDate, attendanceMarkedAs);



        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;
        Phrase phrase = null;

        pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{25f, 50f, 25f});

        pdfPCell = new PdfPCell();
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        phrase = new Phrase();
        phrase.add(
            new Chunk("Attendance Report", TIMES_BOLD_18));
        pdfPCell = new PdfPCell(phrase);
        pdfPCell.setFixedHeight(60f);
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        paragraph = new Paragraph("Search By:", TIME_BOLD_12);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        pdfPTable = new PdfPTable(6);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{14f, 19f, 14f, 19f, 14f, 20f});

        pdfPCell = new PdfPCell(new Paragraph("From", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(fromDate.toString(), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("To", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(toDate.toString(), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Department", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(departmentId.equals(Long.parseLong("-1")) ? "SELECT ALL" : departmentId.toString(), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("EmpId", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(empId.equals("-1") ? "NONE" : empId, TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Employee", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(employeeId.equals(Long.parseLong("-1")) ? "SELECT ALL" : employeeId.toString(), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Marked As", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(attendanceMarkedAs == null ? "SELECT ALL" : attendanceMarkedAs.toString(), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        paragraph = new Paragraph("Record (" + attendanceSummaryDTOS.size() + "):", TIME_BOLD_12);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        pdfPTable = new PdfPTable(11);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{5f, 9f, 10f, 10f, 9f, 10f, 10f, 9f, 9f, 9f, 10f});

        pdfPCell = new PdfPCell(new Paragraph("#", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Department", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Id", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Employee", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Date", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Id (Machine)", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("In", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Out", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Diff", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("OverTime", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Status", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        int serial = 0;

        for (AttendanceSummaryDTO attendanceSummaryDTO : attendanceSummaryDTOS) {
            serial++;
            pdfPCell = new PdfPCell(new Paragraph(serial + "", TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);



            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getDepartmentName(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getEmpId(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getEmployeeName(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getAttendanceDate() == null ? "" : attendanceSummaryDTO.getAttendanceDate().toString(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getEmployeeMachineId(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getInTime() == null ? "" : attendanceSummaryDTO.getInTime().toString(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getOutTime() == null ? "" : attendanceSummaryDTO.getOutTime().toString(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getDiff() == null ? "" : attendanceSummaryDTO.getDiff().toString(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getOverTime() == null ? "" : attendanceSummaryDTO.getOverTime().toString(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getAttendanceMarkedAs() == null ? "" : attendanceSummaryDTO.getAttendanceMarkedAs().toString(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
        }
        document.add(pdfPTable);
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    static class headerAndFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.BOLDITALIC, BaseColor.BLACK);
            DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
            Paragraph header = new Paragraph("Generated on " + new SimpleDateFormat("dd MMMM, yyyy").format(new Date()) + " at " + dateFormat.format(new Date()), headerFont);
            header.setAlignment(Element.ALIGN_RIGHT);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(header),
                document.right(), document.top() + 10, 0);
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            String text = String.format("Page %s", writer.getCurrentPageNumber());
            Paragraph paragraph = new Paragraph(text, mBoldFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(paragraph), (document.right() - document.left()) + document.leftMargin(), document.bottom() - 10, 0);
        }
    }

    void lineBreak(Paragraph p, int number) {
        for (int i = 0; i < number; i++) {
            p.add(new Paragraph(" "));
        }
    }
}
