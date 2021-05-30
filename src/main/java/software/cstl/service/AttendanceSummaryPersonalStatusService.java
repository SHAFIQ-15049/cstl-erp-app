package software.cstl.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.service.dto.AttendanceSummaryDTO;
import software.cstl.service.dto.salary.EmployeeInfoDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttendanceSummaryPersonalStatusService {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryPersonalStatusService.class);

    private final AttendanceSummaryService attendanceSummaryService;
    private final EmployeeService employeeService;

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

    public AttendanceSummaryPersonalStatusService(AttendanceSummaryService attendanceSummaryService, EmployeeService employeeService){
        this.attendanceSummaryService = attendanceSummaryService;
        this.employeeService = employeeService;
    }

    public ByteArrayInputStream download(Long departmentId, String empId, Long employeeId, LocalDate fromDate, LocalDate toDate, AttendanceMarkedAs attendanceMarkedAs) throws DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(20, 20, 25, 25);
        document.addTitle("Job Card");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new AttendanceSummaryReportService.headerAndFooter());
        document.open();

        List<AttendanceSummaryDTO> attendanceSummaryDTOS = attendanceSummaryService.findAll(departmentId, empId, employeeId, fromDate, toDate, attendanceMarkedAs);

        Employee employee = employeeService.findByEmpId(empId);


        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;
        Phrase phrase = null;

        //title
        pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{25f, 50f, 25f});

        pdfPCell = new PdfPCell();
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        phrase = new Phrase();
        phrase.add(
            new Chunk("Single Employee Attendance", TIMES_BOLD_18));
        pdfPCell = new PdfPCell(phrase);
        pdfPCell.setFixedHeight(30f);
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

//        paragraph = new Paragraph();
//        lineBreak(paragraph, 1);
//        document.add(paragraph);

        pdfPCell = new PdfPCell();
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph("Date is between : "+fromDate.toString()+" and "+toDate.toString(),TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        //search




//        paragraph = new Paragraph("", TIME_BOLD_12);
//        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        pdfPTable = new PdfPTable(4);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{20f, 30f, 20f, 30f});

        pdfPCell = new PdfPCell(new Paragraph("Employee Name", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(employee.getName().toString(), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Department", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(employee.getDepartment().getName().toString(), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Designation", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(employee.getDesignation().getName().toString(), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Employee Id", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(empId.equals("-1") ? "NONE" : empId, TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Branch", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(employee.getCompany().getName().toString(), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Joining Date", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(employee.getJoiningDate().toString(), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        paragraph = new Paragraph("Record (" + attendanceSummaryDTOS.size() + "):", TIME_BOLD_12);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        pdfPTable = new PdfPTable(8);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{7f, 14f, 13f, 13f, 13f, 13f, 13f,14f});

        pdfPCell = new PdfPCell(new Paragraph("#", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Date", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("In Time", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Out Time", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Late Hour", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Over Time", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Extra OverTime", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph("Status", TIMES_BOLD_10));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        int serial = 0;
        int totalPresentDays = 0,totalAbsentDays = 0,totalLateDays = 0 ,totalLeaves = 0 , totalHolidays = 0,totalDays = 0;
        float totalLateHours = 0, totalOverTime = 0;

        for (AttendanceSummaryDTO attendanceSummaryDTO : attendanceSummaryDTOS) {
            serial++;
            pdfPCell = new PdfPCell(new Paragraph(serial + "", TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            pdfPTable.addCell(pdfPCell);


            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getAttendanceDate() == null ? "" : attendanceSummaryDTO.getAttendanceDate().toString(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            pdfPTable.addCell(pdfPCell);



            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getInTime() == null ? "" : getUsualTime(attendanceSummaryDTO.getInTime().toString()), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getOutTime() == null ? "" : getUsualTime(attendanceSummaryDTO.getOutTime().toString()), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);



            pdfPCell = new PdfPCell(new Paragraph("0.00", TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);



            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getOverTime() == null ? "" : getSplitOverTime(attendanceSummaryDTO.getOverTime().toString()), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            if(attendanceSummaryDTO.getOverTime() != null)
                totalOverTime += getTotalOverTime(getSplitOverTime(attendanceSummaryDTO.getOverTime().toString()));


            pdfPCell = new PdfPCell(new Paragraph("0.00", TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);


            if(attendanceSummaryDTO.getAttendanceStatus().toString().equals("Absent"))
                totalAbsentDays++;
            else
                totalPresentDays++;

            pdfPCell = new PdfPCell(new Paragraph(attendanceSummaryDTO.getAttendanceStatus() == null ? "" : attendanceSummaryDTO.getAttendanceStatus().toString(), TIME_ROMAN_10));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
        }
        document.add(pdfPTable);

        paragraph = new Paragraph("Summary:", TIME_BOLD_12);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        pdfPTable = new PdfPTable(6);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{17f, 17f, 17f, 17f,16f,16f});

        pdfPCell = new PdfPCell(new Paragraph("Total Days", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        totalDays = Math.round(getNumberOfDays(fromDate.toString(),toDate.toString())) == 0 ? 1 : Math.round(getNumberOfDays(fromDate.toString(),toDate.toString()));


        pdfPCell = new PdfPCell(new Paragraph(Integer.toString(totalDays), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Present", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(Integer.toString(totalPresentDays), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Absent", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(Integer.toString(totalAbsentDays), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Late Days", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("0", TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Late Hour", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("0.00", TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total OverTime", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);
        log.debug("total overtime "+Float.toString(totalOverTime));

        pdfPCell = new PdfPCell(new Paragraph(Float.toString(totalOverTime), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Leaves", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("0", TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Holidays", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(Integer.toString(totalDays-(totalPresentDays+totalAbsentDays)), TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("", TIME_ROMAN_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("", TIME_BOLD_12));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);


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

    public String getUsualTime(String dateTime){
        String [] temp = dateTime.split("T");

        String time,timeWithoutSecond;

        time = temp[1].toString();

        timeWithoutSecond = (String)time.subSequence(0,5);

        return timeWithoutSecond;
    }

    public String getSplitOverTime(String overTime)
    {
        String time = "";
        char [] temp = overTime.toCharArray();
        int i = 0 ;
        while(i < overTime.length()){

            if(temp[i] >= 48 && temp[i] <= 57)
                time += temp[i];

            if(temp[i] == 'H')
                time += '.';

            if(temp[i] == 'M')
                break;
            i++;
        }

        if(time.equals("0"))
            time+=".00";

        return time;
    }

    public float getNumberOfDays(String fromDate, String toDate)
    {
//        fromDate.replace('-',' ');
//        toDate.replace('-',' ');

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        float daysBetween = 0;

        try {
            Date dateBefore = myFormat.parse(fromDate);
            Date dateAfter = myFormat.parse(toDate);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            daysBetween = (difference / (1000*60*60*24));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return daysBetween;
    }

    public float getTotalOverTime(String overTime)
    {
//        String replacedString = overTime.replace(':','.');
        float temp = 0;
        try{
            temp = Float.parseFloat(overTime);
        }catch (Exception e)
        {

        }
        return temp;
    }
}
