package com.activityconflict;

import com.entity.Database;
import com.entity.User;
import com.entity.Interval;
import com.query.Time;

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


    public void splitRawFile(
            String fileName,
            String queryRange,
            Database db,
            long duration,
            Map<Integer, User> markUserMap,
            ArrayList<String> allTimeArray,
            Set<String> allStartTimeSet,
            Set<String> allUsers)throws Exception
    {
        Time TIME = new Time();
        long min = Integer.MAX_VALUE,max =-1;
        long startTIME = 0;
        long endTIME = 0;

        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = "";
        String preUser = "";
        int validRecordCount = 0;
        int markUserFigure = 0;
        Map<String, Boolean> markStartEndMap = new HashMap<>();

        //对查询范围做一个字符串分割处理.
        String[] rangeTime = queryRange.split(",");
        String rangeStart = rangeTime[0].substring(1);
        String rangeEnd = rangeTime[1].substring(0, rangeTime[1].length() - 1);
        System.out.println("查询的时间范围:" + rangeStart + "," + rangeEnd);
        long rangeStartTIME = TIME.uniformTime(rangeStart);
        long rangeEndTIME = TIME.uniformTime(rangeEnd);

        //开始遍历
        while ( (line = br.readLine()) != null) {
            String[] context = line.split("-->");
            String user = context[0];
            String[] time = context[1].split(",");

            String start = time[0].substring(1);
            String end = time[1].substring(0, time[1].length() - 1);
            startTIME = TIME.uniformTime(start);
            endTIME = TIME.uniformTime(end);


            //该时间区间在查询范围内,属于完全有效时间区间
            if ((rangeStartTIME <= startTIME) && (endTIME <= rangeEndTIME))
            {
                if ((endTIME - startTIME) >= duration)
                {
                    //1.设置开始时间 和 结束时间
                    markStartEndMap.put(start, true);
                    if (!markStartEndMap.containsKey(end)) {
                        markStartEndMap.put(end, false);
                    }

                    //2.将有效区间插入到数据库中.
                    if(!user.equals(preUser))
                    {
                        preUser = user;
                        User u = new User(user,new Interval(start,end));
                        db.insert(u);

                        //所有用户名
                        allUsers.add(user);
                        markUserMap.put(markUserFigure, u);
                        ++markUserFigure;
                    }
                    else{
                        db.insertI(new Interval(start,end));
                    }
                    if( startTIME < min ) min = startTIME;
                    if( endTIME > max ) max = endTIME;

                    //3.有效记录条数加一
                    ++validRecordCount;

                }


            }
            //需要重新切割区间
            else if ((startTIME < rangeStartTIME) && (rangeStartTIME < endTIME))
            {
                if ((endTIME - rangeStartTIME) >= duration)
                {
                    start = rangeStart;
                    //1.设置开始时间 和 结束时间
                    markStartEndMap.put(start, true);
                    if (!markStartEndMap.containsKey(end)) {
                        markStartEndMap.put(end, false);
                    }

                    //2.将有效区间插入到数据库中.
                    if (!user.equals(preUser)) {
                        preUser = user;
                        User u = new User(user, new Interval(start, end));
                        db.insert(u);

                        //所有用户名
                        allUsers.add(user);
                        markUserMap.put(markUserFigure, u);
                        ++markUserFigure;
                    } else {
                        db.insertI(new Interval(start, end));
                    }
                    if (startTIME < min) min = startTIME;
                    if (endTIME > max) max = endTIME;

                    //3.有效记录条数加一
                    ++validRecordCount;
                }

            }
            else if (startTIME < rangeEndTIME && rangeEndTIME < endTIME)
            {
                if ((rangeEndTIME - startTIME) >= duration)
                {
                    end = rangeEnd;
                    //1.设置开始时间 和 结束时间
                    markStartEndMap.put(start, true);
                    if (!markStartEndMap.containsKey(end)) {
                        markStartEndMap.put(end, false);
                    }

                    //2.将有效区间插入到数据库中.
                    if (!user.equals(preUser)) {
                        preUser = user;
                        User u = new User(user, new Interval(start, end));
                        db.insert(u);

                        //所有用户名
                        allUsers.add(user);
                        markUserMap.put(markUserFigure, u);
                        ++markUserFigure;
                    } else {
                        db.insertI(new Interval(start, end));
                    }
                    if (startTIME < min) min = startTIME;
                    if (endTIME > max) max = endTIME;

                    //3.有效记录条数加一
                    ++validRecordCount;
                }

            }
        }
        /**
         * 把 markStartEndMap 里 value 值为 true 的 key (即所有开始时间)加到 allStartTimeArray;
         */
        for (String t : markStartEndMap.keySet()) {
            allTimeArray.add(t);
            if (markStartEndMap.get(t)) {
                allStartTimeSet.add(t);
            }
        }
        System.out.println("共读入"+ validRecordCount + "记录");
        System.out.println("有效用户: " + db.data.size());
    }
}
