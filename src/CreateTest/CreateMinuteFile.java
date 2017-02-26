package CreateTest;

import Entity.Database;
import Entity.Interval;
import Entity.Unit;
import Entity.User;
import File.*;
import File.CodeToBitMapFileWrite;
import File.RawFileRead;
import Method.NewAlgorithm;
import Method.SortAndFind;
import Method.Time;

import java.io.*;
import java.util.*;

/**
 * Created by eason on 2017/2/22.
 */
public class CreateMinuteFile {
    public CreateMinuteFile(String input, String output) throws Exception{
        File inputFile = new File(input);
        FileReader fileReader = new FileReader(inputFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);


        File outputFile = new File(output);
        FileWriter fileWriter = new FileWriter(outputFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        String line = "";
        int count = 0;
        while ((line = bufferedReader.readLine()) != null){
            String[] context = line.split("-->");
            String userName = context[0];
            String[] time = context[1].split(",");
            String start = time[0].substring(1);
            String end = time[1].substring(0, time[1].length()-1);

            String startFrontPart = start.substring(0,start.length() - 2);
            String endFrontPart = end.substring(0, start.length() - 2);
            String postFix = "00";

            String hourStart = startFrontPart + postFix;
            String hourEnd = endFrontPart + postFix;
            String record = "";
            Time TIME = new Time();
            long hourStartTime = TIME.uniformTime(hourStart);
            long hourEndTime = TIME.uniformTime(hourEnd);

            if ((hourStartTime < hourEndTime) && (count < 50000)){
                record = userName + "-->" + "[" + hourStart  + "," + hourEnd + "]" + "\n";
                bufferedWriter.write(record);
                ++count;
            }




        }
        bufferedReader.close();
        bufferedWriter.close();



    }

    public static void main(String[] args) throws Exception{
        /*
        String input = "/Users/supreme/Desktop/data/用户数据文本/NewReadInterval";
//                output = "/Users/supreme/Desktop/data/granularity/1minute/NewReadIntervalMinute";
//        CreateMinuteFile createMinuteFile = new CreateMinuteFile(input, output);
//        String rawFile = output;
        */

        Database db = new Database();
        /** 所有用户的集合*/
        ArrayList<String> allUsers = new ArrayList<>();
        Map<Integer, User> markUserMap = new HashMap<>();
        ArrayList<String> allTimeArray = new ArrayList<>();
        HashSet<String> allStartTimeSet = new HashSet<>();
        //持续时间为一分钟
        int duration = 60 * 1000 ;

        String test =                "/Users/supreme/Desktop/data/granularity/1Min/NewReadIntervalMinute";
        String info_bitmapCoding = "/Users/supreme/Desktop/data/granularity/1Min/info_bitmapCoding";
        String testBitMapFile =  "/Users/supreme/Desktop/data/granularity/1Min/bitmapFile";
        String testCompFile =  "/Users/supreme/Desktop/data/granularity/1Min/cmpBitmapFile";
        String testOutput =  "/Users/supreme/Desktop/data/granularity/1Min/queryResult";
//        CodeToBitMapFileWrite ctbm = new CodeToBitMapFileWrite();

//        Map<String, BitSet> encodedMap;
//        encodedMap = ctbm.codeToBitMap(testBitMapFile, info_bitmapCoding, db, markUserMap, sortedTimeArray);

        /**
         * 压缩
         */

//        CompressedFileWrite cmpfw = new CompressedFileWrite();
//    	cmpfw.compressedFileWrite(testBitMapFile, testCompFile);

        /**
         * 查询测试
         */

        RawFileRead rawFileRead = new RawFileRead(test, db, duration, markUserMap, allTimeArray, allStartTimeSet, allUsers);
        CompressedFileRead compbr = new CompressedFileRead();
        Map<String, ArrayList<Unit>> compressedMap = compbr.compressedFileRead(testCompFile);


        SortAndFind saf = new SortAndFind();
        ArrayList<String> sortedTimeArray = saf.sortAllTime(allTimeArray);

        //将所有用户的index置0，供之后筛选假用户用
        for(Integer order:markUserMap.keySet()){
            User u = markUserMap.get(order);
            u.index = 0;
        }

        NewAlgorithm al = new NewAlgorithm();

        Calendar ca = Calendar.getInstance();

        //System.out.println("查询算法开始计时");
        Map<Interval, ArrayList<User>> maxHashMap = al.getMaxHashMap(compressedMap, duration, markUserMap, allTimeArray, allStartTimeSet);


        al.printTheFinalSearchingResult(maxHashMap,testOutput);

        //System.out.println("查询算法结束计时");
        Calendar cb = Calendar.getInstance();

        long queryCost = cb.getTimeInMillis() - ca.getTimeInMillis();
        System.out.println("查询算法花费时间: " + Integer.toString((int) queryCost) + " ms\n");


    }
}
