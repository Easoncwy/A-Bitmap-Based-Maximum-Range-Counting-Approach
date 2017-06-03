package com.activityconflict;

import com.entity.Database;
import com.entity.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by eason on 2017/6/2.
 */
public class ReprocessRawFile {
    public void JudgeIfCrossDay(String fileName)throws Exception{
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = "";
        int count = 0;
        while ( (line = br.readLine()) != null) {
            String[] context = line.split("-->");
            String user = context[0];
            String[] time = context[1].split(",");

            String start = time[0].substring(1);
            String startDate = start.substring(0, 10);
            String end = time[1].substring(0, time[1].length() - 1);
            String endDate = end.substring(0, 10);
            if (!startDate.equals(endDate)){
                count++;
                System.out.println(user + "-->" + "[" + start + "," + end + "]");
            }

            /*
            if (count < 100) {
                System.out.println(startDate + "," + endDate);
                count++;
            } else {
                break;
            }
            */
        }

        System.out.println(count);

    }

    public void countDateRecordNumber(String fileName)throws Exception{
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = "";
//        int count = 0;

        //统计一个月内每天的记录条数.
        Map<String, Integer> map = new HashMap<>();

        while ( (line = br.readLine()) != null) {
            String[] context = line.split("-->");
            String user = context[0];
            String[] time = context[1].split(",");

            String start = time[0].substring(1);
            String startDate = start.substring(0, 10);
            String end = time[1].substring(0, time[1].length() - 1);
//            String endDate = end.substring(0, 10);

            if (map.containsKey(startDate)){
                int value = map.get(startDate)+1;
                map.put(startDate, value);
            }else {
                map.put(startDate, 1);
            }

        }
        for (String key:map.keySet()) {
            System.out.println(key + "-->" + map.get(key));

        }
    }



    public void splitRawFile(String fileName, String queryRange)throws Exception{
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = "";
        int count = 0;
        String[] queryTime = queryRange.split(",");
        String queryStart = queryTime[0].substring(1);
        String queryEnd = queryTime[1].substring(0, queryTime[1].length() - 1);
        System.out.println(queryStart + "," + queryEnd);


        /*
        while ( (line = br.readLine()) != null) {
            String[] context = line.split("-->");
            String user = context[0];
            String[] time = context[1].split(",");

            String start = time[0].substring(1);
            String startDate = start.substring(0, 10);
            String end = time[1].substring(0, time[1].length() - 1);
            String endDate = end.substring(0, 10);

        }
        */

    }
}
