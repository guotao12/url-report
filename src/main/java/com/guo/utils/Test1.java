package com.guo.utils;

import cn.hutool.core.collection.ListUtil;
import com.guo.jdbc.MongoDBDaoImpl;
import com.guo.vo.PercentStandardScore;
import com.mongodb.client.model.Filters;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.map.LinkedMap;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Test1 {


    public static void main(String[] args) throws Exception {

        MongoDBDaoImpl mongoDBDao = new MongoDBDaoImpl();
        Bson eq = Filters.eq("gameCode", "M01");

        List<BigDecimal>  original = new ArrayList<>(300);
        List<Document> documents = mongoDBDao.find("d_game_front", "coll_game_record_data", eq);
        for (Document document: documents) {
            Object abilityData = document.get("abilityData");
            if (abilityData != null) {
                Document abilityDataS = (Document) abilityData;
                Object o = abilityDataS.get("WC18");
                BigDecimal origina = BigDecimal.valueOf((Double) o).setScale(4, RoundingMode.HALF_DOWN);
                original.add(origina);
            }
        }


        // 降序
        original.sort((o1, o2) -> {
            if (o1.compareTo(o2) > 0) {
                return -1;
            } else if(o1.compareTo(o2) < 0) {
                return 1;
            } else {
                return 0;
            }
        });
        for (BigDecimal decimal : original) {
            System.out.println(decimal.toString());
        }
//
//        // 同级每个数字出现
//        Map<BigDecimal, Integer> map = new LinkedMap<>(200);
//        for (BigDecimal decimal : original) {
//            System.out.println(original);
//            if (map.get(decimal) != null) {
//                Integer sum = map.get(decimal);
//                map.put(decimal, sum + 1);
//            } else {
//                map.put(decimal, 1);
//            }
//        }
//
//        LinkedMap<BigDecimal, PercentStandardScore> scoreLinkedMap = new LinkedMap<>(200);
//
//        // 初始百分比
//
//        BigDecimal desPercent = BigDecimal.ZERO;
//
//        // 降序处理
//        for (Map.Entry<BigDecimal, Integer> decimalIntegerEntry : map.entrySet()) {
//
//            BigDecimal score = decimalIntegerEntry.getKey();
//
//            BigDecimal sum = BigDecimal.valueOf(decimalIntegerEntry.getValue());
//
//            BigDecimal divide = sum.divide(BigDecimal.valueOf(original.size()), 5,  RoundingMode.HALF_UP);
//
//            desPercent = desPercent.add(divide);
//
//            PercentStandardScore percentStandardScore = new PercentStandardScore();
//            percentStandardScore.setScore(score);
//            percentStandardScore.setDesPercent(desPercent);
//            scoreLinkedMap.put(score, percentStandardScore);
//        }
//
//        // 升序处理
//        original.sort(BigDecimal::compareTo);
//        // 同级每个数字出现
//        Map<BigDecimal, Integer> aseMap = new LinkedMap<>(200);
//        for (BigDecimal decimal : original) {
//
//            if (aseMap.get(decimal) != null) {
//                Integer sum = aseMap.get(decimal);
//                aseMap.put(decimal, sum + 1);
//            } else {
//                aseMap.put(decimal, 1);
//            }
//        }
//
//        BigDecimal asePercent = BigDecimal.ZERO;
//        for (Map.Entry<BigDecimal, Integer> decimalIntegerEntry : aseMap.entrySet()) {
//            PercentStandardScore percentStandardScore1 = scoreLinkedMap.get(decimalIntegerEntry.getKey());
//            BigDecimal sum = BigDecimal.valueOf(decimalIntegerEntry.getValue());
//            BigDecimal divide = sum.divide(BigDecimal.valueOf(original.size()), 5,  RoundingMode.HALF_UP);
//            asePercent = asePercent.add(divide);
//            percentStandardScore1.setAsePercent(asePercent);
//        }



//        ArrayList<Object[]> list = new ArrayList<>();
//
//        for (Map.Entry<BigDecimal, PercentStandardScore> percentStandardScoreEntry : scoreLinkedMap.entrySet()) {
//            PercentStandardScore value = percentStandardScoreEntry.getValue();
//            Object[] o = new Object[5];
//            o[0] = "";
//            o[1] = value.getScore();
//            o[2] = value.getDesPercent();
//            o[3] = value.getAsePercent();
//            list.add(o);
//        }
//
//        File file = new File("D:\\WS29.xls");
//        ExportExcel exportExcel = new ExportExcel("明细", new String[]{"序号", "原始分数", "百分比降序", "百分比升序", "全部原始分数" }, list);
//        exportExcel.export(file);
    }

}
