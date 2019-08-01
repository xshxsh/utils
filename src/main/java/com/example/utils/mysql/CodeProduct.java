package com.example.utils.mysql;

import lombok.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 谢仕海
 * createDate: 2019-7-31 16:24
 * description:
 */


public class CodeProduct<Char> {
    private static final Pattern linePattern = Pattern.compile("_(\\w)");

    //指定实体生成所在包的路径
    private String packageOutPath = "com.example.utils.entity";
    //作者名字
    private String authorName = "谢仕海";

    //数据库连接
    private String URL = "jdbc:mysql://localhost:3306/imc?useUnicode=true&useSSL=false&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT%2B8";
    private String NAME = "root";
    private String PASS = "root";
    private String DRIVER = "com.mysql.cj.jdbc.Driver";

    //表名,用逗号分隔
    private static String[] tableNameArray = {"escalator_diagnosis", "escalator_info", "escalator_sensor"};

    private static String tableName = "";

    /**
     * !!!!!!!!说明!!!!!!!!!!!!
     * 生成的实体类没有显式的get和set方法，使用@Data注释生成，需要引入以下依赖
     <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.8</version>
        <optional>true</optional>
     </dependency>
     */
    public static void main(String[] args) {
        List<String> list = Arrays.asList(tableNameArray);
        for (String str : list) {
            tableName = str;
            new CodeProduct();
        }
    }

    /**
     * 主函数
     */
    public CodeProduct() {
        //创建连接
        Connection con = null;
        //查要生成实体类的表
        String sql = "show full columns from " + tableName;
        PreparedStatement pStemt = null;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(URL, NAME, PASS);
            pStemt = con.prepareStatement(sql);
            ResultSet rs = pStemt.executeQuery();
            List<ColumnEntity> list = new ArrayList<>();
            while (rs.next()) {
                ColumnEntity ce = new ColumnEntity();
                ce.setName(rs.getString("Field"));
                String type = rs.getString("Type");
                ce.setComment(rs.getString("Comment"));
                ce.setDataType(parseType(type));
                list.add(ce);
            }
            String content = parse(list);

            try {
                File directory = new File("");
                String path = this.getClass().getResource("").getPath();
                String outputPath = directory.getAbsolutePath() + "/src/main/java/" + this.packageOutPath.replace(".", "/") + "/" + replaceUnderlineAndfirstToUpper(tableName.toLowerCase(), "_", "") + ".java";
                File file = new File(outputPath);
                System.out.println("成功生成实体类: " + outputPath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(outputPath);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成类
     *
     * @param list
     * @return
     */
    private String parse(List<ColumnEntity> list) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ";\r\n");
        importPackage(sb, this.packageOutPath + "." + initcap(tableName));
        //注释部分
        infoMsg(sb, "表" + tableName + "实体类");
        //实体部分
        sb.append("@Data\r\n");
        sb.append("public class " + replaceUnderlineAndfirstToUpper(tableName.toLowerCase(), "_", "") + "{\r\n");
        sb.append("\r\n");
        addElement(sb, list);
        sb.append("}\r\n");

        return sb.toString();
    }

    /**
     * 导入包
     */
    public void importPackage(StringBuffer sb, String tableName) {
        sb.append("\r\n");
        sb.append("import java.util.Date;\r\n");
        sb.append("import java.time.LocalDateTime;\r\n");
        sb.append("import lombok.Data;\r\n");
        sb.append("\r\n");
    }

    /**
     * 添加属性
     *
     * @param sb
     * @param list
     */
    public void addElement(StringBuffer sb, List<ColumnEntity> list) {
        for (ColumnEntity ce : list) {
            sb.append("/**\r\n");
            sb.append("*" + ce.getComment() + "\r\n");
            sb.append("*/\r\n");
            sb.append("\tprivate " + ce.getDataType() + " " + lineToHump(ce.getName()) + ";\r\n");
            sb.append("\r\n");
        }
    }

    /**
     * 自动生成注释
     */
    public void infoMsg(StringBuffer sb, String msg) {
        sb.append("/**\r\n");
        sb.append("*@author " + authorName + "\r\n");
        sb.append("*@date " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")) + "\r\n");
        sb.append("*Description " + msg + "\r\n");
        sb.append("*/\r\n");
        sb.append("\r\n");
    }

    /**
     * 首字母大写
     */
    public String firstCharacterToUpper(String srcStr) {
        return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
    }

    /**
     * 替换字符串并让它的下一个字母为大写
     */
    public String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
        String newString = "";
        int first = 0;
        while (srcStr.indexOf(org) != -1) {
            first = srcStr.indexOf(org);
            if (first != srcStr.length()) {
                newString = newString + srcStr.substring(0, first) + ob;
                srcStr = srcStr.substring(first + org.length(), srcStr.length());
                srcStr = firstCharacterToUpper(srcStr);
            }
        }
        newString = firstCharacterToUpper(newString + srcStr);
        return newString;
    }

    /**
     * 下划线转驼峰
     */
    public String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 功能：将输入字符串的首字母改成大写
     */
    private String initcap(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }

    /**
     * 转换字段类型
     */
    private String parseType(String type) {
        String str = type.toLowerCase();

        if (str.contains("int")) {
            return "Integer";
        }
        if (str.contains("char")) {
            return "String";
        }
        if (str.contains("datetime")) {
            return "LocalDateTime";
        }
        if (str.contains("date")) {
            return "Date";
        }
        if (str.contains("text")) {
            return "String";
        }
        if (str.contains("double")) {
            return "Double";
        }
        return null;
    }

    @Data
    private class ColumnEntity {
        /**
         * 字段名称
         */
        private String name;

        /**
         * 字段类型
         */
        private String dataType;

        /**
         * 字段注释
         */
        private String comment;
    }

}
