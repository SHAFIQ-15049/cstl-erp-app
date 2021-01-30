package software.cstl.service.mediators;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.languages.IndicLigaturizer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.model.IText;
import software.cstl.domain.Employee;
import software.cstl.service.EmployeeService;
import software.cstl.service.extended.EmployeeExtService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Component
@Transactional(readOnly = true)
public class IdCardGeneratorService {

    Font headFontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 3);
    Font headFont = FontFactory.getFont(FontFactory.HELVETICA, 3);

    private final EmployeeExtService employeeExtService;

    public IdCardGeneratorService(EmployeeExtService employeeExtService) {
        this.employeeExtService = employeeExtService;
    }

    public ByteArrayInputStream generateIdCard(Long employeeId) throws DocumentException, IOException {

        Employee employee = employeeExtService.findOne(employeeId).get();
        Rectangle pageSize = new Rectangle(225f, 327f);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        document.setPageSize(pageSize);
        document.setMargins(2f,2f,2f,2f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();


//        document.setPageSize(pageSize);

        PdfPTable front = new PdfPTable(1);
        front.setSplitLate(false);
        front.setSplitRows(false);
        front.setWidthPercentage(28f);


        PdfPCell cell = new PdfPCell();
        cell.setPaddingTop(-1f);
        cell.setPaddingBottom(3f);
        Paragraph paragraph = new Paragraph("Good Day Apparels Ltd.", headFontBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        front.addCell(cell);

        cell = new PdfPCell();
        cell.setPaddingTop(-1f);
        cell.setPaddingBottom(3f);

        paragraph = new Paragraph("H 79, Block D, Chairmanbari,", headFontBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);

        paragraph = new Paragraph("Banani Dhaka-1213", headFontBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.LEFT| Rectangle.RIGHT);
        front.addCell(cell);

        Image image = Image.getInstance(employee.getPersonalInfo().getPhoto());

        image.scaleAbsolute(25f, 25f);
        PdfPCell imageCell = new PdfPCell(image);
        imageCell.setBorder(Rectangle.NO_BORDER);
        imageCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        imageCell.setVerticalAlignment(Element.ALIGN_CENTER);

        front.addCell(imageCell);

        cell = new PdfPCell();
        cell.setPaddingTop(-.4f);


        float[] columnSize = {25f, 75f};
        PdfPTable infoTable = new PdfPTable(columnSize);
        infoTable.setWidthPercentage(100);
        PdfPCell infoCell = new PdfPCell(new Paragraph("Name", headFont));

        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);

        byte[] bytes = employee.getPersonalInfo().getBanglaName().getBytes(StandardCharsets.US_ASCII);
        StringBuilder sb = new StringBuilder();
        for(byte b: bytes){
            sb.append(String.format("x%02x", b));
        }

        IndicLigaturizer indicLigaturizer;

        BaseFont base = BaseFont.createFont("D://kalpurush.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(base, 11f, Font.BOLD);
        infoCell = new PdfPCell(new Paragraph(": "+  employee.getPersonalInfo().getBanglaName(), headFont));

        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);

        infoCell = new PdfPCell(new Paragraph("Code", headFont));
        infoCell.setPaddingBottom(-.2f);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);

        infoCell = new PdfPCell(new Paragraph(": "+ employee.getLocalId(), headFont));
        infoCell.setPaddingBottom(-.2f);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);

        infoCell = new PdfPCell(new Paragraph("Desig", headFont));
        infoCell.setPaddingBottom(-.2f);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);

        infoCell = new PdfPCell(new Paragraph(": "+ employee.getDesignation().getName().toUpperCase(), headFont));
        infoCell.setPaddingBottom(-.2f);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);

        infoCell = new PdfPCell(new Paragraph("Dept", headFont));
        infoCell.setPaddingBottom(-.2f);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);

        infoCell = new PdfPCell(new Paragraph(": "+ employee.getDepartment().getName().toUpperCase(), headFont));
        infoCell.setPaddingBottom(-.2f);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);

        infoCell = new PdfPCell(new Paragraph("Gend", headFont));
        infoCell.setPaddingBottom(-.2f);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);

        infoCell = new PdfPCell(new Paragraph(": "+ employee.getPersonalInfo().getGender(), headFont));
        infoCell.setPaddingBottom(-.2f);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);


        infoCell = new PdfPCell(new Paragraph("J Date", headFont));
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        infoCell = new PdfPCell(new Paragraph(": "+ employee.getJoiningDate().format(formatter), headFont));
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(infoCell);

        cell.addElement(infoTable);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);

        front.addCell(cell);

        PdfPTable footerTable = new PdfPTable(2);
        footerTable.setWidthPercentage(95);
        PdfPCell footerTableCell = new PdfPCell(new Paragraph("Holder's Sign", headFont));
        footerTableCell.setPaddingTop(5f);
        footerTableCell.setPaddingBottom(3f);
        footerTableCell.setBorder(Rectangle.NO_BORDER);
        footerTable.addCell(footerTableCell);

        footerTableCell = new PdfPCell(new Paragraph("Auth. Signature", headFont));
        footerTableCell.setPaddingTop(5f);
        footerTableCell.setPaddingBottom(3f);

        footerTableCell.setBorder(Rectangle.NO_BORDER);
        footerTableCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        footerTable.addCell(footerTableCell);
        cell = new PdfPCell(footerTable);

        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
        cell.setPadding(0);
        front.addCell(cell);

        front.setSpacingBefore(0f);
        front.setSpacingAfter(0f);
        Paragraph emptyParagraph = new Paragraph(" ");
        emptyParagraph.setPaddingTop(-10f);
        //document.add(emptyParagraph);
        document.add(new Paragraph(0, "\u00a0"));
        document.add(front);
        document.add(new Paragraph(-20, "\u00a0"));

        document.newPage();
        //document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 3.6f);
        paragraph = new Paragraph("GOOD DAY APPARELS LTD.", headerFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setPaddingTop(10f);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        Font timesFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 3.2f);
        paragraph = new Paragraph("House # 79, Block-D", timesFont);
        paragraph.setPaddingTop(1f);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph);

        paragraph = new Paragraph("International Airport Road", timesFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph("Chairman Bari, Banani", timesFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph("Dhaka-1213, Bangladesh", timesFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph("Phone: 02-9886834,8812593", timesFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable backFooter = new PdfPTable(1);
        backFooter.setSpacingBefore(4f);
        backFooter.setWidthPercentage(22);
        cell = new PdfPCell(new Paragraph("IF FOUND, PLEASE RETURN", FontFactory.getFont(FontFactory.HELVETICA_BOLD,2.8f)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.TOP);
        cell.setBorderWidth(.4f);
        cell.setPaddingBottom(-1f);


        backFooter.addCell(cell);

        cell = new PdfPCell(new Paragraph("THIS CARD ABOVE ADDRESS", FontFactory.getFont(FontFactory.HELVETICA_BOLD,2.8f)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);

        backFooter.addCell(cell);
        document.add(backFooter);
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
