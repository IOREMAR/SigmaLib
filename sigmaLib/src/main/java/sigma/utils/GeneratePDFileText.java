package sigma.utils;


import android.util.Log;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import sigma.manager.AppLogger;

public class GeneratePDFileText {

    private static final String TAG = GeneratePDFileText.class.getSimpleName();
    //(Definición de fuentes).
    private static final Font PARAGRAPH_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
    // Fuente para Cierre de Turno
    private static final Font SHIFT_TURN = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL);

    private static final String MPOS_APP = "MposApp";

    /**
     * genera un archivo pdf para los tickets
     *
     * @param pdfNewFile
     * @param ticket
     */
    public void createPDF(final File pdfNewFile, final String ticket) {

        try {
            final com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
            document.open();

            document.setPageSize(PageSize.A4);
            document.setMargins(20, 20, 10, 10);
            document.addAuthor(MPOS_APP);
            document.addCreator(MPOS_APP);
            final Paragraph paragraph = new Paragraph();
            paragraph.setFont(PARAGRAPH_FONT);
            paragraph.add(ticket);
            paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(paragraph);
            document.close();
        } catch (DocumentException documentException) {
            Log.d("Exception PDFFile", documentException.toString());
        } catch (FileNotFoundException e) {
            Log.d("Exception PDFFile", e.toString());
        }
    }

    /**
     * Genera un archivo PDF para los cierres de turno
     *
     * @param file
     * @param data
     */
    public void createShiftTurnPDF(final File file, final String data) {
        try {
            final com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.setPageSize(PageSize.A4);
            document.setMargins(20, 20, 10, 10);
            document.addAuthor(MPOS_APP);
            document.addCreator(MPOS_APP);
            final Paragraph paragraph = new Paragraph();
            paragraph.setFont(SHIFT_TURN);
            paragraph.add(data);
            paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            document.add(paragraph);
            document.close();
        } catch (DocumentException | FileNotFoundException exc) {
            AppLogger.LOGGER.throwing(TAG, 1, exc, exc.getMessage());
        }
    }
}
