package com.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.entity.Database;
import com.entity.Unit;
import com.entity.User;
import com.query.NewAlgorithm;
import com.query.SortAndFind;
import com.file.CompressedFileRead;
import com.file.RawFileRead;

public class Main {
	
    public static void main(String[] args) throws IOException, ParseException, Exception {

		/**
		 * Main 方法
		 */
    	
    	Database db = new Database();
    	/** 所有用户的集合*/
    	Set<String> allUsers = new HashSet<>();
    	Map<Integer, User> markUserMap = new HashMap<>();
    	ArrayList<String> allTimeArray = new ArrayList<>();
    	HashSet<String> allStartTimeSet = new HashSet<>();
		//持续时间为一分钟
//        int duration = 60 * 1000 ;
		//持续时间为60分钟
		int duration = 60 * 60 * 1000;

//        Create10ThousandFile create10ThousandFile = new Create10ThousandFile(input,output,duration);

		String test =                "/Users/supreme/Desktop/data/granularity/1Hour/NewReadIntervalHour";
//		String info_bitmapCoding = "/Users/supreme/Desktop/data/granularity/1Hour/info_bitmapCoding";
//		String testBitMapFile =  "/Users/supreme/Desktop/data/granularity/1Hour/bitmapFile";
		String testCompFile =  "/Users/supreme/Desktop/data/granularity/1Hour/cmpBitmapFile";
		String testOutput =  "/Users/supreme/Desktop/data/granularity/1Hour/queryResult";


    	RawFileRead fr = new RawFileRead(test, db, duration, markUserMap, allTimeArray, allStartTimeSet,allUsers);

    	/**
    	 * 获得原始的bitmap
    	 */

		SortAndFind saf = new SortAndFind();
		ArrayList<String> sortedTimeArray = saf.sortAllTime(allTimeArray);


//    	CodeToBitMapFileWrite ctbm = new CodeToBitMapFileWrite();
//    	ctbm.codeToBitMap(testBitMapFile, info_bitmapCoding, db, markUserMap, sortedTimeArray);


    	/**
    	 * 压缩bitmap
    	 */
//        CompressedFileWrite cmpfw = new CompressedFileWrite();
//    	cmpfw.compressedFileWrite(testBitMapFile, testCompFile);

    	CompressedFileRead compbr = new CompressedFileRead();
    	Map<String, ArrayList<Unit>> compressedMap = compbr.compressedFileRead(testCompFile);

        /**
         * 插入测试用例a.新加用户之前未存在
         *
         */
        String userID03 = "u888888";
        String start03 = "2013-07-01 11:32:54";
        String end03 = "2013-07-01 12:59:14";

        /**
         * 插入测试用例b,新加用户之前已经存在
         */

        String userID04 = "u000006";
        String start04 = "2013-07-26 22:52:00";
        String end04 = "2013-07-26 22:54:00";

        /*
        Calendar c1 = Calendar.getInstance();

        Interval insertI = new Interval(start03, end03);
        Insert insert = new Insert(userID03, insertI, markUserMap, sortedTimeArray, allStartTimeSet, allUsers, compressedMap);

        Calendar c2 = Calendar.getInstance();

        long insertCost = c2.getTimeInMillis() - c1.getTimeInMillis();
        System.out.println("插入操作花费时间: " + Integer.toString((int) insertCost) + " ms\n");
        */


        /**
         * 删除测试
         */

        /**
         * 删除用例测试a,删掉区间后,该用户没有其他区间.
         */

        String userID01 = "u000000";
        String start01  = "2013-07-01 12:34:54";
        String end01   = "2013-07-01 12:44:14";

        /**
         * 删除用例测试b,删除区间后,该用户还有其他区间
         */
        String userID02 = "u000000";
        String start02  = "2013-07-26 22:48:42";
        String end02    = "2013-07-26 23:42:52";


		/*
        Calendar c1 = Calendar.getInstance();

        Interval deleteI = new Interval(start01, end01);
        Delete delete = new Delete(userID01, deleteI, markUserMap, allTimeArray, allStartTimeSet, allUsers, compressedMap);

        Calendar c2 = Calendar.getInstance();

        long delCost = c2.getTimeInMillis() - c1.getTimeInMillis();
        System.out.println("删除操作花费时间: " + Integer.toString((int) delCost) + " ms\n");
        */

        /**
         * 查询测试
         */

        //将所有用户的index置0，供之后筛选假用户用
    	for(Integer order:markUserMap.keySet()){
    		User u = markUserMap.get(order);
    		u.index = 0;
    	}
  
    	NewAlgorithm al = new NewAlgorithm();

    	al.getMaxHashMap(compressedMap, duration, markUserMap, allTimeArray, allStartTimeSet, allUsers, testOutput);



	}  

}
