package com.example.utils.json.fastjson.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @fileName：User
 * @createTime：2019-7-24 9:21
 * @author：XSH
 * @version：
 * @description：
 */

@Data
@ToString
public class User {

    @JSONField(name = "ID")
    private Long id;

    private String name;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;

}
