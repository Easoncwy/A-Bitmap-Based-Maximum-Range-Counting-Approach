package Main;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import Entity.Database;
import Entity.Interval;
import Entity.Unit;
import Entity.User;
import File.RawFileRead;
import Method.NewAlgorithm;
import Method.SortAndFind;
import File.CompressedFileRead;
import File.CodeToBitMapFileWrite;

public class Main {
	
    public static void main(String[] args) throws IOException, ParseException {
    	
    	Database db = new Database();
    	/** 所有用户的集合*/
    	ArrayList<String> allUsers = new ArrayList<>();
    	Map<Integer, User> markUserMap = new HashMap<>();
    	ArrayList<String> allTimeArray = new ArrayList<>();
    	HashSet<String> allStartTimeArray = new HashSet<>();
        int duration = 300 * 1000 ;
    	
       // String testFile = "/Users/hou/Documents/data/50000/test";
    	//String testBitMapFile = "/Users/hou/Documents/data/50000/testBitMapFile";
    	
    	String file = "/Users/supreme/Desktop/data/50000/60second50000";
    	String bitmapFile = "/Users/supreme/Desktop/data/50000/BitMapFile";
    	String compressedFile = "/Users/supreme/Desktop/data/50000/CompressedBitMapFile";
        String output = "/Users/supreme/Desktop/data/50000/queryResult";

//    	String example = "/Users/hou/Documents/data/example/example";
//    	String exampleBitMapFile = "/Users/hou/Documents/data/example/exampleBitMapFile";
//    	String exampleCompFile = "/Users/hou/Documents/data/example/exampleCompressedFile";
//    	
    	
//    	String minuteFile = "/Users/supreme/Desktop/NewReadIntervalMinute50000";
//    	ctf.testFileWrite(minuteFile, db, duration);
//    	ctf.createExampleFile(example);
    	
    	RawFileRead fr = new RawFileRead();
    	fr.fileRead(file, db, duration, markUserMap, allTimeArray, allStartTimeArray,allUsers);
    	
    	/**测试插入*/

    	
    	SortAndFind sortAndFind = new SortAndFind();
    	ArrayList<String> sortedTimeArray = SortAndFind.sortAllTime(allTimeArray);
    	
    	/**
    	 * 获得原始的bitmap
    	 */
    	CodeToBitMapFileWrite ctbm = new CodeToBitMapFileWrite();
    	ctbm.codeToBitMap(bitmapFile, db, markUserMap, sortedTimeArray);

    	/**
    	 * 获得压缩后的bitmap
    	 */
  /*  	CompressedFileWrite compfw = new CompressedFileWrite();
    	compfw.compressedFileWrite(bitmapFile, compressedFile);
   */ 	

		/*
    	CompressedFileRead compbr = new CompressedFileRead();
    	Map<String, ArrayList<Unit>> compressedMap = compbr.compressedFileRead(compressedFile);
    	
    	//将所有用户的index置0，供之后筛选假用户用
    	for(Integer order:markUserMap.keySet()){
    		User u = markUserMap.get(order);
    		u.index = 0;
    	}
  
    	NewAlgorithm al = new NewAlgorithm();
    	
    	
    	Calendar ca = Calendar.getInstance();
    	
    	//System.out.println("查询算法开始计时");
        Map<Interval, ArrayList<User>> maxHashMap = al.getMaxHashMap(compressedMap, duration, markUserMap, allTimeArray, allStartTimeArray);

    	
    	al.printTheFinalSearchingResult(maxHashMap,output);
    	
    	//System.out.println("查询算法结束计时");
    	Calendar cb = Calendar.getInstance();
    	
    	long finalCost = cb.getTimeInMillis() - ca.getTimeInMillis();
		System.out.println("查询算法花费时间: " + Integer.toString((int) finalCost) + " ms\n");
		*/
    	
	}  

}
