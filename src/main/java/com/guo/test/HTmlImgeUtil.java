package com.guo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author 32688
 */
public class HTmlImgeUtil {

    /**
     * 转换html为pdf
     *
     * @author Zachary46
     */

    public static String parseHtml2Img(String imgUrl, String targeFile) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec( "D:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe" + " " + "D:\\phantomjs-2.1.1-windows\\bin\\html2img.js" + " " + imgUrl + " " + targeFile + " " + 400*400);
        InputStream fis = p.getInputStream();
        BufferedReader br =  new BufferedReader( new InputStreamReader(fis));
        String line =  null;
        StringBuffer cmdout =  new StringBuffer();
            while ((line = br.readLine()) !=  null) {
                cmdout.append(line);
            }

        System.out.println(cmdout);
        return targeFile;
    }

    public static void main(String[] args) throws Exception {
        String result = parseHtml2Img("https://tool.lu/crontab/", "D:\\好玩.png");
    }
}
