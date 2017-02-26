package Main;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import Entity.Database;
import Entity.Interval;
import Entity.Unit;
import Entity.User;
import File.*;
import InsertAndDelete.Delete;
import InsertAndDelete.Insert;
import Method.NewAlgorithm;
import Method.SortAndFind;

public class Main {
	
    public static void main(String[] args) throws IOException, ParseException, Exception {
    	
    	Database db = new Database();
    	/** 所有用户的集合*/
    	ArrayList<String> allUsers = new ArrayList<>();
    	Map<Integer, User> markUserMap = new HashMap<>();
    	ArrayList<String> allTimeArray = new ArrayList<>();
    	HashSet<String> allStartTimeSet = new HashSet<>();
		//持续时间为一分钟
        int duration = 60 * 1000 ;
    	
//    	String file = "/Users/supreme/Desktop/data/50000/60second50000";
//    	String bitmapFile = "/Users/supreme/Desktop/data/50000/BitMapFile";
//    	String compressedFile = "/Users/supreme/Desktop/data/50000/CompressedBitMapFile";
//        String output = "/Users/supreme/Desktop/data/50000/queryResult";

        /*
        String test =            "/Users/supreme/Desktop/data/Insert50000/60second50000";
    	String testBitMapFile = "/Users/supreme/Desktop/data/Insert50000/BitMapFile";
    	String testCompFile = "/Users/supreme/Desktop/data/Insert50000/CompressedBitMapFile";
		String testOutput = "/Users/supreme/Desktop/data/Insert50000/queryResult";
		*/
//        String input = "/Users/supreme/Desktop/data/NewReadInterval";
//        String output = "/Users/supreme/Desktop/data/100000/60seconds100000";

//        Create10ThousandFile create10ThousandFile = new Create10ThousandFile(input,output,duration);

		String test =                "/Users/supreme/Desktop/data/granularity/1Min/NewReadIntervalMinute";
		String info_bitmapCoding = "/Users/supreme/Desktop/data/granularity/1Min/info_bitmapCoding";
		String testBitMapFile =  "/Users/supreme/Desktop/data/granularity/1Min/bitmapFile";
		String testCompFile =  "/Users/supreme/Desktop/data/granularity/1Min/cmpBitmapFile";
		String testOutput =  "/Users/supreme/Desktop/data/granularity/1Min/queryResult";


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
        String start03 = "2013-07-26 22:52:00";
        String end03 = "2013-07-26 22:54:00";

        /**
         * 插入测试用例b,新加用户之前已经存在
         */
        String userID04 = "u000006";
        String start04 = "2013-07-26 22:52:00";
        String end04 = "2013-07-26 22:54:00";

        /*
        Calendar c1 = Calendar.getInstance();

        Interval insertI = new Interval(start04, end04);
        Insert insert = new Insert(userID04, insertI, markUserMap, sortedTimeArray, allStartTimeSet, allUsers, compressedMap);

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

        String userID01 = "u000002";
        String start01  = "2013-07-13 16:21:47";
        String end01   = "2013-07-13 16:23:25";

        /**
         * 删除用例测试b,删除区间后,该用户还有其他区间
         */
        String userID02 = "u000000";
        String start02  = "2013-07-26 22:48:42";
        String end02    = "2013-07-26 23:42:52";

        /*
        Calendar c1 = Calendar.getInstance();

        Interval deleteI = new Interval(start02, end02);
        Delete delete = new Delete(userID02, deleteI, markUserMap, allTimeArray, allStartTimeSet, allUsers, compressedMap);

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
