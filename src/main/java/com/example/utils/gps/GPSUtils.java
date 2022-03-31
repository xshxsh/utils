package com.example.utils.gps;

import java.text.NumberFormat;

/**
 * @author shihai.xie
 * @create 2022-03-31 11:03:49
 * @description:
 */

public class GPSUtils {

    //经纬度，度转度分秒
    private String duToDuFen(double data) {
        StringBuffer result = new StringBuffer();
        if (data <= 0) {
            data = - data;
        }
        //得到度
        int du = (int) data;
        result = result.append(du);
        //得到分
        double du_Decimal = data - du;
        int min = (int) (du_Decimal * 60);
        if (min <= 9) {
            result.append("0").append(min);
        } else {
            result.append(min);
        }
        //得到秒
        double min_Decimal = du_Decimal * 60 - min;
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留4位小数
        nf.setMaximumFractionDigits(4);
        String format = nf.format(min_Decimal).substring(1);
        result = result.append(format);
        return result.toString();
    }
}
