package app.util;

import app.model.Bill;
import app.model.Client;
import app.model.TouristicPackage;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

public class BillGenerator {

    public static void generateBill(Bill bill, Client client, TouristicPackage pack) {
        String fileName = "Invoice_" + bill.getSeries() + ".pdf";
        Document document = new Document(PageSize.A4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 22, Font.BOLD, new Color(44, 62, 80));
            Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.BLACK);
            Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);
            Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY);

            Paragraph appName = new Paragraph("TRAVEL APP", titleFont);
            appName.setAlignment(Element.ALIGN_CENTER);
            appName.setSpacingAfter(20);
            document.add(appName);

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);

            PdfPCell cellSeries = new PdfPCell(new Paragraph("SERIES: " + bill.getSeries(), boldFont));
            cellSeries.setBorder(Rectangle.NO_BORDER);
            cellSeries.setHorizontalAlignment(Element.ALIGN_LEFT);
            infoTable.addCell(cellSeries);

            PdfPCell cellDate = new PdfPCell(new Paragraph("DATE: " + bill.getPrintDate().format(formatter), boldFont));
            cellDate.setBorder(Rectangle.NO_BORDER);
            cellDate.setHorizontalAlignment(Element.ALIGN_RIGHT);
            infoTable.addCell(cellDate);

            document.add(infoTable);

            LineSeparator ls = new LineSeparator();
            ls.setLineColor(new Color(200, 200, 200));
            document.add(new Chunk(ls));
            document.add(new Paragraph(" ")); // Spațiu

            document.add(new Paragraph("CLIENT DETAILS:", boldFont));
            document.add(new Paragraph("Full Name: " + client.getName() + " " + client.getSurname(), normalFont));
            document.add(new Paragraph("Phone: " + client.getPhoneNum(), normalFont));
            document.add(new Paragraph(" ")); // Spațiu

            PdfPTable packTable = new PdfPTable(2);
            packTable.setWidthPercentage(100);
            packTable.setSpacingBefore(10f);

            addStyledRow(packTable, "Description", "Details", boldFont, true);
            addStyledRow(packTable, "Touristic Package:", pack.getName(), normalFont, false);
            addStyledRow(packTable, "Hotel:", pack.getHotel().getName(), normalFont, false);
            addStyledRow(packTable, "Stay Period:", pack.getStartDate() + " -> " + pack.getEndDate(), normalFont, false);

            document.add(packTable);
            document.add(new Paragraph(" "));

            Paragraph total = new Paragraph("TOTAL AMOUNT TO PAY: " + bill.getTotalAmountToPay() + " EUR", titleFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(20);
            document.add(total);

            document.add(new Paragraph("\n\n\n"));
            LineSeparator footerLine = new LineSeparator();
            footerLine.setLineColor(Color.LIGHT_GRAY);
            document.add(new Chunk(footerLine));

            Paragraph footer = new Paragraph("THANK YOU FOR CHOOSING OUR SERVICES!", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

            File file = new File(fileName);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addStyledRow(PdfPTable table, String col1, String col2, Font font, boolean isHeader) {
        PdfPCell c1 = new PdfPCell(new Paragraph(col1, font));
        PdfPCell c2 = new PdfPCell(new Paragraph(col2, font));

        if (isHeader) {
            c1.setBackgroundColor(new Color(240, 240, 240));
            c2.setBackgroundColor(new Color(240, 240, 240));
        }

        c1.setPadding(8f);
        c2.setPadding(8f);
        table.addCell(c1);
        table.addCell(c2);
    }
}