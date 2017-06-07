package com.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.activityconflict.ActivityConflict;
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
		ArrayList<Interval> intervals = new ArrayList<>();
		String start1 = "2013-07-07 13:06:13";
		String end1 = "2013-07-07 13:08:13";
		Interval inter1 = new Interval(start1, end1);
		intervals.add(inter1);

		String start2 = "2013-07-07 13:05:46";
		String end2 = "2013-07-07 13:06:49";
		Interval inter2 = new Interval(start2, end2);
		intervals.add(inter2);

		String start3 = "2013-07-07 13:05:50";
		String end3 = "2013-07-07 13:08:59";
		Interval inter3 = new Interval(start3, end3);
		intervals.add(inter3);

		/*
		SortAndFind saf = new SortAndFind();
		ArrayList<Interval> sortedInetrvals = saf.sortAllIntervals(intervals);
		for (int i = 0; i < sortedInetrvals.size(); i++) {
			Interval interval = sortedInetrvals.get(i);
			System.out.println(interval.getStart() + "," + interval.getEnd());

		}
		*/
		ActivityConflict activityConflict = new ActivityConflict();
		boolean isConflict = activityConflict.isConflict(intervals);
		System.out.println(isConflict);





	}

}
