package com.guo.controller;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.guo.utils.DateUtils;
import com.guo.utils.ExportExcel;
import sun.net.www.http.HttpClient;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.hutool.poi.excel.sax.AttributeName.s;

public class XinZhaoReport {

    public static void main(String[] args) throws Exception {

        String[] title = new String[11];
        title[0] = "序号";
        title[1] = "考生姓名";
        title[2] = "手机号";
        title[3] = "机构名称";
        title[4] = "总分";
        title[5] = "创建时间";
        title[6] = "学习能力";
        title[7] = "分析思维";
        title[8] = "人际理解力";
        title[9] = "适应能力";
        title[10] = "目标导向";
        List<Object[]> data = new ArrayList<>(800);

        List<Entity> d_enterprise = Db.use("d_enterprise").query("SELECT\n" +
                "\tcandidate.*,\n" +
                "\treport.id  'reportId' \n" +
                "FROM\n" +
                "\t`t_candidate` candidate \n" +
                "\tleft join t_test_report report on report.candidate_id = candidate.id\n" +
                "WHERE\n" +
                "\tcandidate.org_id IN ( 74, 107, 76, 77, 108, 75, 109, 112, 73, 56 ) \n" +
                "\tand report.id > 0\n" +
                "\tAND candidate.created > 1648742399 \n" +
                "\tAND candidate.created < 1656604800");

        int index = 1;
        for (Entity entity : d_enterprise) {
            Object[] itme = new Object[title.length];



            long id = entity.getLong("id");
            String name = entity.getStr("name");
            long orgId = entity.getLong("org_id");
            long created = entity.getLong("created");
            long mobile = entity.getLong("mobile");
            long reportId = entity.getLong("reportId");
            List<Entity> by = Db.use("d_zxbc").findBy("tbl_org", "id", orgId);
            Entity entity1 = by.get(0);
            String orgName = entity1.getStr("name");

            if (reportId > 0) {
                List<Entity> d_zxbc = Db.use("d_zxbc").findBy("t_post_match", "candidate_id", id);
                itme[4]= d_zxbc.get(0).getStr("post_match");
                // 请求接口

                //链式构建请求
                String result2 = HttpRequest.get("http://localhost:8002/report/showReportData/" + reportId)
                        .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                        .timeout(20000)//超时，毫秒
                        .execute().body();


                JSONObject parse = (JSONObject) JSONUtil.parse(result2);
                for (Map.Entry<String, Object> entry : parse.entrySet()) {
                    if (entry.getKey().equals("attributes")) {
                        JSONObject value = (JSONObject) entry.getValue();
                        for (Map.Entry<String, Object> stringObjectEntry : value.entrySet()) {
                            if (stringObjectEntry.getKey().equals("学习能力")) {
                                itme[6] = stringObjectEntry.getValue();
                            } else if (stringObjectEntry.getKey().equals("分析思维")) {
                                itme[7] = stringObjectEntry.getValue();
                            } else if (stringObjectEntry.getKey().equals("人际理解力")) {
                                itme[8] = stringObjectEntry.getValue();
                            } else if (stringObjectEntry.getKey().equals("适应能力")) {
                                itme[9] = stringObjectEntry.getValue();
                            } else if (stringObjectEntry.getKey().equals("目标导向")) {
                                itme[10] = stringObjectEntry.getValue();
                            }
                        }
                    }
                }
                itme[0] = index;
                itme[1] = name;
                itme[2] = mobile;
                itme[3] = orgName;
                itme[5] = DateUtils.stampToDate(String.valueOf(created));
                System.out.println("完成弟" + index);
                index++;
                data.add(itme);
            }
        }

        File file = new File("D:\\优秀的人前三百123.xlsx");
        ExportExcel exportExcel = new ExportExcel("4—6月测评分项得分得明细湖北公司", title, data);
        exportExcel.export(file);
    }
}
