package com;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PdfConverter {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();

        String inputStr = "C:\\softwares\\Issue_logs\\docs\\2020-07-23.docx";
        String outputStr = "C:\\softwares\\Issue_logs\\docs\\doc_to_pdf_1.pdf";
        InputStream in = new BufferedInputStream(new FileInputStream(new File(inputStr)));
        IConverter converter = LocalConverter.builder()
                .baseFolder(new File(inputStr))
                .workerPool(20, 25, 2, TimeUnit.SECONDS)
                .processTimeout(5, TimeUnit.SECONDS)
                .build();

        Future<Boolean> conversion = converter
                .convert(in).as(DocumentType.MS_WORD)
                .to(bo).as(DocumentType.PDF)
                .prioritizeWith(1000) // optional
                .schedule();
        conversion.get();
                try (OutputStream outputStream = new FileOutputStream(new File(outputStr))) {
            bo.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        in.close();
        bo.close();
        System.out.println("Successfully done");
    }
}
