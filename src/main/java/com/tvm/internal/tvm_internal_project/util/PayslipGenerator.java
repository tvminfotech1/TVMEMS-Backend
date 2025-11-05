package com.tvm.internal.tvm_internal_project.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.tvm.internal.tvm_internal_project.model.PayRoleEmployee;
import com.tvm.internal.tvm_internal_project.model.SalaryHistory;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class PayslipGenerator {

    private static final String OUTPUT_DIR = "D:/payslips/";
    private static final String COMPANY_NAME = "TVM InfoTech Private Ltd.,";


    public static String generatePayslip(PayRoleEmployee emp, SalaryHistory salary) {

        String fileName = OUTPUT_DIR + emp.getFirstName() + "_" + emp.getLastName() + "_" + emp.getId() + "_" + salary.getMonth() + ".pdf";
        String logoPath = "src/main/resources/static/TVM_Infotech_Logo.jpg";
        try {
            Document document = new Document(PageSize.A4, 36, 36, 54, 36);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_LEFT);

            PdfPTable header = new PdfPTable(2);
            header.setWidthPercentage(100);
            header.setWidths(new float[]{1, 3});

            PdfPCell logoCell = new PdfPCell(logo, false);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            header.addCell(logoCell);

            String formattedMonth = DateUtils.formatMonthYear(salary.getMonth());
            Font companyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new BaseColor(0, 102, 204));
            PdfPCell companyCell = new PdfPCell(new Phrase(COMPANY_NAME + "\nPayslip for " + formattedMonth, companyFont));
            companyCell.setBorder(Rectangle.NO_BORDER);
            companyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            header.addCell(companyCell);

            document.add(header);

            LineSeparator ls = new LineSeparator();
            ls.setLineColor(new BaseColor(0, 102, 204));
            document.add(new Chunk(ls));

            document.add(new Paragraph(" ")); // spacing

            Font sectionTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new BaseColor(0, 51, 102));
            document.add(new Paragraph("Employee Details", sectionTitle));

            PdfPTable empTable = new PdfPTable(2);
            empTable.setWidthPercentage(100);
            empTable.addCell(getCell("Employee Name:", Element.ALIGN_LEFT, true));
            empTable.addCell(getCell(emp.getFirstName() + " " + emp.getLastName(), Element.ALIGN_LEFT, false));
            empTable.addCell(getCell("Employee ID:", Element.ALIGN_LEFT, true));
            empTable.addCell(getCell(emp.getId().toString(), Element.ALIGN_LEFT, false));
            empTable.addCell(getCell("Month:", Element.ALIGN_LEFT, true));
            empTable.addCell(getCell(formattedMonth, Element.ALIGN_LEFT, false));
            empTable.addCell(getCell("Generated On:", Element.ALIGN_LEFT, true));
            empTable.addCell(getCell(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")), Element.ALIGN_LEFT, false));

            document.add(empTable);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Earnings & Deductions", sectionTitle));

            PdfPTable salaryTable = new PdfPTable(2);
            salaryTable.setWidthPercentage(100);
            salaryTable.setSpacingBefore(10);

            salaryTable.addCell(getCell("Basic Salary", Element.ALIGN_LEFT, true));
            salaryTable.addCell(getCell(String.format("₹ %.2f", salary.getBasicSalary()), Element.ALIGN_RIGHT, false));
            salaryTable.addCell(getCell("House Rent Allowance (HRA)", Element.ALIGN_LEFT, true));
            salaryTable.addCell(getCell(String.format("₹ %.2f", salary.getHra()), Element.ALIGN_RIGHT, false));
            salaryTable.addCell(getCell("Deductions", Element.ALIGN_LEFT, true));
            salaryTable.addCell(getCell(String.format("₹ %.2f", salary.getLeaveDeduction() + salary.getOtherDeduction()), Element.ALIGN_RIGHT, false));

            PdfPCell netLabel = getCell("Net Pay", Element.ALIGN_LEFT, true);
            netLabel.setBackgroundColor(new BaseColor(204, 229, 255));
            PdfPCell netValue = getCell(String.format("₹ %.2f", salary.getNetPay()), Element.ALIGN_RIGHT, true);
            netValue.setBackgroundColor(new BaseColor(204, 229, 255));

            salaryTable.addCell(netLabel);
            salaryTable.addCell(netValue);

            document.add(salaryTable);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("This is a system-generated payslip and does not require a signature.", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY)));

            document.close();
            System.out.println("Payslip generated successfully: " + OUTPUT_DIR);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private static PdfPCell getCell(String text, int alignment, boolean bold) {
        Font font = bold ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK) : FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(6);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }
}
