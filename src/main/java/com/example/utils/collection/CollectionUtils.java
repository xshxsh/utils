// package com.example.utils.collection;
//
// import org.modelmapper.internal.util.Lists;
//
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;
//
// /**
//  * @author: shihai.xie
//  * @create: 2021-06-24 19:01
//  * @description:
//  **/
//
// public class CollectionUtils {
//
//     public void java8Stream() {
//         //按每3个一组分割
//         final Integer MAX_SEND = 3;
//         List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
//         int limit = (list.size() + MAX_SEND - 1) / MAX_SEND;
//         //方法一：使用流遍历操作
//         List<List<Integer>> mglist = new ArrayList<>();
//         Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
//             mglist.add(list.stream().skip(i * MAX_SEND).limit(MAX_SEND).collect(Collectors.toList()));
//         });
//
//
//         //方法二：获取分割后的集合
//         List<List<Integer>> splitList = Stream.iterate(0, n -> n + 1).limit(limit).parallel().map(a -> list.stream().skip(a * MAX_SEND).limit(MAX_SEND).parallel().collect(Collectors.toList())).collect(Collectors.toList());
//
//     }
//
//     public void googleGuava() {
//         //使用guava对list进行分割
//         List<User> users = userService.findAll();
//         //按每50个一组分割
//         List<List<User>> parts = Lists.partition(users, 50);
//         parts.stream().forEach(list -> {
//             process(list);
//         });
//     }
//
//     public void apacheCommonCollection() {
//         List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
//         List<List<Integer>> subs = ListUtils.partition(intList, 3);
//     }
//
//     public static <T> List<List<T>> averageAssign(List<T> source, int n) {
//         List<List<T>> result = new ArrayList<>();
//         int remainder = source.size() % n;  //(先计算出余数)
//         int number = source.size() / n;  //然后是商
//         int offset = 0;//偏移量
//         for (int i = 0; i < n; i++) {
//             List<T> value;
//             if (remainder > 0) {
//                 value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
//                 remainder--;
//                 offset++;
//             } else {
//                 value = source.subList(i * number + offset, (i + 1) * number + offset);
//             }
//             result.add(value);
//         }
//         return result;
//     }
// }
