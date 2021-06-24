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
    Font italicFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8,Font.ITALIC);
    Font italicBoldFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8,Font.ITALIC|Font.BOLD);



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
        document.setMargins(50, 50, 50, 50);
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



        Paragraph section3 = new Paragraph("Section III", blackBoldFont);
        section3.setAlignment(Element.ALIGN_CENTER);
        document.add(section3);

        Paragraph subTitle2 = new Paragraph("(Vehicle Information)", blackFont);
        subTitle2.setAlignment(Element.ALIGN_CENTER);
        document.add(subTitle2);

        chunk = null;
        paragraph = null;
        pdfPTable = null;
        pdfPCell = null;
        phrase = null;




        pdfPTable = new PdfPTable(4);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{30f, 30f, 20f,20f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Vehicle or trailer :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getType().name().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Prev. Regn. No. (if any) :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getPrevRegnNo().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("14a. Class of vehicle :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getClassOfVehicle().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("15a. Maker's name :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getMakersName().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Type of body :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getTypeOfBody().toString().equals("null") ? " " : customer.get().getVehicle().getTypeOfBody().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Maker's country : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getMakersCountry().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Colour(cabin/body) : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getColour().name().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph(count++ +". Year of manufacture : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getYearsOfManufacture().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Number of cylinders : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getNumberOfCylinders().name().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph(count++ +". Chassis number: ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getChassisNumber().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Engine number : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getEngineNumber().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Fuel Used : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getFuelUsed().name().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Horse power : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getHorsePower().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". RPM : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getRpm().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Cubic capacity : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getCubicCapacity().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Seats (incl. driver) : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getSeats().toString()+" PERSON",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". No. of Standee : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getNoOfStandee().toString(),blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Wheel base : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getWheelBase().toString() +" MM",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Unladen weight(kgs) : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getUnladenWeight().toString() +" KG",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Maximum laden/ train weight (kgs) : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(customer.get().getVehicle().getMaxLaden().toString() +" KG",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        Paragraph section4 = new Paragraph("Section IV", blackBoldFont);
        section4.setAlignment(Element.ALIGN_CENTER);
        document.add(section4);

        Paragraph subTitle3 = new Paragraph("(Additional information for transport vehicle)", blackFont);
        subTitle3.setAlignment(Element.ALIGN_CENTER);
        document.add(subTitle3);

        chunk = null;
        paragraph = null;
        pdfPTable = null;
        pdfPCell = null;
        phrase = null;

        pdfPTable = new PdfPTable(4);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{30f, 15f, 35f,20f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". No. of tyres :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Tyres size : F-2.75x17, R-100/90 x 17",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph(count++ +". No. of axle :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Maximum axle weight(kgs)",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        document.add(pdfPTable);

        pdfPTable = new PdfPTable(4);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{30f, 20f, 40f,10f});

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("(a) Front axle  (1)      (2)",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("(b) Front axle  (1)      (2)     (3)",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("(b) Rear axle   (1)       (2)     (3)",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Dimensions (mm)",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{40f,30f,30f});

        pdfPCell = new PdfPCell(new Paragraph("    (a) Overall length 2055",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("    (b) Overall width 790",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("    (c) Overall height 1100",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Overhangs (%)",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{40f,30f,30f});

        pdfPCell = new PdfPCell(new Paragraph("    (a) Front",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("(b) Rear",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("(c) Other",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". A copy of the drawing showing the vehicle dimensions, specifications of the body" +
            "and of the seating arrangements approved by ...........................on..................... is attached herewith.",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);


        document.newPage();

        Paragraph section5 = new Paragraph("Section V", blackBoldFont);
        section5.setAlignment(Element.ALIGN_CENTER);
        document.add(section5);


        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Hire purchase/hypothecation information :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph("      The vehicle is subject to hire purchase/hypothecation with :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        document.add(new Paragraph("\n"));

        pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{60f,40f});

        pdfPCell = new PdfPCell(new Paragraph("    (a) Name :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("(b) Date :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("       Address :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Insurance information :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);


        pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{30f,30f,30f});

        pdfPCell = new PdfPCell(new Paragraph("    (a) Policy No. :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("(b) Type of policy :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("(c)Insurer's name & address :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("    (d) Date of expiry :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Joint owner information :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{50f,50f});

        pdfPCell = new PdfPCell(new Paragraph("    (a) Name. :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("(b) Name :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("     Father/Husband :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("     Father/Husband :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        Paragraph section6 = new Paragraph("Section VI", blackBoldFont);
        section6.setAlignment(Element.ALIGN_CENTER);
        document.add(section6);

        Paragraph subTitle6 = new Paragraph("(Declaration, Certificates and documents)", blackFont);
        subTitle6.setAlignment(Element.ALIGN_CENTER);
        document.add(subTitle6);


        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Declaration by owner :",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);


        pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{5f,95f});

        pdfPCell = new PdfPCell(new Paragraph(" (a)",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(" I the undersigned do hereby declare that to the best of my knowledge and belief, the information given and the\n" +
            "documents enclosed (as per list attached) are true. I also declare that in case the papers/documents and information\n" +
            "furnished are found to be incorrect at any later stage, I shall be liable for legal action.",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        document.add(new Paragraph("\n"));

        pdfPTable = new PdfPTable(5);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{20f,20f,20f,20f,20f});

        pdfPCell = new PdfPCell(new Paragraph("Date : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Signature of owner",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph("Encl : List of documents ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Registered dealer’s certificate : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);


        pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{3f,97f});

        pdfPCell = new PdfPCell(new Paragraph(" ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("I the undersigned do hereby certify that the vehicle in question has been sold by me/my firm and the ownership\n" +
            "documents attached with the application for registration are true. The information/specifications pertaining to the vehicle" +
            "are correct and the vehicle complies with all the requirements of the registration.",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        document.add(new Paragraph("\n"));

        pdfPTable = new PdfPTable(5);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{20f,20f,20f,10f,30f});

        pdfPCell = new PdfPCell(new Paragraph("Date : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Signature of registered dealer Seal",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph("Encl : List of documents ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);


        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Certificate by the Inspector of Motor Vehicles : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);


        pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{3f,97f});

        pdfPCell = new PdfPCell(new Paragraph(" ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Certificate that the particulars pertaining to the owner and the vehicle (Chassis No. .......................................Engine\n" +
            "No...................................................) given in the application match with the ownership documents attached to this" +
            "application. It is further certified that the vehicle complies with the registration requirements specified in the MV Act" +
            "and the Rules and/or Regulations made thereunder and the vehicle is not mechanically defective. The necessary" +
            "documents/papers are available as per list enclosed.",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        document.add(new Paragraph("\n"));

        pdfPTable = new PdfPTable(5);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{20f,20f,10f,10f,40f});

        pdfPCell = new PdfPCell(new Paragraph("Date : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Signature of Inspector of Motor Vehicles" +
            " Official Seal",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph("Encl : List of documents ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Registration Status : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{3f,57f,40f});

        pdfPCell = new PdfPCell(new Paragraph(" ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Registration allowed/not allowed",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Signature of Registering Authority Seal",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);


        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph(count++ +". Fees and Tax Accounts : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);


        pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{3f,97f});

        pdfPCell = new PdfPCell(new Paragraph(" ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Necessary fees and taxes amounting to taka.............................................................................................has been paid to" +
            "PO/Bank...................................................................vide vouchers and receipts enclosed.",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        document.add(new Paragraph("\n"));

        pdfPTable = new PdfPTable(5);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{20f,20f,20f,20f,20f});

        pdfPCell = new PdfPCell(new Paragraph("Signature of owner : ",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Signature of dealing assistant",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        document.add(new Paragraph("\n"));

        pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{30f,60f,20f});

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Counter signature by the registering authority.",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("",blackFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        document.add(new Paragraph("\n\n"));

        pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{70f,30f});

        pdfPCell = new PdfPCell(new Paragraph("Printed from the e-Citizen application of the Government of Bangladesh",italicBoldFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Download site : http://www.forms.gov.bd",italicFont));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);


        document.add(pdfPTable);

        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
