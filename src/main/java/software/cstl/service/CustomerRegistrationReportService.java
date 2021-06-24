package software.cstl.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Customer;
import software.cstl.web.rest.AccountResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

@Service
@Transactional
public class CustomerRegistrationReportService {

    private final Logger log = LoggerFactory.getLogger(CustomerRegistrationReportService.class);
    Font headFontBold = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,Font.BOLD);
    Font headFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,Font.UNDERLINE);
    Font redFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,Font.BOLD);
    Font blackFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
    Font blackBoldFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,Font.BOLD);
    Font blackBoldUnderlineFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,Font.BOLD|Font.UNDERLINE);



    private CustomerService customerService;

    public CustomerRegistrationReportService(CustomerService customerService)
    {
        this.customerService  = customerService;
    }


    public ByteArrayInputStream download(Long customerId) throws DocumentException {

        redFont.setColor(BaseColor.RED);

        log.debug("Customer Id {}",customerId);





//        Rectangle pageSize = new Rectangle(225f, 327f);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();

        document.setPageSize(PageSize.A4);
        document.setMargins(50, 50, 25, 25);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();


        Paragraph pTitle = new Paragraph("FORM OF APPLICATION FOR THE REGISTRATION OF MOTOR VEHICLE", headFontBold);
        pTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(pTitle);

        Paragraph pTitle2 = new Paragraph("To be filled in by the office", headFont);
        pTitle2.setAlignment(Element.ALIGN_CENTER);
        document.add(pTitle2);

        Paragraph section1 = new Paragraph("Section I", redFont);
        section1.setAlignment(Element.ALIGN_CENTER);
        document.add(section1);


        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;
        Phrase phrase = null;

        //title
        pdfPTable = new PdfPTable(4);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{30f, 30f, 20f,20f});

        pdfPCell = new PdfPCell(new Paragraph("Regn. No. :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Date :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Prev. Regn. No.(if any)",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);





        pdfPCell = new PdfPCell(new Paragraph("Issue. No. :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Date :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Issued by : ",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);




        pdfPCell = new PdfPCell(new Paragraph("Diary No. :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Date :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Received by : ",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);



        pdfPCell = new PdfPCell(new Paragraph("Customer ID :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("District :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Vehicle ID",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph("Veh. Description :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Call on date :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph("Refusal date :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Refusal Code :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Refused by :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph("P.O./ Bank :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Index No. :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph("Remarks (if any) :",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",redFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        document.add(pdfPTable);

        document.add(new Paragraph("\n"));

//


        Paragraph pTitle3 = new Paragraph("To be filled in by the Owner", blackBoldUnderlineFont);
        pTitle3.setAlignment(Element.ALIGN_CENTER);
        document.add(pTitle3);

        Paragraph section2 = new Paragraph("Section II", blackBoldFont);
        section2.setAlignment(Element.ALIGN_CENTER);
        document.add(section2);

        Paragraph subTitle1 = new Paragraph("(Owner Information)", blackFont);
        subTitle1.setAlignment(Element.ALIGN_CENTER);
        document.add(subTitle1);

        chunk = null;
        paragraph = null;
        pdfPTable = null;
        pdfPCell = null;
        phrase = null;
        int count = 1;


        Optional<Customer> customer = customerService.findOne(customerId);


        pdfPTable = new PdfPTable(4);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{30f, 30f, 20f,20f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Name of owner :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getName().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Date of birth :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getDateOfBirth().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Father/ Husband :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getFatherOrHusband().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Nationality :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getNationality().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Sex :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getSex().toString().equals("null") ? " " : customer.get().getSex().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Guardian's name : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getGuardiansName().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Owner's Address (one only) : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getAddress().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Phone No. (If any) : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getPhoneNo().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". P.O./Bank : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getPoOrBank().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph(count++ +". Joint owner: ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Owner type : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Hire : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Hire purchase : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);


        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
