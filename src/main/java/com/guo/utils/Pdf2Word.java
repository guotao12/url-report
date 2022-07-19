package com.guo.utils;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class Pdf2Word {
    public static void main(String[] args) throws IOException {
        pdf2doc("D:\\data\\XMjk2MzI=.pdf");
    }


    //移除文字水印
    public static boolean removeWatermark(File file) {
        try {
            XWPFDocument doc = new XWPFDocument(Files.newInputStream(file.toPath()));
            // 段落
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                String text=paragraph.getText();
                if("Evaluation Only. Created with Aspose.PDF. Copyright 2002-2022 Aspose Pty Ltd.".equals(text)){
                    List<XWPFRun> runs = paragraph.getRuns();
                    runs.forEach(e-> e.setText("",0));
                }
            }
            FileOutputStream outStream = new FileOutputStream(file);
            doc.write(outStream);
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    //pdf转doc(目前最大支持21页)
    public static void pdf2doc(String pdfPath) {
        long old = System.currentTimeMillis();
        try {
            //新建一个pdf文档
            String wordPath = "D:\\youxiu.docx";
            File file = new File(wordPath);
            FileOutputStream os = new FileOutputStream(file);
            //Address是将要被转化的word文档
            Document doc = new Document(pdfPath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.DocX);
            os.close();
            //去除水印
            removeWatermark(new File(wordPath));
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("Pdf 转 Word 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("Pdf 转 Word 失败...");
            e.printStackTrace();
        }
    }


}
