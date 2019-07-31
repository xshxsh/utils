package com.example.utils.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.utils.json.fastjson.entity.CustomUser;
import com.example.utils.json.fastjson.entity.User;
import org.junit.Test;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * @fileName：Test1
 * @createTime：2019-7-24 9:22
 * @author：XSH
 * @version：
 * @description：测试fastjson的序列化与反序列化
 */


public class JsonTest {
    /**
     * public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray
     * public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject
     * public static final <T> T parseObject(String text, Class<T> clazz); // 把JSON文本parse为JavaBean
     * public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray
     * public static final <T> List<T> parseArray(String text, Class<T> clazz); //把JSON文本parse成JavaBean集合
     * public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本
     * public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本
     * public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray。
     */

    /**
     * 对象转json字符串
     */
    @Test
    public void objectToJson() {
        User user = new User();
        user.setId(11L);
        user.setName("西安");
        user.setCreateTime(new Date());
        String jsonString = JSON.toJSONString(user);
        System.out.println(jsonString);
    }

    /**
     * json字符串转对象
     */
    @Test
    public void jsonToObject() {
        String jsonString = "{\"createTime\":\"2018-08-17 14:38:38\",\"id\":11,\"name\":\"西安\"}";
        User user = JSON.parseObject(jsonString, User.class);
        System.out.println(user.getName());
        System.out.println(user.getCreateTime());
    }

    /**
     * 数组转json字符串
     */
    @Test
    public void arrayTosJson() {
        User[] users = new User[2];
        User user1 = new User();
        User user2 = new User();
        user1.setName("小明");
        user1.setId(11L);
        user1.setCreateTime(new Date());
        user2.setName("小红");
        users[0] = user1;
        users[1] = user2;
        user2.setCreateTime(new Date());
        String jsonString2 = JSON.toJSONString(users);
        System.out.println(jsonString2);

        System.out.println("-----------------------------------------------------------");

        //fastjson通过SerializerFeature对生成的json格式的数据进行一些定制，比如可以输入的格式更好看，使用单引号而非双引号等
        String jsonString = JSON.toJSONString(users, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.UseSingleQuotes);
        System.out.println(jsonString);
    }

    /**
     * json字符串转数组
     */
    @Test
    public void jsonToArray() {
        String str = "[{\"createTime\":\"2019-07-24 09:41:08\",\"name\":\"小明\"}," +
                "{\"createTime\":\"2019-07-24 09:41:08\",\"name\":\"小红\"}]";
        List<User> users = JSON.parseArray(str, User.class);
        System.out.println(users);
    }

    /**
     * json字符串转Object
     */
    @Test
    public void collectionTosJson() {
        String str = "[{\"createTime\":\"2019-07-24 09:41:08\",\"name\":\"小明\"}," +
                "{\"createTime\":\"2019-07-24 09:41:08\",\"name\":\"小红\"}]";
        //TypeReference可把json成转换成指定类型的数据
        Type type = new TypeReference<List<User>>() {
        }.getType();
        List<Model> list = JSON.parseObject(str, type);
        System.out.println(list);
    }

    /**
     * 自定义序列化
     */
    @Test
    public void customTest() {
        CustomUser user = new CustomUser();
        user.setId(100L);
        user.setName("小明");
        user.setSex("男");
        user.setAddress("杭州");
        user.setPhone("18603396966");
        user.setDate(new Date());

        // User序列化
        String jsonString = JSON.toJSONString(user);
        System.out.println(jsonString);
        //{"date":"2019-07-26 11:08:09.649","sex":"男","ID":"100元","phone":"18603396966","address":"杭州"}
    }

    /**
     * 自定义反序列化
     */
    @Test
    public void strToObject() {
        String str = "{\"date\":\"2019-07-26 10:59:24.937\",\"sex\":\"男\",\"id\":\"100\",\"phone\":\"18603396966\",\"address\":\"杭州\"}";
        CustomUser customUser = JSON.parseObject(str, CustomUser.class);
        System.out.println(customUser.toString());
        //CustomUser(id=100, name=null, sex=男, address=null, phone=18603396966, date=Fri Jul 26 10:59:24 CST 2019)
    }

    /**
     * 获取项目路径方法测试
     */

    @Test
    public void getPath() throws FileNotFoundException, UnsupportedEncodingException {
        //第一种(当前执行命令时所在的目录)
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        System.out.println(path.getAbsolutePath());

        //第二种(当前执行命令时所在的目录)
        System.out.println(System.getProperty("user.dir"));

        //第三种(class文件所在目录)
        String path1 = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        System.out.println(URLDecoder.decode(path1, "utf-8"));

        //第四种(class文件所在目录)
        String path2 = ResourceUtils.getURL("classpath:").getPath();
        System.out.println(path2);

        //第五种(项目root路径)
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getDir();
        System.out.println(jarF.getPath().toString());

        //第六种(项目root路径)
        System.out.println(System.getProperty("user.dir"));
    }

    /**
     * 超大JSON数组序列化
     */
    @Test
    public void bigArraySerialize() {
        try {
            JSONWriter writer = new JSONWriter(new FileWriter("F:\\java\\后台\\code\\json\\src\\main\\resources\\static\\新文件.json"));
            writer.startArray();
            for (int i = 0; i < 10; ++i) {
                //要序列化的实体
                writer.writeValue(new Object());
            }
            writer.endArray();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 超大JSON对象序列化
     */
    @Test
    public void bigObjectSerialize() {
        try {
            JSONWriter writer = new JSONWriter(new FileWriter("F:\\java\\后台\\code\\json\\src\\main\\resources\\static\\.json"));
            writer.startObject();
            for (int i = 0; i < 10; ++i) {
                writer.writeKey("x" + i);
                writer.writeValue(new CustomUser());
            }
            writer.endObject();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 超大JSON数组反序列化
     */
    @Test
    public void bigArrayUnSerialize() {
        try {
            JSONReader reader = new JSONReader(new FileReader("F:\\java\\后台\\code\\json\\src\\main\\resources\\static\\array.json"));
            reader.startArray();
            while (reader.hasNext()) {
                Object message = reader.readObject(Object.class);
                System.out.println(message.toString());
            }
            reader.endArray();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
