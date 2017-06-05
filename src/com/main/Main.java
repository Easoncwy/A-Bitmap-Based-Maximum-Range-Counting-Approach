package com.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.activityconflict.ReprocessRawFile;
import com.entity.Database;
import com.entity.Interval;
import com.entity.Unit;
import com.entity.User;
import com.file.CodeToBitMapFileWrite;
import com.file.CompressedFileWrite;
import com.query.NewAlgorithm;
import com.query.SortAndFind;
import com.file.CompressedFileRead;
import com.file.RawFileRead;

public class Main {

	public static void main(String[] args) throws IOException, ParseException, Exception {
		ReprocessRawFile reprocessRawFile = new ReprocessRawFile();
		String fileName = "/Users/supreme/Desktop/data/NewReadInterval";
		String queryRange = "[2013-07-07 09:00:00,2013-07-07 21:00:00]";
		Database db = new Database();
		long duration = 3 * 60 * 1000;
		Map<Integer, User> markUserMap = new HashMap<>();
		ArrayList<String> allTimeArray = new ArrayList<>();
		Set<String> allStartTimeSet = new HashSet<>();
		Set<String> allUsers = new HashSet<>();
//		reprocessRawFile.JudgeIfCrossDay(fileName);

//		reprocessRawFile.countDateRecordNumber(fileName);

		reprocessRawFile.splitRawFile(fileName, queryRange, db, duration,
				markUserMap, allTimeArray, allStartTimeSet, allUsers);

		//先对所有有效的时间点按时间顺序从小到大进行排序
		SortAndFind saf = new SortAndFind();
		ArrayList<String> sortedTimeArray = saf.sortAllTime(allTimeArray);

		//编码bitmap,并将结果写入文件
		CodeToBitMapFileWrite ctbm = new CodeToBitMapFileWrite();

		String testBitMapFile = "/Users/supreme/Desktop/data/ActivityConflict/3min/bitmapFile";
		String info_bitmapCoding = "/Users/supreme/Desktop/data/ActivityConflict/3min/info_bitmapCoding";
		String testCompBitMapFile = "/Users/supreme/Desktop/data/ActivityConflict/3min/cmpBitmapFile";
		ctbm.codeToBitMap(testBitMapFile,info_bitmapCoding,db,markUserMap,allTimeArray);

		//压缩bitmap
		CompressedFileWrite cmpfw = new CompressedFileWrite();
		cmpfw.compressedFileWrite(testBitMapFile, testCompBitMapFile);

		/**
		 * 读取压缩后的bitmap文件
		 */
		CompressedFileRead compbr = new CompressedFileRead();
		Map<String, ArrayList<Unit>> compressedMap = compbr.compressedFileRead(testCompBitMapFile);

		/**
		 * 查询测试
		 */

		/**
		 * 将所有用户的index置0，供之后筛选假用户用
		 */
		for (Integer order:markUserMap.keySet()) {
			User u = markUserMap.get(order);
			u.index = 0;
		}

		String testOutput =  "/Users/supreme/Desktop/data/ActivityConflict/3min/queryResult";
		NewAlgorithm al = new NewAlgorithm();

		al.getMaxHashMap(compressedMap, duration, markUserMap, sortedTimeArray, allStartTimeSet, allUsers,testOutput);

	}

}
