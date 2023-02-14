package com.example.utils.uuid;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.system.HostInfo;
import cn.hutool.system.SystemUtil;
import org.apache.commons.lang3.StringUtils;

public class SnowIdUtil {
    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(getWorkId(), getDataCenterId());

    public SnowIdUtil() {
    }

    public static long nextId() {
        return SNOWFLAKE.nextId();
    }

    private static String getHostName() {
        HostInfo hostInfo = SystemUtil.getHostInfo();
        return hostInfo.getName();
    }

    private static long getWorkId() {
        HostInfo hostInfo = SystemUtil.getHostInfo();
        String hostAddress = hostInfo.getAddress();
        int[] ints = StringUtils.toCodePoints(hostAddress);
        int sums = 0;
        int[] var4 = ints;
        int var5 = ints.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            int b = var4[var6];
            sums += b;
        }

        return (long)(sums % 32);
    }

    private static long getDataCenterId() {
        try {
            String hostName = getHostName();
            int[] ints = StringUtils.toCodePoints(hostName);
            int sums = 0;
            int[] var3 = ints;
            int var4 = ints.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                int i = var3[var5];
                sums += i;
            }

            return (long)(sums % 32);
        } catch (Exception var7) {
            throw new RuntimeException("使用IP生成workId发生异常", var7);
        }
    }
}
