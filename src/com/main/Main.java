package com.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.activityconflict.ReprocessRawFile;
import com.entity.Database;
import com.entity.Unit;
import com.entity.User;
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
		long duration = 60 * 1000;
		Map<Integer, User> markUserMap = new HashMap<>();
		ArrayList<String> allTimeArray = new ArrayList<>();
		Set<String> allStartTimeSet = new HashSet<>();
		Set<String> allUsers = new HashSet<>();
//		reprocessRawFile.JudgeIfCrossDay(fileName);

//		reprocessRawFile.countDateRecordNumber(fileName);

		reprocessRawFile.splitRawFile(fileName, queryRange, db, duration,
				markUserMap, allTimeArray, allStartTimeSet, allUsers);


	}  

}
