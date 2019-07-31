package com.example.utils.json.fastjson.entity;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.lang.reflect.Type;

/**
 * @createTime：2019-7-26 10:47
 * @author：谢仕海
 * @description：
 */


public class UserIDValueSerializer  implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        Long value = (Long) object;
        String text = value + "元";
        serializer.write(text);
    }
}