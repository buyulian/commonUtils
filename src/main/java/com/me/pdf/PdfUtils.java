package com.me.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author buyulian
 * @date 2020/6/10
 */
public class PdfUtils {

    public static void splitPdf(InputStream inputStreamParam,
                                OutputStream outputStream,
                                int fromPage,
                                int toPage) {
        Document document = new Document();
        try (InputStream inputStream = inputStreamParam){
            PdfReader pdfReader = new PdfReader(inputStream);

            int totalPages = pdfReader.getNumberOfPages();

            //make fromPage equals to toPage if it is greater
            if(fromPage > toPage ) {
                fromPage = toPage;
            }
            if(toPage > totalPages) {
                toPage = totalPages;
            }

            PdfCopy pdfCopy = new PdfCopy(document, outputStream);

            document.open();
            PdfImportedPage page;

            while(fromPage <= toPage) {
                document.newPage();
                page = pdfCopy.getImportedPage(pdfReader, fromPage);
                pdfCopy.addPage(page);
                fromPage++;
            }
            outputStream.flush();
            pdfCopy.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mergePdf(List<InputStream> inputStreamParamList,
                                OutputStream outputStream) {
        Document document = new Document();
        PdfCopy pdfCopy = null;
        try {
            pdfCopy = new PdfCopy(document, outputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.open();

        for (InputStream inputStreamItem : inputStreamParamList) {

            try (InputStream inputStream = inputStreamItem){
                PdfReader pdfReader = new PdfReader(inputStream);

                int totalPages = pdfReader.getNumberOfPages();

                PdfImportedPage page;

                int fromPage = 1;
                while(fromPage <= totalPages) {
                    document.newPage();
                    page = pdfCopy.getImportedPage(pdfReader, fromPage);
                    pdfCopy.addPage(page);
                    fromPage++;
                }
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        pdfCopy.close();

    }

}
