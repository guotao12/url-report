package com.guo.test;

import com.sun.javafx.scene.shape.PathUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Html2pdfUtil {


    /**
     * 转换html为pdf
     *
     * @author Zachary46
     */

    public static String parseHtml2Pdf(String url, String targe) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec( "D:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe" + " " + "D:\\phantomjs-2.1.1-windows\\bin\\html2pdf.js" + " " + url + " " + targe);
        InputStream fis = p.getInputStream();
        BufferedReader br =  new BufferedReader( new InputStreamReader(fis));
        String line =  null;
        StringBuffer cmdout =  new StringBuffer();
        while ((line = br.readLine()) !=  null) {
            cmdout.append(line);
        }

        System.out.println(cmdout);
        return cmdout.toString();
    }

    public static void main(String[] args) throws Exception {
        String result = Html2pdfUtil.parseHtml2Pdf("https://tool.lu/crontab/", "D:\\优秀.pdf");
    }


}
