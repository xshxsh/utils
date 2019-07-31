package com.example.utils.json.fastjson.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @createTime：2019-7-26 10:46
 * @author：谢仕海
 * @description：
 */

@Data
@ToString
public class CustomUser {

    /**
     * 设置属性的名称为ID，序列化的时候会把id换成ID
     */
    @JSONField(name = "ID", ordinal = 3, serializeUsing = UserIDValueSerializer.class)
    private Long id;

    @JSONField(serialize = false, ordinal = 6)
    private String name;

    @JSONField(serialize = true, ordinal = 2)
    private String sex;

    @JSONField(deserialize = false, ordinal = 5)
    private String address;

    @JSONField(deserialize = true, ordinal = 4)
    private String phone;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS", ordinal = 1)
    private Date date;
}