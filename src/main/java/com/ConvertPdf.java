package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

public class ConvertPdf {

    public static void main(String[] args) {

        String inputStr = "C:\\softwares\\Issue_logs\\docs\\2020-07-23.docx";
        String outputStr = "C:\\softwares\\Issue_logs\\docs\\doc_to_pdf_1.pdf";
        File inputWord = new File(inputStr);
        File outputFile = new File(outputStr);
        try  {
            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputFile);
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
            outputStream.close();
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
