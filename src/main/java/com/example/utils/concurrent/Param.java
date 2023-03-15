package com.example.utils.concurrent;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Param {

    // 并发线程数
    private Integer concurrenceNum;

    // 循环数
    private Long loopNum;

    // 运行时间
    private Long minute;

    // 主题
    private String topic;

    // key
    private String key;

    // value
    private JSONObject message;


}
