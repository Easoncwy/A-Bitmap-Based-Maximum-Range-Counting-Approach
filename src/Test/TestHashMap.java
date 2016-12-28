package Test;

import Main.Main;

import java.util.Map;
import java.util.*;

/**
 * Created by eason on 2016/12/27.
 */
public class TestHashMap {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(0,"a");
        map.put(1,"b");
        map.put(2,"c");
        map.put(3,"d");

        for (Integer order: map.keySet()) {
            String value = map.get(order);
            System.out.println(order + ":" + value);

        }
        System.out.println("*****************");
        map.remove(1);


        for (Integer order:map.keySet()) {

            String key = map.get(order);
            int temp = -1;
            if (order > 1){
                temp = order - 1;
            }
            map.put(temp,key);

        }

//        SortedMap sortedMap = t




        for (Integer order: map.keySet()) {
            String value = map.get(order);
            System.out.println(order + ":" + value);

        }

    }



}
