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
		String queryRange = "[2013-07-01 09:00:00,2013-07-01 21:00:00]";
//		reprocessRawFile.JudgeIfCrossDay(fileName);

//		reprocessRawFile.countDateRecordNumber(fileName);

		reprocessRawFile.splitRawFile(fileName, queryRange);

	}  

}
