package com.guo.test;

import cn.hutool.core.img.Img;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;


public class XinzhaoSql {

    public static void main(String[] args) throws SQLException, IOException {

        FileWriter writer = new FileWriter("1.2.0.sql");


        StringBuffer sb = new StringBuffer();

        List<Entity> all = Db.use("group_db2").query("select * from t_test_record where oauth_id  > 0");

        for (Entity entity : all) {
            System.out.println();
            Object candidate_id = entity.get("candidate_id");
            Object oauth_id = entity.get("oauth_id");
            Db.use("group_db3").update(
                    Entity.create().set("oauth_id", oauth_id),
                    Entity.create("t_candidate").set("id", candidate_id));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



        writer.write(sb.toString());
        writer.flush();
        writer.close();

    }
}
