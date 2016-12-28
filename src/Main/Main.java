package Main;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import Entity.Database;
import Entity.Interval;
import Entity.Unit;
import Entity.User;
import File.RawFileRead;
import Insert.Insert;
import Method.NewAlgorithm;
import Method.SortAndFind;
import File.CompressedFileRead;

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
    	
       // String testFile = "/Users/hou/Documents/data/50000/test";
    	//String testBitMapFile = "/Users/hou/Documents/data/50000/testBitMapFile";
    	
//    	String file = "/Users/supreme/Desktop/data/50000/60second50000";
//    	String bitmapFile = "/Users/supreme/Desktop/data/50000/BitMapFile";
//    	String compressedFile = "/Users/supreme/Desktop/data/50000/CompressedBitMapFile";
//        String output = "/Users/supreme/Desktop/data/50000/queryResult";


		String example = "/Users/supreme/Desktop/data/example/example";
    	String exampleBitMapFile = "/Users/supreme/Desktop/data/example/exampleBitMapFile";
    	String exampleCompFile = "/Users/supreme/Desktop/data/example/exampleCompFile";
		String exampleOutput = "/Users/supreme/Desktop/data/example/exampleQueryResult";
//    	
    	
//    	String minuteFile = "/Users/supreme/Desktop/NewReadIntervalMinute50000";
//    	ctf.testFileWrite(minuteFile, db, duration);
//    	ctf.createExampleFile(example);
    	
    	RawFileRead fr = new RawFileRead();
    	fr.fileRead(example, db, duration, markUserMap, allTimeArray, allStartTimeSet,allUsers);

    	
    	/**
    	 * 获得原始的bitmap
    	 */

		SortAndFind sortAndFind = new SortAndFind();
		ArrayList<String> sortedTimeArray = SortAndFind.sortAllTime(allTimeArray);


//    	CodeToBitMapFileWrite ctbm = new CodeToBitMapFileWrite();
//    	ctbm.codeToBitMap(exampleBitMapFile, db, markUserMap, sortedTimeArray);


    	/**
    	 * 获得压缩后的bitmap
    	 */
//        CompressedFileWrite compfw = new CompressedFileWrite();
//    	compfw.compressedFileWrite(exampleBitMapFile, exampleCompFile);


    	CompressedFileRead compbr = new CompressedFileRead();
    	Map<String, ArrayList<Unit>> compressedMap = compbr.compressedFileRead(exampleCompFile);


		/**
		 * 测试插入用例1:开始时间点和结束时间点 都已经编码.在(start, end)范围里没有其他时间点.
		 */
		String userId1 = "u000002";
		String start1 = "2013-07-01 12:52:09";
		String end1   = "2013-07-01 12:58:48";

		/**
		 * 测试插入用例2:开始时间点和结束时间点 都已经编码.在(start, end)范围里有其他时间点.
		 */
		String userId2 = "u000002";
		String start2 = "2013-07-01 12:51:09";
		String end2   = "2013-07-01 12:59:48";

		/**
		 * 测试插入用例3:开始时间未编码.在(start, end)范围里有其他时间点.
		 */
		String userId3 = "u000002";
		String start3 = "2013-07-01 12:55:09";
		String end3   = "2013-07-01 12:59:48";


		Interval insertI = new Interval(start3, end3);

		Insert insert = new Insert(userId3, insertI,markUserMap,allTimeArray,allStartTimeSet,allUsers,compressedMap);




		//将所有用户的index置0，供之后筛选假用户用
    	for(Integer order:markUserMap.keySet()){
    		User u = markUserMap.get(order);
    		u.index = 0;
    	}
  
    	NewAlgorithm al = new NewAlgorithm();
    	
    	
    	Calendar ca = Calendar.getInstance();
    	
    	//System.out.println("查询算法开始计时");
        Map<Interval, ArrayList<User>> maxHashMap = al.getMaxHashMap(compressedMap, duration, markUserMap, allTimeArray, allStartTimeSet);

    	
    	al.printTheFinalSearchingResult(maxHashMap,exampleOutput);
    	
    	//System.out.println("查询算法结束计时");
    	Calendar cb = Calendar.getInstance();
    	
    	long finalCost = cb.getTimeInMillis() - ca.getTimeInMillis();
		System.out.println("查询算法花费时间: " + Integer.toString((int) finalCost) + " ms\n");




    	
	}  

}
