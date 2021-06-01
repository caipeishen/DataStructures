package cn.cps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        // 空集合并不会报空指针异常

        List<String> list = new ArrayList<>();

        list.addAll(Stream.of("1","2","3").collect(Collectors.toList()));

        List<String> collect = list.stream()
                .filter(str -> str.equals("0"))
                .filter(str -> str.equals(""))
                .sorted()
                .collect(Collectors.toList());

        System.out.println(collect.toString());
    }
}
