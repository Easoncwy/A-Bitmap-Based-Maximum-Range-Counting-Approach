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

		/*

		//第一个活动
		Map<Interval, Integer> map1 = new HashMap<>();
		String start1 = "2013-07-07 13:05:46";
		String end1 = "2013-07-07 13:08:47";

		String start2 = "2013-07-07 13:05:50";
		String end2 = "2013-07-07 13:08:59";

		String start_x = "2013-07-07 15:05:50";
		String end_x = "2013-07-07 15:08:59";

		Interval inter1 = new Interval(start1, end1);
		Interval inter2 = new Interval(start2, end2);
		Interval inter_x = new Interval(start_x, end_x);

		map1.put(inter1, 79);
		map1.put(inter2, 79);
		map1.put(inter_x, 60);

		//第二个活动
		Map<Interval, Integer> map2 = new HashMap<>();

		String start3 = "2013-07-07 13:06:13";
		String end3 = "2013-07-07 13:08:13";

		String start4 = "2013-07-07 13:06:15";
		String end4 = "2013-07-07 13:08:23";
		Interval inter3 = new Interval(start3, end3);
		Interval inter4 = new Interval(start4, end4);
		map2.put(inter3, 86);
		map2.put(inter4, 86);

		//第三个活动
		Map<Interval, Integer> map3 = new HashMap<>();

		String start5 = "2013-07-07 14:47:30";
		String end5 = "2013-07-07 14:48:33";

		String start6 = "2013-07-07 13:05:46";
		String end6 = "2013-07-07 13:06:49";
		Interval inter5 = new Interval(start5, end5);
		Interval inter6 = new Interval(start6, end6);
		map3.put(inter5, 92);
		map3.put(inter6, 92);


		ActivityConflict activityConflict = new ActivityConflict();
		Map<ArrayList<Interval>, Integer> plans = activityConflict.ActivityConflict(map1, map2, map3);
		activityConflict.outputPlans(plans);
		*/
		String start_x = "2013-07-07 15:05:50";
		String end_x = "2013-07-07 15:08:59";

		String start_y = "2013-07-07 13:06:13";
		String end_y = "2013-07-07 13:08:13";

		String start_z = "2013-07-07 14:47:30";
		String end_z = "2013-07-07 14:48:33";

		Interval inter_x = new Interval(start_x, end_x);
		Interval inter_y = new Interval(start_y, end_y);
		Interval inter_z = new Interval(start_z, end_z);
		ArrayList<Interval> intervals = new ArrayList<>();
		intervals.add(inter_x);
		intervals.add(inter_y);
		intervals.add(inter_z);
		ActivityConflict ac = new ActivityConflict();
		if (!ac.isConflict(intervals)){
			System.out.println("活动不发生冲突");
		}else {
			System.out.println("活动发生冲突");

		}



	}

}
