package com.guo.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtils {

    /*
     * 将时间戳转换为时间
     * @params s 秒
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static void main(String[] args) {

        List list = new ArrayList();
        list.add(1649382199);
        list.add(1649382242);
        list.add(1649384521);
        list.add(1649665584);
        list.add(1649725485);
        list.add(1649731625);
        list.add(1649987140);
        list.add(1649991114);
        list.add(1650006452);
        list.add(1650254379);
        list.add(1650270706);
        list.add(1650336833);
        list.add(1649380771);
        list.add(1649640543);
        list.add(1649664986);
        list.add(1649668162);
        list.add(1649671277);
        list.add(1649728509);
        list.add(1649728895);
        list.add(1649733165);
        list.add(1649748544);
        list.add(1649836360);
        list.add(1649993247);
        list.add(1650505725);
        list.add(1650509737);
        for (Object o : list) {
            System.out.println(stampToDate(o.toString()));
        }
    }


}
