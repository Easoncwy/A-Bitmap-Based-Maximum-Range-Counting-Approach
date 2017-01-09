package Main;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import Delete.Delete;
import Entity.Database;
import Entity.Interval;
import Entity.Unit;
import Entity.User;
import File.*;
import Insert.Insert;
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



        String test =               "/Users/supreme/Desktop/data/50000/60seconds50000";
        String info_bitmapCoding = "/Users/supreme/Desktop/data/50000/info_bitmapCoding";
        String testBitMapFile =  "/Users/supreme/Desktop/data/50000/bitmapFile";
        String testCompFile =  "/Users/supreme/Desktop/data/50000/cmpBitmapFile";
        String testOutput =  "/Users/supreme/Desktop/data/50000/queryResult";


    	RawFileRead fr = new RawFileRead();
    	fr.fileRead(test, db, duration, markUserMap, allTimeArray, allStartTimeSet,allUsers);

    	
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
        String userID08 = "u888888";
        String start08 = "2013-07-26 22:52:00";
        String end08 = "2013-07-26 22:54:00";

        /**
         * 插入测试用例b,新加用户之前已经存在
         */
        String userID09 = "u000002";
        String start09 = "2013-07-29 21:52:00";
        String end09 = "2013-07-29 21:56:00";



        Calendar c1 = Calendar.getInstance();

        Interval insertI = new Interval(start08, end08);
        Insert insert = new Insert(userID08, insertI, markUserMap, allTimeArray, allStartTimeSet, allUsers, compressedMap);

        Calendar c2 = Calendar.getInstance();

        long insertCost = c2.getTimeInMillis() - c1.getTimeInMillis();
        System.out.println("插入操作花费时间: " + Integer.toString((int) insertCost) + " ms\n");








        /**
         * 删除用例测试a,删掉区间后,该用户没有其他区间.
         */


        String userID10 = "u000002";
        String start10  = "2013-07-13 16:21:47";
        String end10    = "2013-07-13 16:23:25";

        /**
         * 删除用例测试b,删除区间后,该用户还有其他区间
         */
        String userID11 = "u000218";
        String start11  = "2013-07-29 21:54:27";
        String end11    = "2013-07-29 21:56:15";


        /*

        Calendar c1 = Calendar.getInstance();

        Interval deleteI = new Interval(start11, end11);
        Delete delete = new Delete(userID11, deleteI, markUserMap, allTimeArray, allStartTimeSet, allUsers, compressedMap);

        Calendar c2 = Calendar.getInstance();

        long insertCost = c2.getTimeInMillis() - c1.getTimeInMillis();
        System.out.println("删除操作花费时间: " + Integer.toString((int) insertCost) + " ms\n");
        */








//        Interval deleteI = new Interval(start06, end06);
//        Delete delete = new Delete(userID06, deleteI, markUserMap, allTimeArray, allStartTimeSet, allUsers, compressedMap);





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
