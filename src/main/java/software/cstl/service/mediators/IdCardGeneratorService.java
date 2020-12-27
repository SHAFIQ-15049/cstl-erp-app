package software.cstl.service.mediators;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.model.IText;
import software.cstl.service.EmployeeService;
import software.cstl.service.extended.EmployeeExtService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
@Transactional(readOnly = true)
public class IdCardGeneratorService {

    Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);

    private final EmployeeExtService employeeExtService;

    public IdCardGeneratorService(EmployeeExtService employeeExtService) {
        this.employeeExtService = employeeExtService;
    }

    public ByteArrayInputStream generateIdCard(Long employeeId) throws DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        Rectangle pageSize = new Rectangle(Utilities.millimetersToInches(77), Utilities.millimetersToInches(54));
        document.setPageSize(pageSize);

        PdfPTable front = new PdfPTable(1);
        front.setWidthPercentage(100);
        front.setPaddingTop(0f);

        PdfPCell cell = new PdfPCell();
        Paragraph paragraph = new Paragraph("Good Day Apparels Ltd.", headFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        front.addCell(cell);

        document.add(front);
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
