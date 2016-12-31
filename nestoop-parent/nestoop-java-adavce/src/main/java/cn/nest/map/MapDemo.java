package cn.nest.map;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Botter
 *
 * @DATE 2016/12/27
 * @DESC
 */
public class MapDemo {
    static ConcurrentHashMap<String, List<String>> concurrentHashMap = new ConcurrentHashMap();

    public static void concurrentHashMapTest() {
        if (concurrentHashMap.containsKey("admin")) {
            concurrentHashMap.get("admin").stream().filter(String::isEmpty).map(s -> s.toUpperCase()).collect(Collectors.toList()).forEach(System.out::println);
        } else {
            concurrentHashMap.put("admin", new CopyOnWriteArrayList<>(new String[]{"rterer"}));
            List<String> stringList = concurrentHashMap.putIfAbsent("admin", new CopyOnWriteArrayList<>(new String[]{"1212", "34534"}));
            String s = stringList.get(0);
            System.out.println(s);
        }
    }

    public static void main(String[] args) {

        concurrentHashMapTest();

        Map<String, String> map = new ConcurrentHashMap<>();

        String value = map.putIfAbsent("admin", "maxwit");
        System.out.println(value);
        System.out.println(map.get("admin"));
        String oldvalue = map.putIfAbsent("admin", "maxwit111");
        System.out.println(oldvalue);
        System.out.println(map.get("admin"));
        String oldvalue1 = map.putIfAbsent("admin", "maxwit3333");
        System.out.println(oldvalue1);
        System.out.println(map.get("admin"));
        String oldvalue2 = map.put("admin", "maxwit4444");
        System.out.println(oldvalue2);
        System.out.println(map.get("admin"));

    }

}
