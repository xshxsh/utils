package com.example.utils.nio;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author 谢仕海
 * createDate: 2019-7-30 10:22
 * description:
 */

public class NioTest {
    /**
     * Path 既可以表示一个目录，也可以表示一个文件，就像 File 那样——当然了，Path 就是用来取代 File 的。
     * 1）可以通过 Paths.get() 创建一个 Path 对象，此时 Path 并没有真正在物理磁盘上创建；参数既可以是一个文件名，也可以是一个目录名；绝对路径或者相对路径均可。
     * 2）可以通过 Files.notExists() 确认 Path（目录或者文件） 是否已经存在。
     * 3）可以通过 Files.createDirectory() 创建目录，此时目录已经在物理磁盘上创建成功，可通过资源管理器查看到。
     * 4）可以通过 Files.createFile() 创建文件，此时文件已经在物理磁盘上创建成功，可通过资源管理器查看到。
     * 5）可以通过 toAbsolutePath() 查看 Path 的绝对路径。
     * 6）可以通过 resolve() 将 Path 连接起来，参数可以是一个新的 Path 对象，也可以是对应的字符串。
     */

    /**
     * 创建文件夹和文件
     */
    @Test
    public void createFile() {

        // 获取项目路径
        String property = System.getProperty("user.dir");
        // 相对路径
        Path dir = Paths.get(property + "/src/main/resources/file");
        // 如果目录不存在，则创建目录
        if (Files.notExists(dir)) {
            try {
                Files.createDirectory(dir);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        // 通过 resolve 方法把 dir 和 demo.txt 链接起来
        Path file = dir.resolve("demo.txt");

        if (Files.notExists(file)) {
            try {
                // 如果文件不存在，则创建文件
                Files.createFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将 File 转换为 Path
     */
    @Test
    public void fileToPath() {
        // 将 File 转换为 Path，可以通过 File 类的 toPath() 方法完成
        File file = new File("沉默王二.txt");
        Path path = file.toPath();
        System.out.println(path.toAbsolutePath());
    }

    /**
     * 将 Path 转换为 File
     */
    @Test
    public void pathToFile() {
        // 将 Path 转换为 File，可以通过 Path 类的 toFile() 方法完成
        Path path = Paths.get("沉默王二.txt");
        File file = path.toFile();
        System.out.println(file.getAbsolutePath());
    }

    /**
     * 查找指定目录下的 指定 后缀的文件
     */
    @Test
    public void handleDirectory() {

        // 相对路径
        Path dir = Paths.get("src/main/java/com/example/utils/json/fastjson/entity");

        // 查找指定目录下的 java 后缀的文件
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.java")) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理目录树,目录树意味着一个目录里既有文件也有子目录，也可能都没有，也可能有其一
     */
    @Test
    public void handleDirectoryTree() {

        // 相对路径
        Path dir = Paths.get("src");

        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.toString().endsWith(".java")) {
                        System.out.println(file.getFileName());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件
     */
    @Test
    public void copyFile() {

        Path source = Paths.get("src/main/resources/file/array.json");
        Path target = Paths.get("src/main/resources/file/沉默王二.txt");
        try {
            Files.copy(source, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     */
    @Test
    public void deleteFile() {

        Path file = Paths.get("src/main/resources/file/array.json");
        try {
            //            Files.delete(file);
            // 使用 Files.exists() 判断文件是否存在
            Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动文件
     */
    @Test
    public void moveFile() {

        Path source = Paths.get("src/main/resources/file/array.json");
        Path target = Paths.get("src/main/resources/file/沉默王二.txt");
        try {
            Files.move(source, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 快速写文件
     */
    @Test
    public void writeFile() {

        Path path = Paths.get("src/main/resources/file/test.txt");
        //已追加的形式写文件
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            //换行
            writer.newLine();
            writer.write("一个有趣的程序员");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 快速读文件
     */
    @Test
    public void readFile() {

        Path path = Paths.get("src/main/resources/file/test.txt");
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步读文件,callback方式
     */
    @Test
    public void nioFuture() throws Exception {

        Path path = Paths.get("src/main/resources/file/test.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                System.out.println(new String(attachment.array()));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println(exc.getMessage());
            }
        });

        System.out.println("主线程继续做事情");
    }

}
